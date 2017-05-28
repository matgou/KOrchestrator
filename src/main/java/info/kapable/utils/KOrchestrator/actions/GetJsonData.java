package info.kapable.utils.KOrchestrator.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.kapable.utils.KOrchestrator.Exception.RunActionException;
import info.kapable.utils.KOrchestrator.annotations.KOrchestratorFlowAction;
import info.kapable.utils.KOrchestrator.domain.Action;
import info.kapable.utils.KOrchestrator.domain.Flow;
import info.kapable.utils.KOrchestrator.domain.FlowExecutionContext;

@KOrchestratorFlowAction("get_json_data")
public class GetJsonData extends Action {

	private String urlToRead;
	public GetJsonData(Flow flow, String cmd) {
		super(flow, cmd);
		urlToRead = cmd.substring(cmd.indexOf(" "));
	}

	@Override
	public FlowExecutionContext run(FlowExecutionContext initContext) throws RunActionException {
		try {
		    StringBuilder result = new StringBuilder();
			URL url = new URL(urlToRead);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("GET");
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      String line;
		      while ((line = rd.readLine()) != null) {
		         result.append(line);
		      }
		      rd.close();
		      initContext.store(this, "result", result.toString());
		      String json = result.toString();
		      ObjectMapper mapper = new ObjectMapper();
		      
		      Map<String, Object> map = new HashMap<String, Object>();
		      map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
		      Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		      while(it.hasNext()) {
		    	  Entry<String, Object> c = it.next();
		    	  initContext.store(this, c.getKey(), (String) c.getValue());
		      }
		} catch (MalformedURLException e) {
			throw new RunActionException(e);
		} catch (IOException e) {
			throw new RunActionException(e);
		}
		return initContext;
	}

}
