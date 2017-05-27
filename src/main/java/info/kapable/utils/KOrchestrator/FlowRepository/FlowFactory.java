package info.kapable.utils.KOrchestrator.FlowRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;

import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;

public class FlowFactory {

	private static String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
	public static Flow fromFile(String file) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String cmd = readFile(file);
		return FlowFactory.fromCmd(cmd, file);
	}

	@SuppressWarnings("rawtypes")
	private static Flow fromCmd(String cmd, String flowName) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	    String         line = null;
	    BufferedReader reader = new BufferedReader(new StringReader (cmd));
	    Flow flow = new Flow(flowName);
	    Class[] cArg = new Class[2];
	    cArg[0] = Flow.class;
	    cArg[1] = String.class;
	    
        while((line = reader.readLine()) != null) {
            String instruction = line.substring(0, line.indexOf(" "));
            Class actionClass = getClassFor(instruction);
            if(actionClass != null) {
            	Action action = (Action) actionClass.getDeclaredConstructor(cArg).newInstance(flow, line);
            	flow.addAction(action);
            }
        }

		return flow;
	}

	@SuppressWarnings("rawtypes")
	private static Class getClassFor(String instruction) {
		switch (instruction) {
			case "write":
				return info.kapable.utils.KOrchestrator.actions.WriteAction.class;
		}
		return null;
	}

}
