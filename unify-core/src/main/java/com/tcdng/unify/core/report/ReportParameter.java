/*
 * Copyright 2018-2019 The Code Department.
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

package com.tcdng.unify.core.report;

/**
 * Report parameter.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class ReportParameter {

    private String name;

    private String description;

    private String formatter;

    private Object value;

    private boolean headerDetail;

    private boolean footerDetail;

    public ReportParameter(String name, String description, String formatter, Object value, boolean headerDetail,
            boolean footerDetail) {
        this.name = name;
        this.description = description;
        this.formatter = formatter;
        this.value = value;
        this.headerDetail = headerDetail;
        this.footerDetail = footerDetail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFormatter() {
        return formatter;
    }

    public Object getValue() {
        return value;
    }

    public boolean isHeaderDetail() {
        return headerDetail;
    }

    public boolean isFooterDetail() {
        return footerDetail;
    }

}