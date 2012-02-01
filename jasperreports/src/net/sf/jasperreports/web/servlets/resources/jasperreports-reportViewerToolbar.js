/**
 * Defines 'viewertoolbar' module  in jasperreports namespace
 */
(function(global) {
	if (typeof global.jasperreports.reportviewertoolbar !== 'undefined') {
		return;
	}
	
	var jvt = {
				undoRedoCounters: {
					undos: 0,
					redos: 0
				}
	};
	
	jvt.init = function(toolbarId) {
		var toolbar = jQuery('#' + toolbarId);
		
		if (toolbar.size() != 1) {
			return;
		}
		
		if (toolbar.attr('data-initialized') == null) {
			toolbar.attr('data-initialized', 'true');
			
			toolbar.css({ opacity: 0.8 });

			toolbar.draggable();
			
			toolbar.bind('click', function(event) {
				var target = jQuery(event.target);
				if (target.is('.enabledViewerButton')) {
					var jg = global.jasperreports.global,
						parent = jQuery(this),
						toolbarId = parent.attr('id'),
						currentPage = parseInt(parent.attr('data-currentpage')),
						totalPages = parseInt(parent.attr('data-totalpages')),
						requestedPage,
						ctx;
					
					// cancel the auto refresh of the current page
					jvt.cancelAutoRefresh(parent);
					
					if (target.is('.pageFirst')) {
						requestedPage = 0;
					} else if (target.is('.pagePrevious')) {
						requestedPage = currentPage - 1;
					} else if (target.is('.pageNext')) {
						requestedPage = currentPage+1;
					} else if (target.is('.pageLast')) {
						requestedPage = totalPages -1;
					}
					
					if (requestedPage != null) {
						jvt.refreshPage(toolbarId, requestedPage);
					}
					
					if (target.is('.undo')) {		// FIXMEJIVE: place this in headertoolbar.js
						var actionData = {actionName: 'undo'},
							undoActionLink = jQuery('.headerToolbarMask:first').attr('data-resizeAction'),
							ctx = jg.getToolbarExecutionContext(jQuery('div.columnHeader:first'), 
																undoActionLink, 'jr.action=' + jg.toJsonString(actionData), 
																jvt.performUndo, 
            	    											[toolbarId], 
																true);
                        
						if (ctx) {
                            ctx.run();
                        }
						
					} else if (target.is('.redo')) {		// FIXMEJIVE: place this in headertoolbar.js
						var actionData = {actionName: 'redo'},
						undoActionLink = jQuery('.headerToolbarMask:first').attr('data-resizeAction'),
						ctx = jg.getToolbarExecutionContext(jQuery('div.columnHeader:first'), 
															undoActionLink, 
															'jr.action=' + jg.toJsonString(actionData), 
															jvt.performRedo, 
															[toolbarId], 
															true);
						
                        if (ctx) {
                            ctx.run();
                        }
					} else if (target.is('.save')) {		// FIXMEJIVE: place this in headertoolbar.js
						var actionData = {actionName: 'save'},
						undoActionLink = jQuery('.headerToolbarMask:first').attr('data-resizeAction'),
						ctx = jg.getToolbarExecutionContext(jQuery('div.columnHeader:first'), 
															undoActionLink, 
															'jr.action=' + jg.toJsonString(actionData), 
															null, 
															null, 
															true);
						
                        if (ctx) {
                            ctx.run();
                        }
					}
				}
			});
		}
	};
	
	jvt.refreshPage = function(toolbarId, requestedPage, requestParams) {
		var jg = global.jasperreports.global,
			toolbar = jQuery('#' + toolbarId),
			currentHref = toolbar.attr('data-url'),
			params = 'jr.page=' + requestedPage;
		
		if (requestParams) {
			params += '&' + requestParams;
		}

		var ctx = jg.getToolbarExecutionContext(toolbar, 
										currentHref, 
										params, 
										jvt.afterReportLoadCallback, 
										[toolbarId], 
										true);
		if (ctx) {
			ctx.run();
		}
	};
	
	jvt.setAutoRefresh = function(toolbarId) {
		var jqToolbar = jQuery('#' + toolbarId),
			pageTimestamp = jqToolbar.attr('data-pageTimestamp');
		
		if (pageTimestamp) {
			var timeoutId = global.setTimeout(
					(function (tid, reqPage, reqParams) {
						return function() {
							jvt.refreshPage(tid, reqPage, reqParams);
						};
					}(toolbarId, jqToolbar.attr('data-currentpage'), 'jr.pagetimestamp=' + pageTimestamp)), 
					5000);//FIXME configure
			jqToolbar.attr('data-autoRefreshId', timeoutId);
		} else {
			jqToolbar.removeAttr('data-autoRefreshId');
		}
	};
	
	jvt.cancelAutoRefresh = function(toolbar) {
		if (typeof toolbar === 'string') {
			toolbar = jQuery('#' + toolbar);
		} 
		var timeoutId = toolbar.attr('data-autoRefreshId');
		if (timeoutId) {
			window.clearTimeout(timeoutId);
			toolbar.removeAttr('data-autoRefreshId');
		}
	};

	jvt.updateCurrentPageForToolbar = function(jQueryToolbar, newCurrentPage, newTotalPages, pageTimestamp) {
		jQueryToolbar.attr('data-currentpage', newCurrentPage);
		if (typeof(newTotalPages) != 'undefined') {
			jQueryToolbar.attr('data-totalpages', newTotalPages);
		} else {
			jQueryToolbar.attr('data-totalpages', '');
		}
		
		if (pageTimestamp) {
			jQueryToolbar.attr('data-pagetimestamp', pageTimestamp);
		} else {
			jQueryToolbar.removeAttr('data-pagetimestamp');
		}
	};
	
	jvt.toolbarUtils = (function() {
		var classEnabled = 'enabledViewerButton',
			classDisabled = 'disabledViewerButton';
		
		return {
			getClassEnabled: function() {
				return classEnabled;
			},
			getClassDisabled: function() {
				return classDisabled;
			},
			enableElem: function(jqElem) {
				jqElem.removeClass(classDisabled);
				jqElem.addClass(classEnabled);
			},
			disableElem: function(jqElem) {
				jqElem.removeClass(classEnabled);
				jqElem.addClass(classDisabled);
			},
			enablePair: function(jqElem1, jqElem2){
				this.enableElem(jqElem1);
				this.enableElem(jqElem2);
			},
			disablePair: function(jqElem1, jqElem2){
				this.disableElem(jqElem1);
				this.disableElem(jqElem2);
			}
		};
	}());
	
	jvt.updateToolbarPaginationButtons = function (jqToolbar) {
		var jg = global.jasperreports.global,
			currentPage = jqToolbar.attr('data-currentpage'),
			totalPages = jqToolbar.attr('data-totalpages'),
			pageFirst = jQuery('.pageFirst', jqToolbar),
			pagePrevious = jQuery('.pagePrevious', jqToolbar),
			pageNext = jQuery('.pageNext', jqToolbar),
			pageLast = jQuery('.pageLast', jqToolbar),
			undo = jQuery('.undo', jqToolbar),
			redo = jQuery('.redo', jqToolbar),
			save = jQuery('.save', jqToolbar),
			utils = jvt.toolbarUtils,
			classEnabled = utils.getClassEnabled(),
			classDisabled = utils.getClassDisabled();
		
		if (jg.isEmpty(totalPages)) {
			utils.enableElem(pageNext);
			utils.disableElem(pageLast);
		}
		else if (totalPages > 1 && currentPage < totalPages - 1) {
			utils.enablePair(pageNext, pageLast);
		} else {
			utils.disablePair(pageNext, pageLast);
		}
		
		if (currentPage == 0) {
			utils.disablePair(pageFirst, pagePrevious);
		} else {
			utils.enablePair(pageFirst, pagePrevious);
		}
		
		if (!(undo.hasClass(classEnabled) || undo.hasClass(classDisabled))) {
			utils.disableElem(undo);
		}
		if (!(redo.hasClass(classEnabled) || redo.hasClass(classDisabled))) {
			utils.disableElem(redo);
		}
	};
	
	jvt.disableToolbarPaginationButtons = function (toolbarId) {
		var jqToolbar = jQuery('#' + toolbarId),
			utils = jvt.toolbarUtils;
		utils.disablePair(jQuery('.pageFirst', jqToolbar), jQuery('.pageLast', jqToolbar));
		utils.disablePair(jQuery('.pagePrevious', jqToolbar), jQuery('.pageNext', jqToolbar));
		
	};
	
	jvt.performAction = function (toolbarId, response) {
		var undoRedoCounters = jvt.undoRedoCounters;
		
		undoRedoCounters.undos ++;
		jvt.updateToolbarUndoButton(toolbarId, true); // enable undo
		
		undoRedoCounters.redos = 0;
		jvt.updateToolbarRedoButton(toolbarId, false); // disable redo
		
		
		jvt.disableToolbarPaginationButtons(toolbarId);
		jvt.afterReportLoadCallback(toolbarId, response);
	};
	
	jvt.performUndo = function (toolbarId, response) {
		var undoRedoCounters = jvt.undoRedoCounters;
		
		undoRedoCounters.redos ++;
		jvt.updateToolbarRedoButton(toolbarId, true); // enable redo
		
		undoRedoCounters.undos --;
		if (undoRedoCounters.undos <= 0) {
			jvt.updateToolbarUndoButton(toolbarId, false); // disable undo
			undoRedoCounters.undos = 0;
		}
		
		jvt.disableToolbarPaginationButtons(toolbarId);
		jvt.afterReportLoadCallback(toolbarId, response);
	};
	
	jvt.performRedo = function (toolbarId, response) {
		var undoRedoCounters = jvt.undoRedoCounters;
		
		undoRedoCounters.undos ++;
		jvt.updateToolbarUndoButton(toolbarId, true); // enable undo
		
		undoRedoCounters.redos --;
		if (undoRedoCounters.redos <= 0) {
			jvt.updateToolbarRedoButton(toolbarId, false); // disable redo
			undoRedoCounters.redos = 0;
		}
		
		jvt.disableToolbarPaginationButtons(toolbarId);
		jvt.afterReportLoadCallback(toolbarId, response);
	};
	
	jvt.updateToolbarUndoButton = function (toolbarId, boolEnable) {
		var jqToolbar = jQuery('#' + toolbarId),
			utils = jvt.toolbarUtils;
		
		if (boolEnable) {
			utils.enableElem(jQuery('.undo', jqToolbar));
		} else {
			utils.disableElem(jQuery('.undo', jqToolbar));
		}
	};

	jvt.updateToolbarRedoButton = function (toolbarId, boolEnable) {
		var jqToolbar = jQuery('#' + toolbarId),
			utils = jvt.toolbarUtils;
		
		if (boolEnable) {
			utils.enableElem(jQuery('.redo', jqToolbar));
		} else {	
			utils.disableElem(jQuery('.redo', jqToolbar));
		}
	};
	
	jvt.loadReport = function (reportUrl, jsonParamsObject, toolbarId) {
		var jg = global.jasperreports.global,
			ctx = jg.getToolbarExecutionContext(jQuery('#'+toolbarId),	// startPoint 
									reportUrl, 							// url
									jQuery.parseJSON(jsonParamsObject), // params
									jvt.afterReportLoadCallback, 		// callback
									[toolbarId],						// calback args array
									true);								// is JSON
		if (ctx) {
			ctx.run();
		}
	};
	
	jvt.afterReportLoadCallback = function (toolbarId, response) {
		var jqToolbar = jQuery('#' + toolbarId),
			reportStatusDiv = jQuery('#reportStatus', response);
		
		jvt.updateCurrentPageForToolbar(jqToolbar, 
										reportStatusDiv.attr('data-currentPage'), 
										reportStatusDiv.attr('data-totalPages'), 
										reportStatusDiv.attr('data-pagetimestamp'));
		
		jvt.updateToolbarPaginationButtons(jqToolbar);
		jvt.setAutoRefresh(toolbarId);
	};

	global.jasperreports.reportviewertoolbar = jvt;
	
} (this));
