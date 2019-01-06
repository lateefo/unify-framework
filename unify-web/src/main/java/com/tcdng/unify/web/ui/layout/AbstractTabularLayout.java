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
package com.tcdng.unify.web.ui.layout;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.UplAttribute;
import com.tcdng.unify.core.annotation.UplAttributes;
import com.tcdng.unify.web.ui.AbstractLayout;
import com.tcdng.unify.web.ui.TabularCellType;
import com.tcdng.unify.web.ui.TabularLayout;

/**
 * Abstract base class for tabular layouts.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@UplAttributes({ @UplAttribute(name = "cellType", type = TabularCellType.class, defaultValue = "top"),
        @UplAttribute(name = "cellPadding", type = boolean.class, defaultValue = "true"),
        @UplAttribute(name = "widths", type = String[].class),
        @UplAttribute(name = "heights", type = String[].class), })
public abstract class AbstractTabularLayout extends AbstractLayout implements TabularLayout {

    @Override
    public TabularCellType getCellType() throws UnifyException {
        return getUplAttribute(TabularCellType.class, "cellType");
    }

    @Override
    public boolean isCellPadding() throws UnifyException {
        return getUplAttribute(boolean.class, "cellPadding");
    }

    @Override
    public String[] getWidths() throws UnifyException {
        return getUplAttribute(String[].class, "widths");
    }

    @Override
    public String[] getHeights() throws UnifyException {
        return getUplAttribute(String[].class, "heights");
    }

}
