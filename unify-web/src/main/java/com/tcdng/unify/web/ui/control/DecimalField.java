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
package com.tcdng.unify.web.ui.control;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.UplAttribute;
import com.tcdng.unify.core.annotation.UplAttributes;
import com.tcdng.unify.core.format.Formatter;
import com.tcdng.unify.core.format.NumberFormatter;

/**
 * Represents an input field for entering a decimal amount.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component("ui-decimal")
@UplAttributes({ @UplAttribute(name = "scale", type = int.class),
        @UplAttribute(name = "formatter", type = Formatter.class, defaultValue = "$d{!decimalformat}") })
public class DecimalField extends IntegerField {

    @Override
    public void onPageConstruct() throws UnifyException {
        NumberFormatter<?> numberFormatter = (NumberFormatter<?>) getFormatter();
        int scale = getUplAttribute(int.class, "scale");
        numberFormatter.setScale(scale);

        super.onPageConstruct();
    }
}
