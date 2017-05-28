package info.kapable.utils.KOrchestrator.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;
import info.kapable.utils.KOrchestrator.FlowRepository.FlowFactory;
import info.kapable.utils.KOrchestrator.FlowRepository.InMemoryFlowRepository;
import info.kapable.utils.KOrchestrator.domain.Flow;

public class KOrchestrator {

	public static InMemoryFlowRepository inMemoryFlowRepository;
	private static int rc;
	private static boolean testUnit = false;
	private static final Logger logger = LoggerFactory.getLogger(KOrchestrator.class);
	public static Properties config;

	/**
	 * Show Help on display
	 * @param options
	 */
	private static void help(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("TxtToMail", options);
	}

	private static void Greeting() {
		logger.info("=======================");
		logger.info("Booting KOrchestrator !");
		logger.info("=======================");
	}


	private static void exit(int rc) {
		logger.info("KOrchestrator exit with rc = " + rc);
		KOrchestrator.rc  = rc;
		if(KOrchestrator.testUnit  == false) {
			System.exit(rc);
		}
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		Greeting();
		
		// load default config
		InputStream defaultConfigInput = KOrchestrator.class.getClassLoader().getResourceAsStream("config.properties");
		Properties config = new Properties();
		config.load(defaultConfigInput);
		
		// Parsing Option, sea apache.commons.cli
		Options options = new Options();

		options.addOption("c", "config", true, "Properties file");
		options.addOption("i", "interactive", false, "run KOrchestrator in interactive mode");
		options.addOption("e", "execute", true, "run a command with KOrchestrator");
		options.addOption("help", false, "Print help");


		CommandLineParser parser = new org.apache.commons.cli.DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args, true);
			// Help option => display help and exit != 0
			if (cmd.hasOption("help")) {
				help(options);
				exit(1);
			}
			if(cmd.hasOption("config")) {
				String configFile = cmd.getOptionValue("config");
				InputStream input = new FileInputStream(configFile);
				// load a properties file
				Properties customConfig = new Properties();
				customConfig.load(input);
				config.putAll(customConfig);
			}

			// init repository
			File configDirectory = new File(config.getProperty("flow.directory", "config.d"));
			if(configDirectory.isDirectory()) {
				inMemoryFlowRepository = new InMemoryFlowRepository(configDirectory);
			}
			
			if(cmd.hasOption("execute")) {
				if(cmd.hasOption("interactive")) {
					throw new ParseException("interactive mode can't be combined with execute mode");
				}
				try {
					execute(cmd.getOptionValue("execute"));
				} catch (RunActionException e) {
					exit(255);
				}
				exit(0);
			}
			if(cmd.hasOption("interactive")) {
				throw new ParseException("interactive mode is not supported yet");
			}
			

		} catch (ParseException e) {
			// Handle ParsingException
			System.err.println("Parsing failed.  Reason: " + e.getMessage());
			help(options);
			exit(1);
		}
		
		exit(0);
	}

	private static void execute(String cmd) throws RunActionException {
		logger.info("Executing single command: " + cmd);
		Flow singleCommandFlow;
		try {
			singleCommandFlow = FlowFactory.getDefaultFlowFactory().fromCmd(cmd, "singleCommandFlow");
			singleCommandFlow.run();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | IOException e) {
			logger.error("Probleme while interpreting command", e);
		}
	}
}
