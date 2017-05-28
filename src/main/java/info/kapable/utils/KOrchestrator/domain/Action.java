package info.kapable.utils.KOrchestrator.domain;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;

public abstract class Action extends AbstractEntity {

	private Flow flow;
	private String actionCmdText;
	
	public Action(Flow flow, String cmd) {
		this.setFlow(flow);
		this.actionCmdText = cmd;
		String aString = flow.getName() + ":" + cmd;
		this.EntityUUID = UUID.nameUUIDFromBytes(aString.getBytes());
	}
	
	public abstract FlowExecutionContext run(FlowExecutionContext initContext, String args) throws RunActionException;

	/**
	 * @return the flow
	 */
	public Flow getFlow() {
		return flow;
	}

	/**
	 * @param flow the flow to set
	 */
	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public String processAlias(FlowExecutionContext ctx) {
		final StringBuffer sb = new StringBuffer();
		String txt = actionCmdText.substring(actionCmdText.indexOf(" "));
		Pattern pattern = Pattern.compile("(@[a-zA-Z]+[.][a-zA-Z]+)",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(txt);
        while(matcher.find()) {
            String key = matcher.group(1).substring(1, matcher.group(1).indexOf("."));
            String type = matcher.group(1).substring(matcher.group(1).indexOf(".")+1);
            String replacement = ctx.getAlias(key, type);
            if(replacement == null){
                throw new IllegalArgumentException(
                   "Template contains unmapped key: "
                    + key);
            }
            matcher.appendReplacement(sb, replacement);
        }
        return sb.toString();
	}
}
