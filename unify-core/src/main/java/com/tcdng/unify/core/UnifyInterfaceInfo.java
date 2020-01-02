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
package com.tcdng.unify.core;

/**
 * Unify interface information data object.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class UnifyInterfaceInfo {

    private String name;

    private int portNumber;

    private boolean open;

    public UnifyInterfaceInfo(String name, int portNumber, boolean open) {
        this.name = name;
        this.portNumber = portNumber;
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public boolean isOpen() {
        return open;
    }
}
