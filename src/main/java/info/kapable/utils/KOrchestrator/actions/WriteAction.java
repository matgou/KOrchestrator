package info.kapable.utils.KOrchestrator.actions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;
import info.kapable.utils.KOrchestrator.annotations.KOrchestratorFlowAction;
import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;
import info.kapable.utils.KOrchestrator.domain.FlowExecutionContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KOrchestratorFlowAction("write")
public class WriteAction extends Action {

	private static final Logger logger = LoggerFactory.getLogger(WriteAction.class);
	
	String text;
	String filename;
	
	public WriteAction(Flow flow, String cmd) {
		super(flow, cmd);
		String[] args = cmd.split(" ");
		text = args[1];
		filename = args[2];
	}

	@Override
	public FlowExecutionContext run(FlowExecutionContext initContext) throws RunActionException {
		logger.info(this.getFlow().toString() + "=> write");
		logger.debug("Write in file : " + filename + ", text : " + text);
		try {
			Files.write(Paths.get(filename), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RunActionException(e);
		}
		return initContext;
	}
	
	

}
