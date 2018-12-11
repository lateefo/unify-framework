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
package com.tcdng.unify.web.ui.writer.layout;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.Writes;
import com.tcdng.unify.core.data.ValueStore;
import com.tcdng.unify.web.ui.Container;
import com.tcdng.unify.web.ui.ResponseWriter;
import com.tcdng.unify.web.ui.TabularLayout;
import com.tcdng.unify.web.ui.Widget;
import com.tcdng.unify.web.ui.layout.VerticalLayout;

/**
 * Vertical layout writer.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Writes(VerticalLayout.class)
@Component("verticallayout-writer")
public class VerticalLayoutWriter extends AbstractTabularLayoutWriter {

	@Override
	protected void writeTableContent(ResponseWriter writer, TabularLayout layout, Container container)
			throws UnifyException {
		int rowIndex = 0;
		for (String longName : container.getLayoutWidgetLongNames()) {
			Widget widget = container.getWidgetByLongName(longName);
			if (widget.isVisible()) {
				appendRowStart(writer, layout, rowIndex);
				appendCellContent(writer, layout, widget, rowIndex, 0);
				appendRowEnd(writer);
				rowIndex++;
			} else if (widget.isHidden()) {
				writer.writeStructureAndContent(widget);
			}
		}
	}

	@Override
	protected void writeRepeatTableContent(ResponseWriter writer, TabularLayout layout, Container container)
			throws UnifyException {
		int rowIndex = 0;
		Widget widget = container.getWidgetByLongName(container.getLayoutWidgetLongNames().get(0));
		for (ValueStore valueStore : container.getRepeatValueStores()) {
			widget.setValueStore(valueStore);
			if (widget.isVisible()) {
				appendRowStart(writer, layout, rowIndex);
				appendCellContent(writer, layout, widget, rowIndex, 0);
				appendRowEnd(writer);
				rowIndex++;
			} else if (widget.isHidden()) {
				writer.writeStructureAndContent(widget);
			}
		}
	}

}
