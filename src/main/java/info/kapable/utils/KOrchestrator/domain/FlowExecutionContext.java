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
}
