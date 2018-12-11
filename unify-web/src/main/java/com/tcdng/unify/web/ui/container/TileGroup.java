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
package com.tcdng.unify.web.ui.container;

import java.util.List;

import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;
import com.tcdng.unify.core.annotation.UplAttribute;
import com.tcdng.unify.core.annotation.UplAttributes;
import com.tcdng.unify.core.data.ValueStore;
import com.tcdng.unify.core.ui.Tile;
import com.tcdng.unify.web.ui.AbstractValueListContainer;
import com.tcdng.unify.web.ui.Control;

/**
 * A container that displays a list of tiles. The list of tiles {@link Tile} is
 * obtained from the {@link #getValue()} method, which if null, prompts the
 * widget to check the current session for a tile list.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component("ui-tilegroup")
@UplAttributes({ @UplAttribute(name = "columns", type = int.class) })
public class TileGroup extends AbstractValueListContainer<ValueStore, Tile> {

	private Control imageCtrl;

	public TileGroup() {
		super(false);
	}

	@Override
	public void onPageInitialize() throws UnifyException {
		super.onPageInitialize();
		imageCtrl = addInternalControl("!ui-image srcBinding:imageSrc binding:image");
	}

	public int getColumns() throws UnifyException {
		return getUplAttribute(int.class, "columns");
	}

	public Control getImageCtrl() {
		return imageCtrl;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Tile> getItemList() throws UnifyException {
		return (List<Tile>) getValue();
	}

	@Override
	protected ValueStore newValue(Tile tile, int index) throws UnifyException {
		return createValueStore(tile, index);
	}

	@Override
	protected void onCreateValueList(List<ValueStore> valueList) throws UnifyException {

	}
}
