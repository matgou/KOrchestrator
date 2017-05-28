package info.kapable.utils.KOrchestrator.actions;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;
import info.kapable.utils.KOrchestrator.annotations.KOrchestratorFlowAction;
import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;
import info.kapable.utils.KOrchestrator.main.KOrchestrator;

@KOrchestratorFlowAction("run_flow")
public class RunFlow extends Action {

	private String[] flowToRun;
	
	public RunFlow(Flow flow, String cmd) {
		super(flow, cmd);
		flowToRun = cmd.split(" ");
	}

	@Override
	public void run() throws RunActionException {
		KOrchestrator.inMemoryFlowRepository.run(flowToRun[1]);
	}

}
