package info.kapable.utils.KOrchestrator.actions;

import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;

public class WriteAction extends Action {

	String text;
	String filename;
	
	public WriteAction(Flow flow, String cmd) {
		super(flow, cmd);
		String[] args = cmd.split(" ");
		text = args[1];
		filename = args[2];
	}

	@Override
	public void run() {
		System.out.println("Write in file : " + filename + ", text : " + text);
		
		// TODO
	}
	
	

}
