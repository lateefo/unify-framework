package com.tcdng.unify.core.task;

import com.tcdng.unify.core.AbstractUnifyComponent;
import com.tcdng.unify.core.UnifyException;

/**
 * Convenient abstract task ID generator component.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
public abstract class AbstractTaskIdGenerator extends AbstractUnifyComponent
	implements TaskIdGenerator {

	@Override
	protected void onInitialize() throws UnifyException {

	}

	@Override
	protected void onTerminate() throws UnifyException {

	}

}
