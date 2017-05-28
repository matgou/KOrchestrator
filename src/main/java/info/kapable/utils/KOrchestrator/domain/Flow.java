package info.kapable.utils.KOrchestrator.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;

public class Flow extends AbstractEntity {
	// The flow name (filename without extention.flow)
	private String name;
	
	// List of action composing the flow
	private List<Action> actions;
	private Map<String, String> aliases;
	
	public Flow(String name) {
		this.setName(name);
		this.aliases = new HashMap<String, String>();
		this.EntityUUID = UUID.nameUUIDFromBytes(name.getBytes());
		
		this.setActions(new ArrayList<Action>());
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the actions
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Add an action to flow
	 * @param action
	 */
	public void addAction(Action action) {
		this.actions.add(action);
	}
	
	/**
	 * @param actions the actions to set
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public FlowExecutionContext run() throws RunActionException {
		FlowExecutionContext ctx = new FlowExecutionContext();
		for(Action action: this.actions) {
			String args = action.processAlias(ctx);
			ctx = action.run(ctx, args);
			if(this.aliases.get(action.EntityUUID.toString()) != null) {
				ctx.addAlias(action, this.aliases.get(action.EntityUUID.toString()));
			}
		}
		
		return ctx;
	}
	
	public String toString() {
		return this.EntityUUID.toString();
	}

	public void addAlias(Action action, String alias) {
		this.aliases.put(action.EntityUUID.toString(), alias);
	}
}
