package info.kapable.utils.KOrchestrator.FlowRepository;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import info.kapable.utils.KOrchestrator.domain.*;

public class InMemoryFlowRepository extends AbstractFlowRepository {

	private List<Flow> flows;
	
	public InMemoryFlowRepository(File configDirectory) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super();
		this.flows = new ArrayList<Flow>();
		for(String flowFile: configDirectory.list()) {
			if(flowFile.contentEquals(".") || flowFile.contentEquals("..")) {
				continue;
			}
			if(!flowFile.contains("flow")) {
				continue;
			}
			this.flows.add(FlowFactory.fromFile(configDirectory.getAbsolutePath() + "/" + flowFile));
		}
	}

	public void runAll() {
		for(Flow flow: this.flows) {
			flow.run();
		}
	}

}
