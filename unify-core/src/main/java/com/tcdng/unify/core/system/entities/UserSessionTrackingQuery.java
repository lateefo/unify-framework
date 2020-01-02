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
package com.tcdng.unify.core.system.entities;

import java.util.Collection;
import java.util.Date;

import com.tcdng.unify.core.database.Query;

/**
 * User session tracking query.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public class UserSessionTrackingQuery extends Query<UserSessionTracking> {

    public UserSessionTrackingQuery() {
        super(UserSessionTracking.class);
    }

    public UserSessionTrackingQuery id(String id) {
        return (UserSessionTrackingQuery) addEquals("sessionId", id);
    }

    public UserSessionTrackingQuery idNot(String id) {
        return (UserSessionTrackingQuery) addNotEqual("sessionId", id);
    }

    public UserSessionTrackingQuery idAmongst(Collection<String> ids) {
        return (UserSessionTrackingQuery) addAmongst("sessionId", ids);
    }

    public UserSessionTrackingQuery userLoginId(String userLoginId) {
        return (UserSessionTrackingQuery) addEquals("userLoginId", userLoginId);
    }

    public UserSessionTrackingQuery node(String node) {
        return (UserSessionTrackingQuery) addEquals("node", node);
    }

    public UserSessionTrackingQuery loggedIn() {
        return (UserSessionTrackingQuery) addIsNotNull("userLoginId");
    }

    public UserSessionTrackingQuery notLoggedIn() {
        return (UserSessionTrackingQuery) addIsNull("userLoginId");
    }

    public UserSessionTrackingQuery expired(Date expirationDt) {
        return (UserSessionTrackingQuery) addLessThan("lastAccessTime", expirationDt);
    }
}
