package info.kapable.utils.KOrchestrator.FlowRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import com.sun.javafx.collections.MappingChange.Map;

import info.kapable.utils.KOrchestrator.annotations.KOrchestratorFlowAction;
import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;

public class FlowFactory {
	private HashMap<String, Class> actionsMap;
	private static FlowFactory defaultFlowFactory;
	
	public static FlowFactory getDefaultFlowFactory() {
		if(defaultFlowFactory == null) {
			defaultFlowFactory = new FlowFactory();
		}
		return defaultFlowFactory;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private FlowFactory() {
		this.actionsMap = new HashMap<String, Class>();
		
		 Reflections reflections = new Reflections("info.kapable.utils.KOrchestrator");

		 Set<Class<? extends Action>> allClasses = 
		     reflections.getSubTypesOf(Action.class);
		 for(Class clazz: allClasses) {
			if (clazz.isAnnotationPresent(KOrchestratorFlowAction.class)) {
				 KOrchestratorFlowAction anno = (KOrchestratorFlowAction) clazz.getAnnotation(KOrchestratorFlowAction.class);
				 
				 this.actionsMap.put(anno.value(), clazz);
			}
		 }
		 
	}

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
	
	public Flow fromFile(String file, String flowName) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String cmd = readFile(file);
		return this.fromCmd(cmd, flowName);
	}

	@SuppressWarnings("rawtypes")
	public Flow fromCmd(String cmd, String flowName) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
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
            	String postStore = null;
            	if(line.indexOf(">") > 0) {
            		postStore = line.substring(line.indexOf(">")+1);
            		postStore = postStore.replaceAll(" ", "");
            		line = line.substring(0, line.indexOf(">"));
                }
            	Action action = (Action) actionClass.getDeclaredConstructor(cArg).newInstance(flow, line);
            	flow.addAction(action);
            	if(postStore != null) {
            		flow.addAlias(action, postStore);
            	}
            } else {
            	throw new IllegalArgumentException("No such KOrchestratorFlowAction for : " + instruction);
            }
        }

		return flow;
	}

	@SuppressWarnings("rawtypes")
	private Class getClassFor(String instruction) {
		return this.actionsMap.get(instruction);
	}

}
