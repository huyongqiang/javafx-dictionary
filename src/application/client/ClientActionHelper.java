package application.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import application.utils.MyResponse;
import application.utils.Word;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientActionHelper {
	final String SEARCH = "search", ADD = "add", DELETE = "delete";

	@SuppressWarnings("unchecked")
	public String createSearchJson(String word) {
		JSONObject obj = new JSONObject();
		obj.put("action", SEARCH);
		obj.put("word", word);

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public String createAddJson(Word word) {
		JSONObject obj = new JSONObject();
		obj.put("action", ADD);
		obj.put("word", word.getWord());
		obj.put("meaning", word.getMeaning());

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public String createDeleteJson(String word) {
		JSONObject obj = new JSONObject();
		obj.put("action", DELETE);
		obj.put("word", word);

		return obj.toJSONString();
	}

	public MyResponse handleResponse(String jsonString) {
		MyResponse response = null;
		JSONParser parser = new JSONParser();

		try {
			JSONObject object = (JSONObject) parser.parse(jsonString);
			String actionType = (String) object.get("action");
			
			if (actionType.equals(SEARCH)) {
				response = handleSearch(object);
			} else if (actionType.equals(ADD)) {
				response = handleAdd(object);
			} else if (actionType.equals(DELETE)) {
				response = handleDelete(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private MyResponse handleSearch(JSONObject obj) {
		String status = (String) obj.get("status");
		String meaning = (String) obj.get("meaning");
		String msg = (String) obj.get("msg");
		
		MyResponse response = new MyResponse(SEARCH, Integer.valueOf(status), msg, meaning);

		return response;
	}

	private synchronized MyResponse handleAdd(JSONObject obj) {
		String status = (String) obj.get("status");
		String msg = (String) obj.get("msg");
		
		MyResponse response = new MyResponse(ADD, Integer.valueOf(status), msg);
		
		return response;
	}

	private synchronized MyResponse handleDelete(JSONObject obj) {
		String status = (String) obj.get("status");
		String msg = (String) obj.get("msg");
		
		MyResponse response = new MyResponse(DELETE, Integer.valueOf(status), msg);
		
		return response;
	}
}
