package info.kapable.utils.KOrchestrator.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface KOrchestratorFlowAction {
	String value();
}
