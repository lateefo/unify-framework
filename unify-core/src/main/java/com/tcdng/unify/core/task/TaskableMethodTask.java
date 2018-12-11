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
package com.tcdng.unify.core.task;

import com.tcdng.unify.core.UnifyComponent;
import com.tcdng.unify.core.UnifyException;
import com.tcdng.unify.core.annotation.Component;

/**
 * Taskable method task.
 * 
 * @author Lateef Ojulari
 * @since 1.0
 */
@Component(TaskableMethodConstants.TASKABLE_METHOD_TASK)
public class TaskableMethodTask extends AbstractTask {

	@Override
	public TaskInstanceInfo getTaskInstanceInfo(TaskInput input) throws UnifyException {
		TaskableMethodConfig tmc = input.getTmc();
		String executionId = input.getOrigTaskName();
		if (tmc.getIdGenerator() != null) {
			TaskIdGenerator taskIdGenerator = (TaskIdGenerator) getComponent(tmc.getIdGenerator());
			executionId = taskIdGenerator.generateID(input);
		}

		return new TaskInstanceInfo(tmc.getTaskExecLimit(), executionId);
	}

	@Override
	public void execute(TaskMonitor taskMonitor, TaskInput input, TaskOutput output) throws UnifyException {
		try {
			TaskableMethodConfig tmc = input.getTmc();
			Object[] params = new Object[tmc.getParamCount() + 1];
			params[0] = taskMonitor;
			int i = 1; // Skip task monitor
			for (TaskableMethodConfig.ParamConfig pc : tmc.getParamConfigList()) {
				params[i] = input.getParam(pc.getType(), pc.getParamName());
				i++;
			}

			UnifyComponent component = getComponent(tmc.getComponentName());
			Object result = tmc.getMethod().invoke(component, params);
			output.setResult(TaskableMethodConstants.TASK_RESULT, result);
		} catch (UnifyException e) {
			throw e;
		} catch (Exception e) {
			throwOperationErrorException(e);
		}
	}

}
