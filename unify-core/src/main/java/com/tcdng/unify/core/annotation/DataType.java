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

package com.tcdng.unify.core.annotation;

import com.tcdng.unify.core.constant.EnumConst;
import com.tcdng.unify.core.util.EnumUtils;

/**
 * Data type enumeration.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public enum DataType implements EnumConst {

    AUTO(ColumnType.AUTO),
    CHARACTER(ColumnType.CHARACTER),
    BOOLEAN(ColumnType.BOOLEAN),
    DATE(ColumnType.DATE),
    DECIMAL(ColumnType.DECIMAL),
    DOUBLE(ColumnType.DOUBLE),
    FLOAT(ColumnType.FLOAT),
    SHORT(ColumnType.SHORT),
    INTEGER(ColumnType.INTEGER),
    LONG(ColumnType.LONG),
    STRING(ColumnType.STRING),
    TIMESTAMP_UTC(ColumnType.TIMESTAMP_UTC),
    TIMESTAMP(ColumnType.TIMESTAMP),
    ENUMCONST(ColumnType.ENUMCONST);

    private final ColumnType columnType;

    private DataType(ColumnType columnType) {
        this.columnType = columnType;
    }

    @Override
    public String code() {
        return columnType.code();
    }

    public static DataType fromCode(String code) {
        return EnumUtils.fromCode(DataType.class, code);
    }

    public static DataType fromName(String name) {
        return EnumUtils.fromName(DataType.class, name);
    }

}
