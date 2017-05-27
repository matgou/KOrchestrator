package info.kapable.utils.KOrchestrator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Flow extends AbstractEntity {
	// The flow name (filename without extention.flow)
	private String name;
	
	// List of action composing the flow
	private List<Action> actions;
	
	public Flow(String name) {
		this.setName(name);
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

	public void run() {
		for(Action action: this.actions) {
			action.run();
		}
		
	}
}
