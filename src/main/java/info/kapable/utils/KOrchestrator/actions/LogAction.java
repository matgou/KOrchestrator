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
	public FlowExecutionContext run(FlowExecutionContext ctx) throws RunActionException {
	    final StringBuffer sb = new StringBuffer();
		String txt = cmd.substring(cmd.indexOf(" "));
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
        txt = sb.toString();
		logger.info("Log : " + txt);
		ctx.log(this, txt);
		logger.info(ctx.toString());
		return ctx;
	}

}
