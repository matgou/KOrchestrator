package info.kapable.utils.KOrchestrator.Exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunActionException extends Exception {

	private static final long serialVersionUID = -5624837522391626574L;
	private static final Logger logger = LoggerFactory.getLogger(RunActionException.class);

	public RunActionException(IOException e) {
		super(e);
		logger.error("An error occur during processing action in flow", e);
	}

	public RunActionException(String string) {
		super(string);
		logger.error("An error occur during processing action in flow", this);
	}

}
