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
package com.tcdng.unify.core.format;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.UplAttribute;
import com.tcdng.unify.core.annotation.UplAttributes;

/**
 * Default money amount formatter.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component(name = "moneyformat", description = "$m{format.money}")
@UplAttributes({ @UplAttribute(name = "currency", type = String.class, mandatory = true)})
public class MoneyFormatterImpl extends AmountFormatterImpl implements MoneyFormatter {

    @Override
    public Number parse(String string) throws UnifyException {
        if (string != null) {
            return super.parse(string.substring(getUplAttribute(String.class, "currency").length()));
        }
        
        return null;
    }

    @Override
    public String format(Number value) throws UnifyException {
        if (value != null) {
            return getUplAttribute(String.class, "currency") + super.format(value);
        }
        
        return null;
    }

}