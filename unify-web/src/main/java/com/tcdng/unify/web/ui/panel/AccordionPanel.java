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

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.web.annotation.Action;
import com.tcdng.unify.web.ui.AbstractPanel;
import com.tcdng.unify.web.ui.Control;

/**
 * Represents an accordion.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component("ui-accordionpanel")
public class AccordionPanel extends AbstractPanel {

    private Control currentSelCtrl;

    private int currentSel;

    private int sectionCount;

    private boolean collapsed;

    @Override
    public void onPageInitialize() throws UnifyException {
        super.onPageInitialize();
        currentSelCtrl = (Control) addInternalControl("!ui-hidden binding:currentSel");
    }

    @Action
    @Override
    public void switchState() throws UnifyException {

    }

    @Action
    @Override
    public void resetState() throws UnifyException {

    }

    @Action
    public void expandContent() throws UnifyException {
        collapsed = false;
    }

    @Action
    public void collapseContent() throws UnifyException {
        collapsed = true;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public String getHeaderIdBase() throws UnifyException {
        return getPrefixedId("hdr_");
    }

    public Control getCurrentSelCtrl() {
        return currentSelCtrl;
    }

    public void clearSectionCount() {
        sectionCount = 0;
    }

    public void incrementSectionCount() {
        sectionCount++;
    }

    public int getSectionCount() {
        return sectionCount;
    }

    public int getCurrentSel() {
        return currentSel;
    }

    public void setCurrentSel(int currentSel) {
        this.currentSel = currentSel;
    }

}
