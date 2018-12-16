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
package com.tcdng.unify.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.web.annotation.RequestParameter;

/**
 * Mock resource controller.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component("/resource/mock")
public class MockResourceController extends AbstractResourceController {

    public MockResourceController() {
        super(false);
    }

    @RequestParameter
    private String accountNo;

    @RequestParameter
    private Double balance;

    @Override
    public void execute(OutputStream outputStream) throws UnifyException {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write("Hello World!");
            osw.flush();
        } catch (IOException e) {
            throwOperationErrorException(e);
        }
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public void setContentType(String contentType) {

    }

    @Override
    public String getResourceName() {
        return null;
    }

    @Override
    public void setResourceName(String resourceName) {

    }

    @Override
    public Set<String> getMetaDataKeys() {
        return null;
    }

    @Override
    public String getMetaData(String name) {
        return null;
    }

    @Override
    public void prepareExecution() throws UnifyException {

    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
