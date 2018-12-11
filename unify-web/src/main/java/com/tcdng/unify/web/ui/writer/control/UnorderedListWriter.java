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

package com.tcdng.unify.web.ui.writer.control;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.Writes;
import com.tcdng.unify.core.util.DataUtils;
import com.tcdng.unify.web.ui.ResponseWriter;
import com.tcdng.unify.web.ui.Widget;
import com.tcdng.unify.web.ui.control.UnorderedList;
import com.tcdng.unify.web.ui.writer.AbstractControlWriter;

/**
 * Unordered list writer.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Writes(UnorderedList.class)
@Component("unorderedlist-writer")
public class UnorderedListWriter extends AbstractControlWriter {

	@Override
	protected void doWriteStructureAndContent(ResponseWriter writer, Widget widget) throws UnifyException {
		UnorderedList unorderedList = (UnorderedList) widget;
		writer.write("<ul");
		writeTagAttributes(writer, unorderedList);
		writer.write(">");
		String[] items = unorderedList.getItems();
		if (items == null) {
			items = DataUtils.convert(String[].class, unorderedList.getValue(), null);
		}

		if (items != null) {
			for (String item : items) {
				writer.write("<li>");
				writer.writeWithHtmlEscape(item);
				writer.write("</li>");
			}
		}

		writer.write("</ul>");
	}

}
