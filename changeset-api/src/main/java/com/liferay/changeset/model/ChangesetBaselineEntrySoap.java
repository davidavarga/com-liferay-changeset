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

package com.liferay.changeset.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ChangesetBaselineEntrySoap implements Serializable {
	public static ChangesetBaselineEntrySoap toSoapModel(
		ChangesetBaselineEntry model) {
		ChangesetBaselineEntrySoap soapModel = new ChangesetBaselineEntrySoap();

		soapModel.setChangesetBaselineEntryId(model.getChangesetBaselineEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setChangesetBaselineCollectionId(model.getChangesetBaselineCollectionId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setResourcePrimKey(model.getResourcePrimKey());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static ChangesetBaselineEntrySoap[] toSoapModels(
		ChangesetBaselineEntry[] models) {
		ChangesetBaselineEntrySoap[] soapModels = new ChangesetBaselineEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ChangesetBaselineEntrySoap[][] toSoapModels(
		ChangesetBaselineEntry[][] models) {
		ChangesetBaselineEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ChangesetBaselineEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new ChangesetBaselineEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ChangesetBaselineEntrySoap[] toSoapModels(
		List<ChangesetBaselineEntry> models) {
		List<ChangesetBaselineEntrySoap> soapModels = new ArrayList<ChangesetBaselineEntrySoap>(models.size());

		for (ChangesetBaselineEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ChangesetBaselineEntrySoap[soapModels.size()]);
	}

	public ChangesetBaselineEntrySoap() {
	}

	public long getPrimaryKey() {
		return _changesetBaselineEntryId;
	}

	public void setPrimaryKey(long pk) {
		setChangesetBaselineEntryId(pk);
	}

	public long getChangesetBaselineEntryId() {
		return _changesetBaselineEntryId;
	}

	public void setChangesetBaselineEntryId(long changesetBaselineEntryId) {
		_changesetBaselineEntryId = changesetBaselineEntryId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getChangesetBaselineCollectionId() {
		return _changesetBaselineCollectionId;
	}

	public void setChangesetBaselineCollectionId(
		long changesetBaselineCollectionId) {
		_changesetBaselineCollectionId = changesetBaselineCollectionId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	private long _changesetBaselineEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _changesetBaselineCollectionId;
	private long _classNameId;
	private long _classPK;
	private long _resourcePrimKey;
	private double _version;
}