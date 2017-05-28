package info.kapable.utils.KOrchestrator.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;
import info.kapable.utils.KOrchestrator.annotations.KOrchestratorFlowAction;
import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;
import info.kapable.utils.KOrchestrator.domain.FlowExecutionContext;

@KOrchestratorFlowAction("log")
public class LogAction extends Action {

	private static final Logger logger = LoggerFactory.getLogger(LogAction.class);
	private String cmd;
	
	public LogAction(Flow flow, String cmd) {
		super(flow, cmd);
		this.cmd = cmd;
	}

	@Override
	public FlowExecutionContext run(FlowExecutionContext ctx, String args) throws RunActionException {
	    String txt = args;
		logger.info("Log : " + txt);
		ctx.log(this, txt);
		logger.info(ctx.toString());
		return ctx;
	}

}
