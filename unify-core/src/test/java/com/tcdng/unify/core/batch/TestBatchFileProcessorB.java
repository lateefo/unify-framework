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
package com.tcdng.unify.core.batch;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.business.BusinessLogicInput;

/**
 * Test batch file processor B.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component("test-batchfileprocessor-b")
public class TestBatchFileProcessorB extends AbstractDBBatchFileReadProcessor<TestBatchRecordB, TestBatchItemRecordB> {

    public TestBatchFileProcessorB() {
        super(TestBatchRecordB.class, TestBatchItemRecordB.class);
    }

    @Override
    public String getBatchCategory(TestBatchItemRecordB batchItem) throws UnifyException {
        return ((TestBatchItemRecordB) batchItem).getCurrency();
    }

    @Override
    public void preBatchCreate(BusinessLogicInput input, TestBatchRecordB batch, TestBatchItemRecordB batchItem)
            throws UnifyException {
        TestBatchRecordB testBatchRecordB = (TestBatchRecordB) batch;
        testBatchRecordB.setItemCount(Integer.valueOf(1));
        testBatchRecordB.setTotalAmount(((TestBatchItemRecordB) batchItem).getAmount());
    }

    @Override
    public void preBatchUpdate(BusinessLogicInput input, TestBatchRecordB batch, TestBatchItemRecordB batchItem)
            throws UnifyException {
        TestBatchRecordB testBatchRecordB = (TestBatchRecordB) batch;
        testBatchRecordB.setItemCount(Integer.valueOf(testBatchRecordB.getItemCount().intValue() + 1));
        testBatchRecordB.setTotalAmount(Double.valueOf(testBatchRecordB.getTotalAmount().doubleValue()
                + ((TestBatchItemRecordB) batchItem).getAmount().doubleValue()));
    }

    @Override
    protected void postBatchCreate(BusinessLogicInput input, TestBatchRecordB batch) throws UnifyException {

    }
}
