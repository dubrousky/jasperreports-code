define(["jquery-1.10.2"], function($) {
	
	var UrlManager = {
	        reportcontexturl: "/jasperreports/servlets/reportcontext",
	        reportoutputurl: "/jasperreports/servlets/reportoutput",
	        reportactionurl: "/jasperreports/servlets/reportaction",
	        reportcomponentsurl: "/jasperreports/servlets/reportcomponents",
	        reportpagestatusurl: "/jasperreports/servlets/reportpagestatus"
	    };
	
    var Loader = function(o) {

        this.config = {
            reporturi: null,
            async: true
        };

        $.extend(this.config, o);

        // promises
        this.contextIdPromise = null;
    };
	
	Loader.prototype = {
        getContextId: function() {
            return this._getContextIdPromise().then(function(jsonData, textStatus, jqXHR) {
                return jsonData.contextid;
            });
        },

        getHtmlForPage: function(page, boolNavigate) {
            var it = this;
            return it.getContextId().then(function(ctxid) {
                return it._ajaxLoad({
                    url: UrlManager.reportoutputurl,
                    params: {
                        jr_ctxid: ctxid,
                        jr_page: page
                    }
                }, 'html');
            });
        },

        getStatusForPage: function(page, timestamp) {
            var it = this;
            return it.getContextId().then(function(ctxid) {
                return it._ajaxLoad({
                    url: UrlManager.reportpagestatusurl,
                    params: {
                        jr_ctxid: ctxid,
                        jr_page: page,
                        jr_page_timestamp: timestamp
                    }
                }, 'json');
            });
        },

        getComponentsForPage: function(page) {
            var it = this;
            return it.getContextId().then(function(ctxid) {
                return it._ajaxLoad({
                    url: UrlManager.reportcomponentsurl,
                    params: {
                        jr_ctxid: ctxid,
                        jr_page: page
                    }
                }, 'json');
            });
        },

        runAction: function(o) {
            var it = this;
            return it.getContextId().then(function(ctxid) {
                return it._ajaxLoad({
                    url: UrlManager.reportactionurl,
                    params: {
                        jr_ctxid: ctxid,
                        jr_action: JSON.stringify(o.action)
                    }
                }, 'json');
            });
        },

        // internal functions
        _getContextIdPromise: function() {
            var it = this;
            if (it.contextIdPromise == null) {
                it.contextIdPromise = it._ajaxLoad({
                    url: UrlManager.reportcontexturl,
                    params: {
                        jr_report_uri: it.config.reporturi,
                        jr_async: it.config.async
                    }
                }, 'json');
            }
            return it.contextIdPromise;
        },
		_ajaxLoad: function(o, dataType) {
			return  $.ajax(o.url, {type: 'POST', dataType: dataType, data: o.params}).then(
                null,
                this._errHandler
            );
		},
		_errHandler: function(jqXHR, textStatus, errorThrown) {
			return $.parseJSON(jqXHR.responseText);
		}
	};

    return Loader;
});