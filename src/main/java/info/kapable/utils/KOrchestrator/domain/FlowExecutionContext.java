package info.kapable.utils.KOrchestrator.domain;

import java.util.Properties;

public class FlowExecutionContext extends AbstractEntity {

	Properties contextProperties;
	
	public Properties getContextProperties() {
		return contextProperties;
	}
	
	public void setContextProperties(Properties contextProperties) {
		this.contextProperties = contextProperties;
	}
	
	public FlowExecutionContext() {
		contextProperties = new Properties();
	}
	
	public void merge(FlowExecutionContext ctx) {
		contextProperties.putAll(ctx.getContextProperties());
	}
	
	public String toString() {
		return this.contextProperties.toString();
	}
	
	public void store(Action a, String type, String txtToStore) {
		this.contextProperties.put("action." + a.EntityUUID.toString() + "." + type, txtToStore);
	}

	public void log(Action a, String txt) {
		this.store(a, "log", txt);
	}

	public void addAlias(Action action, String string) {
		contextProperties.put("alias." + string, action.EntityUUID.toString());
	}

	public String getAlias(String key, String type) {
		String uuid = (String) contextProperties.get("alias." + key);
		
		return contextProperties.getProperty("action." + uuid + "." + type);
	}
}
