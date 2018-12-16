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
package com.tcdng.unify.web.ui.panel;

/**
 * Remote document view data object.
 * 
 * @author Lateef Ojulari
 * @version 1.0
 */
public class RemoteDocViewInfo {

    private String pageTitle;

    private String remoteDocUrl;

    private String remoteSaveUrl;

    public RemoteDocViewInfo(String pageTitle, String remoteDocUrl, String remoteSaveUrl) {
        this.pageTitle = pageTitle;
        this.remoteDocUrl = remoteDocUrl;
        this.remoteSaveUrl = remoteSaveUrl;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getRemoteDocUrl() {
        return remoteDocUrl;
    }

    public String getRemoteSaveUrl() {
        return remoteSaveUrl;
    }

}
