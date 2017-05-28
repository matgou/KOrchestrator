package info.kapable.utils.KOrchestrator.domain;

import java.util.UUID;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;

public abstract class Action extends AbstractEntity {

	private Flow flow;
	private String actionCmdText;
	
	public Action(Flow flow, String cmd) {
		this.setFlow(flow);
		this.actionCmdText = cmd;
		String aString = flow.getName() + ":" + cmd;
		this.EntityUUID = UUID.nameUUIDFromBytes(aString.getBytes());
	}
	
	public abstract FlowExecutionContext run(FlowExecutionContext initContext) throws RunActionException;

	/**
	 * @return the flow
	 */
	public Flow getFlow() {
		return flow;
	}

	/**
	 * @param flow the flow to set
	 */
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
}
