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
package net.sf.jasperreports.engine.util;

import java.awt.Color;

import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.JRStyleContainer;
import net.sf.jasperreports.engine.fill.JRTemplateElement;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id$
 */
public class JRStyleResolver
{


	/**
	 *
	 */
	private static JRFont getBaseFont(JRFont font)
	{
		if (font.getReportFont() != null)
			return font.getReportFont();
		if (font.getDefaultStyleProvider() != null)
			return font.getDefaultStyleProvider().getDefaultFont();
		return null;
	}
	
	/**
	 *
	 */
	private static JRStyle getBaseStyle(JRStyleContainer styleContainer)
	{
		if (styleContainer.getStyle() != null)
			return styleContainer.getStyle();
		if (styleContainer.getDefaultStyleProvider() != null)
			return styleContainer.getDefaultStyleProvider().getDefaultStyle();
		return null;
	}


	/**
	 *
	 */
	public static byte getMode(JRElement element, byte defaultMode)//FIXME STYLE make interface for reducing number of similar mode methods
	{
		if (element.getOwnMode() != null) 
			return element.getOwnMode().byteValue();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getMode() != null)
			return style.getMode().byteValue();
		return defaultMode;
	}

	/**
	 *
	 */
	public static byte getMode(JRPrintElement element, byte defaultMode)
	{
		if (element.getOwnMode() != null) 
			return element.getOwnMode().byteValue();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getMode() != null)
			return style.getMode().byteValue();
		return defaultMode;
	}

	/**
	 *
	 */
	public static byte getMode(JRTemplateElement element, byte defaultMode)
	{
		if (element.getOwnMode() != null) 
			return element.getOwnMode().byteValue();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getMode() != null)
			return style.getMode().byteValue();
		return defaultMode;
	}

	/**
	 *
	 */
	public static Byte getMode(JRStyle style)
	{
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.getMode();
		return null;
	}

	/**
	 *
	 */
	public static Color getForecolor(JRElement element)
	{
		if (element.getOwnForecolor() != null) 
			return element.getOwnForecolor();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getForecolor() != null)
			return style.getForecolor();
		return Color.black;
	}

	/**
	 *
	 */
	public static Color getForecolor(JRPrintElement element)
	{
		if (element.getOwnForecolor() != null) 
			return element.getOwnForecolor();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getForecolor() != null)
			return style.getForecolor();
		return Color.black;
	}

	/**
	 *
	 */
	public static Color getForecolor(JRTemplateElement element)
	{
		if (element.getOwnForecolor() != null) 
			return element.getOwnForecolor();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getOwnForecolor() != null)
			return style.getOwnForecolor();
		return Color.black;
	}

	/**
	 *
	 */
	public static Color getForecolor(JRStyle style)
	{
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.getForecolor();
		return null;
	}

	/**
	 *
	 */
	public static Color getBackcolor(JRElement element)
	{
		if (element.getOwnBackcolor() != null) 
			return element.getOwnBackcolor();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getBackcolor() != null)
			return style.getBackcolor();
		return Color.white;
	}

	/**
	 *
	 */
	public static Color getBackcolor(JRPrintElement element)
	{
		if (element.getOwnBackcolor() != null) 
			return element.getOwnBackcolor();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getBackcolor() != null)
			return style.getBackcolor();
		return Color.white;
	}

	/**
	 *
	 */
	public static Color getBackcolor(JRTemplateElement element)
	{
		if (element.getOwnBackcolor() != null) 
			return element.getOwnBackcolor();
		JRStyle style = getBaseStyle(element);
		if (style != null && style.getOwnBackcolor() != null)
			return style.getOwnBackcolor();
		return Color.white;
	}

	/**
	 *
	 */
	public static Color getBackcolor(JRStyle style)
	{
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.getBackcolor();
		return null;
	}

	/**
	 *
	 */
	public static String getFontName(JRFont font)
	{
		if (font.getOwnFontName() != null)
			return font.getOwnFontName();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null && baseFont.getFontName() != null)
			return baseFont.getFontName();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.getFontName() != null)
			return baseStyle.getFontName();
		return JRFont.DEFAULT_FONT_NAME;
	}
	
	/**
	 *
	 */
	public static String getFontName(JRStyle style)
	{
		if (style.getOwnFontName() != null)
			return style.getOwnFontName();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null && baseStyle.getFontName() != null)
			return baseStyle.getFontName();
		return JRFont.DEFAULT_FONT_NAME;
	}

	/**
	 *
	 */
	public static boolean isBold(JRFont font)
	{
		if (font.isOwnBold() != null)
			return font.isOwnBold().booleanValue();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null)
			return baseFont.isBold();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.isBold() != null)
			return baseStyle.isBold().booleanValue();
		return JRFont.DEFAULT_FONT_BOLD;
	}
	
	/**
	 *
	 */
	public static Boolean isBold(JRStyle style)
	{
		if (style.isOwnBold() != null)
			return style.isOwnBold();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.isBold();
		return null;
	}

	/**
	 *
	 */
	public static boolean isItalic(JRFont font)
	{
		if (font.isOwnItalic() != null)
			return font.isOwnItalic().booleanValue();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null)
			return baseFont.isItalic();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.isItalic() != null)
			return baseStyle.isItalic().booleanValue();
		return JRFont.DEFAULT_FONT_ITALIC;
	}
	
	/**
	 *
	 */
	public static Boolean isItalic(JRStyle style)
	{
		if (style.isOwnItalic() != null)
			return style.isOwnItalic();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.isItalic();
		return null;
	}

	/**
	 *
	 */
	public static boolean isUnderline(JRFont font)
	{
		if (font.isOwnUnderline() != null)
			return font.isOwnUnderline().booleanValue();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null)
			return baseFont.isUnderline();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.isUnderline() != null)
			return baseStyle.isUnderline().booleanValue();
		return JRFont.DEFAULT_FONT_UNDERLINE;
	}
	
	/**
	 *
	 */
	public static Boolean isUnderline(JRStyle style)
	{
		if (style.isOwnUnderline() != null)
			return style.isOwnUnderline();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.isUnderline();
		return null;
	}

	/**
	 *
	 */
	public static boolean isStrikeThrough(JRFont font)
	{
		if (font.isOwnStrikeThrough() != null)
			return font.isOwnStrikeThrough().booleanValue();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null)
			return baseFont.isStrikeThrough();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.isStrikeThrough() != null)
			return baseStyle.isStrikeThrough().booleanValue();
		return JRFont.DEFAULT_FONT_STRIKETHROUGH;
	}
	
	/**
	 *
	 */
	public static Boolean isStrikeThrough(JRStyle style)
	{
		if (style.isOwnStrikeThrough() != null)
			return style.isOwnStrikeThrough();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.isStrikeThrough();
		return null;
	}

	/**
	 *
	 */
	public static int getFontSize(JRFont font)
	{
		if (font.getOwnFontSize() != null)
			return font.getOwnFontSize().intValue();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null)
			return baseFont.getFontSize();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.getFontSize() != null)
			return baseStyle.getFontSize().intValue();
		return JRFont.DEFAULT_FONT_SIZE;
	}
	
	/**
	 *
	 */
	public static Integer getFontSize(JRStyle style)
	{
		if (style.getOwnFontSize() != null)
			return style.getOwnFontSize();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.getOwnFontSize();
		return null;
	}

	/**
	 *
	 */
	public static String getPdfFontName(JRFont font)
	{
		if (font.getOwnPdfFontName() != null)
			return font.getOwnPdfFontName();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null && baseFont.getPdfFontName() != null)
			return baseFont.getPdfFontName();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.getPdfFontName() != null)
			return baseStyle.getPdfFontName();
		return JRFont.DEFAULT_PDF_FONT_NAME;
	}
	
	/**
	 *
	 */
	public static String getPdfFontName(JRStyle style)
	{
		if (style.getOwnPdfFontName() != null)
			return style.getOwnPdfFontName();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null && baseStyle.getPdfFontName() != null)
			return baseStyle.getPdfFontName();
		return JRFont.DEFAULT_PDF_FONT_NAME;
	}

	/**
	 *
	 */
	public static String getPdfEncoding(JRFont font)
	{
		if (font.getOwnPdfEncoding() != null)
			return font.getOwnPdfEncoding();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null && baseFont.getPdfEncoding() != null)
			return baseFont.getPdfEncoding();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.getPdfEncoding() != null)
			return baseStyle.getPdfEncoding();
		return JRFont.DEFAULT_PDF_ENCODING;
	}
	
	/**
	 *
	 */
	public static String getPdfEncoding(JRStyle style)
	{
		if (style.getOwnPdfEncoding() != null)
			return style.getOwnPdfEncoding();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null && baseStyle.getPdfEncoding() != null)
			return baseStyle.getPdfEncoding();
		return JRFont.DEFAULT_PDF_ENCODING;
	}

	/**
	 *
	 */
	public static boolean isPdfEmbedded(JRFont font)
	{
		if (font.isOwnPdfEmbedded() != null)
			return font.isOwnPdfEmbedded().booleanValue();
		JRFont baseFont = getBaseFont(font);
		if (baseFont != null)
			return baseFont.isPdfEmbedded();
		JRStyle baseStyle = getBaseStyle(font);
		if (baseStyle != null && baseStyle.isPdfEmbedded() != null)
			return baseStyle.isPdfEmbedded().booleanValue();
		return JRFont.DEFAULT_PDF_EMBEDDED;
	}
	
	/**
	 *
	 */
	public static Boolean isPdfEmbedded(JRStyle style)
	{
		if (style.isOwnPdfEmbedded() != null)
			return style.isOwnPdfEmbedded();
		JRStyle baseStyle = getBaseStyle(style);
		if (baseStyle != null)
			return baseStyle.isPdfEmbedded();
		return null;
	}

}
