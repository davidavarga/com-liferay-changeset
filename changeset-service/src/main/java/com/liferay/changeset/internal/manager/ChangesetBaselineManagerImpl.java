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

package com.liferay.changeset.internal.manager;

import com.liferay.changeset.configuration.ChangesetConfiguration;
import com.liferay.changeset.constants.ChangesetConstants;
import com.liferay.changeset.manager.ChangesetBaselineManager;
import com.liferay.changeset.model.ChangesetBaselineCollection;
import com.liferay.changeset.model.ChangesetBaselineEntry;
import com.liferay.changeset.service.ChangesetBaselineCollectionLocalService;
import com.liferay.changeset.service.ChangesetBaselineEntryLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ChangesetBaselineManager.class)
public class ChangesetBaselineManagerImpl implements ChangesetBaselineManager {

	@Override
	public boolean containsSnapshot() {
		return false;
	}

	@Override
	public void createBaseline(
		Supplier<? extends Serializable> baselineIdSupplier) {

		createBaseline(baselineIdSupplier, null);
	}

	@Override
	public void createBaseline(
		Supplier<? extends Serializable> baselineIdSupplier,
		ChangesetBaselineCollection copyChangesetBaselineCollection) {

		User defaultUser = null;

		try {
			defaultUser = _userLocalService.getDefaultUser(
				CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException pe) {
			_log.error("Unable to get default user", pe);

			throw new IllegalStateException(
				"Unable to determine default user", pe);
		}

		final ChangesetBaselineCollection changesetBaselineCollection =
			_changesetBaselineCollectionLocalService.
				addChangesetBaselineCollection(
					defaultUser.getUserId(),
					String.valueOf(baselineIdSupplier.get()));

		if (copyChangesetBaselineCollection == null) {
			_addDefaultBaselineVersions(changesetBaselineCollection);
		}
		else {
			List<ChangesetBaselineEntry> changesetBaselineEntries =
				_changesetBaselineEntryLocalService.getChangesetBaselineEntries(
					copyChangesetBaselineCollection.
						getChangesetBaselineCollectionId());

			changesetBaselineEntries.forEach(
				changesetBaselineEntry ->
					_changesetBaselineEntryLocalService.
						addChangesetBaselineEntry(
							changesetBaselineCollection.
								getChangesetBaselineCollectionId(),
							changesetBaselineEntry.getClassNameId(),
							changesetBaselineEntry.getClassPK(),
							changesetBaselineEntry.getVersion()));
		}
	}

	@Override
	public void getBaselineState(long changesetBaselineId) {
	}

	public double getBaselineVersion(
		long changesetBaselineId, long classNameId, long classPK) {

		return 0;
	}

	@Override
	public Optional<ChangesetBaselineCollection> getChangesetBaselineCollection(
		Supplier<? extends Serializable> baselineIdSupplier) {

		return _changesetBaselineCollectionLocalService.
			getChangesetBaselineCollectionByName(
				String.valueOf(baselineIdSupplier.get()));
	}

	@Override
	public Optional<ChangesetBaselineCollection> getProductionBaseline() {
		return getChangesetBaselineCollection(
			() -> ChangesetConstants.PRODUCTION_BASELINE_NAME);
	}

	@Override
	public void removeBaseline(
		Supplier<? extends Serializable> baselineIdSupplier) {
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, unbind = "-")
	protected void addChangesetConfiguration(
		ChangesetConfiguration changesetConfiguration) {

		_changesetConfigurations.add(changesetConfiguration);
	}

	private void _addDefaultBaselineVersions(
		ChangesetBaselineCollection changesetBaselineCollection) {

		Stream<ChangesetConfiguration<?, ?>> stream =
			_changesetConfigurations.parallelStream();

		stream.filter(
			Objects::nonNull
		).map(
			ChangesetConfiguration::getBaselining
		).flatMap(
			List::stream
		).map(
			Supplier::get
		).flatMap(
			Collection::stream
		).map(
			object -> (ClassedModel)object
		).forEach(
			classedModel -> _changesetBaselineEntryLocalService.
				addChangesetBaselineEntry(
					changesetBaselineCollection.
						getChangesetBaselineCollectionId(),
					_portal.getClassNameId(classedModel.getModelClassName()),
					(long)classedModel.getPrimaryKeyObj(), 1.0D)
		);
	}

	private Stream<?> _getBaseLocalServiceModels(
		BaseLocalService baseLocalService) {

		try {
			Method dynamicQueryMethod = ReflectionUtil.getDeclaredMethod(
				baseLocalService.getClass(), "dynamicQuery");

			DynamicQuery dynamicQuery = (DynamicQuery)dynamicQueryMethod.invoke(
				baseLocalService);

			Property companyIdProperty = PropertyFactoryUtil.forName(
				"companyId");

			dynamicQuery.add(
				companyIdProperty.eq(CompanyThreadLocal.getCompanyId()));

			Method parameteredDynamicQueryMethod =
				ReflectionUtil.getDeclaredMethod(
					baseLocalService.getClass(), "dynamicQuery",
					DynamicQuery.class);

			List<?> list = (List<?>)parameteredDynamicQueryMethod.invoke(
				baseLocalService, dynamicQuery);

			return list.stream();
		}
		catch (Exception e) {
			_log.error("Unable to get baseline models", e);

			List<?> emptyList = Collections.emptyList();

			return emptyList.stream();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangesetBaselineManagerImpl.class);

	@Reference
	private ChangesetBaselineCollectionLocalService
		_changesetBaselineCollectionLocalService;

	@Reference
	private ChangesetBaselineEntryLocalService
		_changesetBaselineEntryLocalService;

	private final List<ChangesetConfiguration<?, ?>> _changesetConfigurations =
		new ArrayList<>();

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}