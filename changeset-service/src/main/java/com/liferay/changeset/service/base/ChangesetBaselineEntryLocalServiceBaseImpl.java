/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.changeset.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.changeset.service.persistence.ChangesetBaselineCollectionPersistence;
import com.liferay.changeset.service.persistence.ChangesetBaselineEntryPersistence;
import com.liferay.changeset.service.persistence.ChangesetCollectionPersistence;
import com.liferay.changeset.service.persistence.ChangesetEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the changeset baseline entry local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.changeset.service.impl.ChangesetBaselineEntryLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.changeset.service.impl.ChangesetBaselineEntryLocalServiceImpl
 * @see com.liferay.changeset.service.ChangesetBaselineEntryLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class ChangesetBaselineEntryLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements ChangesetBaselineEntryLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.changeset.service.ChangesetBaselineEntryLocalServiceUtil} to access the changeset baseline entry local service.
	 */

	/**
	 * Adds the changeset baseline entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetBaselineEntry the changeset baseline entry
	 * @return the changeset baseline entry that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ChangesetBaselineEntry addChangesetBaselineEntry(
		ChangesetBaselineEntry changesetBaselineEntry) {
		changesetBaselineEntry.setNew(true);

		return changesetBaselineEntryPersistence.update(changesetBaselineEntry);
	}

	/**
	 * Creates a new changeset baseline entry with the primary key. Does not add the changeset baseline entry to the database.
	 *
	 * @param changesetBaselineEntryId the primary key for the new changeset baseline entry
	 * @return the new changeset baseline entry
	 */
	@Override
	@Transactional(enabled = false)
	public ChangesetBaselineEntry createChangesetBaselineEntry(
		long changesetBaselineEntryId) {
		return changesetBaselineEntryPersistence.create(changesetBaselineEntryId);
	}

	/**
	 * Deletes the changeset baseline entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetBaselineEntryId the primary key of the changeset baseline entry
	 * @return the changeset baseline entry that was removed
	 * @throws PortalException if a changeset baseline entry with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ChangesetBaselineEntry deleteChangesetBaselineEntry(
		long changesetBaselineEntryId) throws PortalException {
		return changesetBaselineEntryPersistence.remove(changesetBaselineEntryId);
	}

	/**
	 * Deletes the changeset baseline entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param changesetBaselineEntry the changeset baseline entry
	 * @return the changeset baseline entry that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public ChangesetBaselineEntry deleteChangesetBaselineEntry(
		ChangesetBaselineEntry changesetBaselineEntry) {
		return changesetBaselineEntryPersistence.remove(changesetBaselineEntry);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(ChangesetBaselineEntry.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return changesetBaselineEntryPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return changesetBaselineEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return changesetBaselineEntryPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return changesetBaselineEntryPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return changesetBaselineEntryPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public ChangesetBaselineEntry fetchChangesetBaselineEntry(
		long changesetBaselineEntryId) {
		return changesetBaselineEntryPersistence.fetchByPrimaryKey(changesetBaselineEntryId);
	}

	/**
	 * Returns the changeset baseline entry with the primary key.
	 *
	 * @param changesetBaselineEntryId the primary key of the changeset baseline entry
	 * @return the changeset baseline entry
	 * @throws PortalException if a changeset baseline entry with the primary key could not be found
	 */
	@Override
	public ChangesetBaselineEntry getChangesetBaselineEntry(
		long changesetBaselineEntryId) throws PortalException {
		return changesetBaselineEntryPersistence.findByPrimaryKey(changesetBaselineEntryId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(changesetBaselineEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(ChangesetBaselineEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"changesetBaselineEntryId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(changesetBaselineEntryLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(ChangesetBaselineEntry.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"changesetBaselineEntryId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(changesetBaselineEntryLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(ChangesetBaselineEntry.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"changesetBaselineEntryId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return changesetBaselineEntryLocalService.deleteChangesetBaselineEntry((ChangesetBaselineEntry)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return changesetBaselineEntryPersistence.findByPrimaryKey(primaryKeyObj);
	}

	@Override
	public List<?extends PersistedModel> getPersistedModel(long resourcePrimKey)
		throws PortalException {
		return changesetBaselineEntryPersistence.findByResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Returns a range of all the changeset baseline entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetBaselineEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of changeset baseline entries
	 * @param end the upper bound of the range of changeset baseline entries (not inclusive)
	 * @return the range of changeset baseline entries
	 */
	@Override
	public List<ChangesetBaselineEntry> getChangesetBaselineEntries(int start,
		int end) {
		return changesetBaselineEntryPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of changeset baseline entries.
	 *
	 * @return the number of changeset baseline entries
	 */
	@Override
	public int getChangesetBaselineEntriesCount() {
		return changesetBaselineEntryPersistence.countAll();
	}

	/**
	 * Updates the changeset baseline entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param changesetBaselineEntry the changeset baseline entry
	 * @return the changeset baseline entry that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ChangesetBaselineEntry updateChangesetBaselineEntry(
		ChangesetBaselineEntry changesetBaselineEntry) {
		return changesetBaselineEntryPersistence.update(changesetBaselineEntry);
	}

	/**
	 * Returns the changeset baseline collection local service.
	 *
	 * @return the changeset baseline collection local service
	 */
	public com.liferay.changeset.service.ChangesetBaselineCollectionLocalService getChangesetBaselineCollectionLocalService() {
		return changesetBaselineCollectionLocalService;
	}

	/**
	 * Sets the changeset baseline collection local service.
	 *
	 * @param changesetBaselineCollectionLocalService the changeset baseline collection local service
	 */
	public void setChangesetBaselineCollectionLocalService(
		com.liferay.changeset.service.ChangesetBaselineCollectionLocalService changesetBaselineCollectionLocalService) {
		this.changesetBaselineCollectionLocalService = changesetBaselineCollectionLocalService;
	}

	/**
	 * Returns the changeset baseline collection persistence.
	 *
	 * @return the changeset baseline collection persistence
	 */
	public ChangesetBaselineCollectionPersistence getChangesetBaselineCollectionPersistence() {
		return changesetBaselineCollectionPersistence;
	}

	/**
	 * Sets the changeset baseline collection persistence.
	 *
	 * @param changesetBaselineCollectionPersistence the changeset baseline collection persistence
	 */
	public void setChangesetBaselineCollectionPersistence(
		ChangesetBaselineCollectionPersistence changesetBaselineCollectionPersistence) {
		this.changesetBaselineCollectionPersistence = changesetBaselineCollectionPersistence;
	}

	/**
	 * Returns the changeset baseline entry local service.
	 *
	 * @return the changeset baseline entry local service
	 */
	public ChangesetBaselineEntryLocalService getChangesetBaselineEntryLocalService() {
		return changesetBaselineEntryLocalService;
	}

	/**
	 * Sets the changeset baseline entry local service.
	 *
	 * @param changesetBaselineEntryLocalService the changeset baseline entry local service
	 */
	public void setChangesetBaselineEntryLocalService(
		ChangesetBaselineEntryLocalService changesetBaselineEntryLocalService) {
		this.changesetBaselineEntryLocalService = changesetBaselineEntryLocalService;
	}

	/**
	 * Returns the changeset baseline entry persistence.
	 *
	 * @return the changeset baseline entry persistence
	 */
	public ChangesetBaselineEntryPersistence getChangesetBaselineEntryPersistence() {
		return changesetBaselineEntryPersistence;
	}

	/**
	 * Sets the changeset baseline entry persistence.
	 *
	 * @param changesetBaselineEntryPersistence the changeset baseline entry persistence
	 */
	public void setChangesetBaselineEntryPersistence(
		ChangesetBaselineEntryPersistence changesetBaselineEntryPersistence) {
		this.changesetBaselineEntryPersistence = changesetBaselineEntryPersistence;
	}

	/**
	 * Returns the changeset collection local service.
	 *
	 * @return the changeset collection local service
	 */
	public com.liferay.changeset.service.ChangesetCollectionLocalService getChangesetCollectionLocalService() {
		return changesetCollectionLocalService;
	}

	/**
	 * Sets the changeset collection local service.
	 *
	 * @param changesetCollectionLocalService the changeset collection local service
	 */
	public void setChangesetCollectionLocalService(
		com.liferay.changeset.service.ChangesetCollectionLocalService changesetCollectionLocalService) {
		this.changesetCollectionLocalService = changesetCollectionLocalService;
	}

	/**
	 * Returns the changeset collection persistence.
	 *
	 * @return the changeset collection persistence
	 */
	public ChangesetCollectionPersistence getChangesetCollectionPersistence() {
		return changesetCollectionPersistence;
	}

	/**
	 * Sets the changeset collection persistence.
	 *
	 * @param changesetCollectionPersistence the changeset collection persistence
	 */
	public void setChangesetCollectionPersistence(
		ChangesetCollectionPersistence changesetCollectionPersistence) {
		this.changesetCollectionPersistence = changesetCollectionPersistence;
	}

	/**
	 * Returns the changeset entry local service.
	 *
	 * @return the changeset entry local service
	 */
	public com.liferay.changeset.service.ChangesetEntryLocalService getChangesetEntryLocalService() {
		return changesetEntryLocalService;
	}

	/**
	 * Sets the changeset entry local service.
	 *
	 * @param changesetEntryLocalService the changeset entry local service
	 */
	public void setChangesetEntryLocalService(
		com.liferay.changeset.service.ChangesetEntryLocalService changesetEntryLocalService) {
		this.changesetEntryLocalService = changesetEntryLocalService;
	}

	/**
	 * Returns the changeset entry persistence.
	 *
	 * @return the changeset entry persistence
	 */
	public ChangesetEntryPersistence getChangesetEntryPersistence() {
		return changesetEntryPersistence;
	}

	/**
	 * Sets the changeset entry persistence.
	 *
	 * @param changesetEntryPersistence the changeset entry persistence
	 */
	public void setChangesetEntryPersistence(
		ChangesetEntryPersistence changesetEntryPersistence) {
		this.changesetEntryPersistence = changesetEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.changeset.model.ChangesetBaselineEntry",
			changesetBaselineEntryLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.changeset.model.ChangesetBaselineEntry");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return ChangesetBaselineEntryLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return ChangesetBaselineEntry.class;
	}

	protected String getModelClassName() {
		return ChangesetBaselineEntry.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = changesetBaselineEntryPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.changeset.service.ChangesetBaselineCollectionLocalService.class)
	protected com.liferay.changeset.service.ChangesetBaselineCollectionLocalService changesetBaselineCollectionLocalService;
	@BeanReference(type = ChangesetBaselineCollectionPersistence.class)
	protected ChangesetBaselineCollectionPersistence changesetBaselineCollectionPersistence;
	@BeanReference(type = ChangesetBaselineEntryLocalService.class)
	protected ChangesetBaselineEntryLocalService changesetBaselineEntryLocalService;
	@BeanReference(type = ChangesetBaselineEntryPersistence.class)
	protected ChangesetBaselineEntryPersistence changesetBaselineEntryPersistence;
	@BeanReference(type = com.liferay.changeset.service.ChangesetCollectionLocalService.class)
	protected com.liferay.changeset.service.ChangesetCollectionLocalService changesetCollectionLocalService;
	@BeanReference(type = ChangesetCollectionPersistence.class)
	protected ChangesetCollectionPersistence changesetCollectionPersistence;
	@BeanReference(type = com.liferay.changeset.service.ChangesetEntryLocalService.class)
	protected com.liferay.changeset.service.ChangesetEntryLocalService changesetEntryLocalService;
	@BeanReference(type = ChangesetEntryPersistence.class)
	protected ChangesetEntryPersistence changesetEntryPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}