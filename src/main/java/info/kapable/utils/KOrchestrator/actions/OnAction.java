package info.kapable.utils.KOrchestrator.actions;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;
import info.kapable.utils.KOrchestrator.annotations.KOrchestratorFlowAction;
import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;

/**
 * This action wait a condition before continue
 */
@KOrchestratorFlowAction("on")
public class OnAction extends Action {

	public OnAction(Flow flow, String cmd) {
		super(flow, cmd);
	}

	@Override
	public void run() throws RunActionException {
		// TODO wait condition

	}

}
