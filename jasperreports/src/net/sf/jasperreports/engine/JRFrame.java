/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2005 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 185, Berry Street, Suite 6200
 * San Francisco CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.engine;

import java.util.List;

/**
 * An abstract representation of a report elements container.
 * <p>
 * A frame is a report element that contains sub elements.
 * It has a backgroud, a border and it stretches to accommodate its content.
 * <p>
 * For the Graphics2D and PDF exporters, a frame is equivalent to a rectangle
 * placed behind a group of elements.  The HTML exporter creates sub-tables for frames
 * and the XLS exporter includes the frame sub elements into the grid.
 * <p>
 * For elements inside a frame, the coordinates, positionType and stretchType
 * properties and relative to the frame instead of the band. 
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id$
 */
public interface JRFrame extends JRElement
{
	/**
	 * Returns the border of the frame.
	 * 
	 * @return the border of the frame
	 */
	public JRBox getBox();
	
	/**
	 * Returns the list of direct children of the frame, containing elements and element groups.
	 * 
	 * @return list of direct children of the frame
	 */
	public List getChildren();
	
	
	/**
	 * Returns all the sub elements of the frame.
	 * 
	 * @return all the sub elements of the frame
	 */
	public JRElement[] getElements();
}
