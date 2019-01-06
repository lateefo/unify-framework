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

import java.util.ArrayList;
import java.util.List;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.UplAttribute;
import com.tcdng.unify.core.annotation.UplAttributes;
import com.tcdng.unify.core.util.StringUtils;
import com.tcdng.unify.web.ui.AbstractMultiControl;
import com.tcdng.unify.web.ui.Control;

/**
 * Used for managing the assignment of items. Provides a view of available items
 * and already assigned items.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component("ui-assignmentbox")
@UplAttributes({ @UplAttribute(name = "filterList1", type = String.class),
        @UplAttribute(name = "filterList2", type = String.class),
        @UplAttribute(name = "assignList", type = String.class, mandatory = true),
        @UplAttribute(name = "unassignList", type = String.class, mandatory = true),
        @UplAttribute(name = "filterCaption1", type = String.class),
        @UplAttribute(name = "filterCaption2", type = String.class),
        @UplAttribute(name = "assignCaption", type = String.class, mandatory = true),
        @UplAttribute(name = "unassignCaption", type = String.class, mandatory = true),
        @UplAttribute(name = "multiSelectStyle", type = String.class) })
public class AssignmentBox extends AbstractMultiControl {

    private Control assignedSelCtrl;

    private Control unassignedSelCtrl;

    private Control filterCtrl1;

    private Control filterCtrl2;

    private Control assignCtrl;

    private Control assignAllCtrl;

    private Control unassignCtrl;

    private Control unassignAllCtrl;

    private String filterId1;

    private String filterId2;

    private List<String> assignedSelList;

    private List<String> unassignedSelList;

    private List<String> assignedIdList;

    @Override
    public void onPageInitialize() throws UnifyException {
        String filterList1 = getUplAttribute(String.class, "filterList1");
        if (!StringUtils.isBlank(filterList1)) {
            filterCtrl1 = (Control) addInternalChildControl("!ui-select styleClass:$e{abfselect} blankOption:$s{} list:"
                    + filterList1 + " binding:filterId1 popupAlways:true");
        }

        String filterList2 = getUplAttribute(String.class, "filterList2");
        if (!StringUtils.isBlank(filterList2)) {
            filterCtrl2 = (Control) addInternalChildControl("!ui-select styleClass:$e{abfselect} blankOption:$s{} list:"
                    + filterList2 + " listParams:$s{filterId1} binding:filterId2 popupAlways:true");
        }

        String msStyle = "";
        String multiSelectStyle = getUplAttribute(String.class, "multiSelectStyle");
        if (multiSelectStyle != null) {
            msStyle = "style:$s{" + multiSelectStyle + "}";
        }

        assignedSelCtrl = (Control) addInternalChildControl("!ui-multiselect styleClass:$e{abmselect} " + msStyle
                + " list:" + getUplAttribute(String.class, "assignList")
                + " listParams:$l{assignedIdList filterId1 filterId2} binding:assignedSelList");
        unassignedSelCtrl = (Control) addInternalChildControl("!ui-multiselect styleClass:$e{abmselect} " + msStyle
                + " list:" + getUplAttribute(String.class, "unassignList")
                + " listParams:$l{assignedIdList filterId1 filterId2} binding:unassignedSelList");
        assignCtrl = addInternalChildControl("!ui-button styleClass:$e{abbutton} caption:$m{button.assign}");
        assignAllCtrl = addInternalChildControl("!ui-button styleClass:$e{abbutton} caption:$m{button.assignall}");
        unassignCtrl = addInternalChildControl("!ui-button styleClass:$e{abbutton} caption:$m{button.unassign}");
        unassignAllCtrl = addInternalChildControl("!ui-button styleClass:$e{abbutton} caption:$m{button.unassignall}");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateState() throws UnifyException {
        List<String> valueList = getValue(List.class, String.class);
        if (assignedIdList != valueList) {
            assignedIdList = valueList;
        }

        if (assignedIdList == null) {
            assignedIdList = new ArrayList<String>();
        }

        if (assignedSelList != null) {
            for (String id : assignedSelList) {
                assignedIdList.remove(id);
            }

            assignedSelList = null;
        }

        if (unassignedSelList != null) {
            for (String id : unassignedSelList) {
                assignedIdList.add(id);
            }

            unassignedSelList = null;
        }

        setValue(assignedIdList);
    }

    public String getFilterCaption1() throws UnifyException {
        return getUplAttribute(String.class, "filterCaption1");
    }

    public String getFilterCaption2() throws UnifyException {
        return getUplAttribute(String.class, "filterCaption2");
    }

    public String getUnassignCaption() throws UnifyException {
        return getUplAttribute(String.class, "unassignCaption");
    }

    public String getAssignCaption() throws UnifyException {
        return getUplAttribute(String.class, "assignCaption");
    }

    public Control getFilterSel1() {
        return filterCtrl1;
    }

    public Control getFilterSel2() {
        return filterCtrl2;
    }

    public Control getAssignSel() {
        return assignedSelCtrl;
    }

    public Control getUnassignSel() {
        return unassignedSelCtrl;
    }

    public Control getAssignBtn() {
        return assignCtrl;
    }

    public Control getAssignAllBtn() {
        return assignAllCtrl;
    }

    public Control getUnassignBtn() {
        return unassignCtrl;
    }

    public Control getUnassignAllBtn() {
        return unassignAllCtrl;
    }

    public List<String> getAssignedSelList() {
        return assignedSelList;
    }

    public void setAssignedSelList(List<String> assignedSelList) {
        this.assignedSelList = assignedSelList;
    }

    public List<String> getUnassignedSelList() {
        return unassignedSelList;
    }

    public void setUnassignedSelList(List<String> unassignedSelList) {
        this.unassignedSelList = unassignedSelList;
    }

    public List<String> getAssignedIdList() {
        return assignedIdList;
    }

    public void setAssignedIdList(List<String> assignedIdList) {
        this.assignedIdList = assignedIdList;
    }

    public String getFilterId1() {
        return filterId1;
    }

    public void setFilterId1(String filterId1) {
        this.filterId1 = filterId1;
    }

    public String getFilterId2() {
        return filterId2;
    }

    public void setFilterId2(String filterId2) {
        this.filterId2 = filterId2;
    }
}
