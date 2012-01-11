/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2011 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.repo;

import net.sf.jasperreports.data.DataAdapter;




/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: FileRepositoryService.java 4882 2012-01-09 14:54:19Z teodord $
 */
public class DefaultRepositoryPersistenceServiceFactory implements PersistenceServiceFactory
{
	private static final DefaultRepositoryPersistenceServiceFactory INSTANCE = new DefaultRepositoryPersistenceServiceFactory();
	
	/**
	 * 
	 */
	public static DefaultRepositoryPersistenceServiceFactory getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 * 
	 */
	public <K extends RepositoryService, L extends Resource, M extends PersistenceService> M getPersistenceService(Class<K> repositoryServiceType, Class<L> resourceType) 
	{
		if (DefaultRepositoryService.class.getName().equals(repositoryServiceType.getName()))
		{
			if (InputStreamResource.class.getName().equals(resourceType.getName()))
			{
				return (M)new InputStreamPersistenceService();
			}
			else if (OutputStreamResource.class.getName().equals(resourceType.getName()))
			{
				return (M)new OutputStreamPersistenceService();
			}
//			else if (ReportResource.class.getName().equals(resourceType.getName()))
//			{
//				return (M)new ReportPersistenceService();
//			}
			else if (DataAdapter.class.isAssignableFrom(resourceType))
			{
				return (M)new CastorDataAdapterPersistenceService();
			}
			return (M)new SerializedObjectPersistenceService();
		}
		return null;
	}
}
