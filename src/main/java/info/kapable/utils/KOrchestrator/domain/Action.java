package info.kapable.utils.KOrchestrator.domain;

import java.util.UUID;

public abstract class Action extends AbstractEntity {

	private Flow flow;
	private String actionCmdText;
	
	public Action(Flow flow, String cmd) {
		this.flow = flow;
		this.actionCmdText = cmd;
		String aString = flow.getName() + ":" + cmd;
		this.EntityUUID = UUID.nameUUIDFromBytes(aString.getBytes());
	}
	
	public abstract void run();
}
