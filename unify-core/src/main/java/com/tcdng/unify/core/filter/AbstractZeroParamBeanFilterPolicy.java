/*
 * Copyright 2018-2020 The Code Department.
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
package com.tcdng.unify.core.filter;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.criterion.Restriction;
import com.tcdng.unify.core.criterion.ZeroParamRestriction;
import com.tcdng.unify.core.util.DataUtils;

/**
 * Convenient abstract base class for zero parameter bean filter policies.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public abstract class AbstractZeroParamBeanFilterPolicy implements BeanFilterPolicy {

    @Override
    public boolean match(Object bean, Restriction restriction) throws UnifyException {
        return doMatch(DataUtils.getNestedBeanProperty(bean, ((ZeroParamRestriction) restriction).getFieldName()));
    }

    protected abstract boolean doMatch(Object fieldVal) throws UnifyException;
}
