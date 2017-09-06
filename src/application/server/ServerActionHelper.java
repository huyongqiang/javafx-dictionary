package application.server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.utils.SQLiteHelper;
import application.utils.Word;

public class ServerActionHelper {
	final String SEARCH = "search", ADD = "add", DELETE = "delete";
	private SQLiteHelper dbHelper;

	public ServerActionHelper(String fileName) {
		dbHelper = new SQLiteHelper(fileName);
	}

	public String handleRequest(String jsonString) {
		String json = "";
		JSONParser parser = new JSONParser();

		try {
			JSONObject object = (JSONObject) parser.parse(jsonString);
			String actionType = (String) object.get("action");

			if (actionType.equals(SEARCH)) {
				json = handleSearch(object);
			} else if (actionType.equals(ADD)) {
				json = handleAdd(object);
			} else if (actionType.equals(DELETE)) {
				json = handleDelete(object);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	@SuppressWarnings("unchecked")
	private String handleSearch(JSONObject object) {
		String word = (String) object.get("word");
		String meaning = dbHelper.selectRecord(word);

		JSONObject obj = new JSONObject();
		obj.put("action", SEARCH);
		if (meaning.equals("-1")) {
			obj.put("status", "400");
			obj.put("meaning", "not found");
			obj.put("msg", word + " isn't in database");
		} else {
			obj.put("status", "200");
			obj.put("meaning", meaning);
			obj.put("msg", "Search Successfully");
		}

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	private String handleAdd(JSONObject object) {
		String word = (String) object.get("word");
		String meaning = (String) object.get("meaning");
		boolean result = dbHelper.insertRecord(new Word(word, meaning));

		JSONObject obj = new JSONObject();
		obj.put("action", ADD);
		if (result) {
			obj.put("status", "200");
			obj.put("msg", "Add " + word + " Successfully");
		} else {
			obj.put("status", "400");
			obj.put("msg", "Add " + word + " failed, the word is duplicate");
		}

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	private String handleDelete(JSONObject object) {
		String word = (String) object.get("word");
		boolean result = dbHelper.deleteRecord(word);

		JSONObject obj = new JSONObject();
		obj.put("action", DELETE);
		if (result) {
			obj.put("status", "200");
			obj.put("msg", "Remove " + word + " Successfully");
		} else {
			obj.put("status", "400");
			obj.put("msg", "Remove " + word + " failed, not found this word");
		}

		return obj.toJSONString();
	}

}
