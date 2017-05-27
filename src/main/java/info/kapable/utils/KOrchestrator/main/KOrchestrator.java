package info.kapable.utils.KOrchestrator.main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import info.kapable.utils.KOrchestrator.FlowRepository.InMemoryFlowRepository;

public class KOrchestrator {

	public static InMemoryFlowRepository inMemoryFlowRepository;
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		System.out.println("Booting KOrchestrator !");
		File configDirectory = new File(args[0]);
		if(configDirectory.isDirectory()) {
			inMemoryFlowRepository = new InMemoryFlowRepository(configDirectory);
		}
		
		// While trigger isn't implemented
		inMemoryFlowRepository.runAll();
	}

}
