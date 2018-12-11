/*
 * Copyright 2018 The Code Department
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tcdng.unify.core.database;

import java.util.ArrayList;
import java.util.List;

import com.tcdng.unify.core.annotation.ChildList;
import com.tcdng.unify.core.annotation.Column;
import com.tcdng.unify.core.annotation.Table;

/**
 * Test report record.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Table("REPORT")
public class Report extends AbstractTestEntity {

	@Column
	private String name;

	@Column
	private String description;

	@ChildList
	private List<ReportParameter> parameters;

	public Report(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Report() {

	}

	public Report addParameter(ReportParameter rp) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<ReportParameter>();
		}
		this.parameters.add(rp);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ReportParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ReportParameter> parameters) {
		this.parameters = parameters;
	}
}
