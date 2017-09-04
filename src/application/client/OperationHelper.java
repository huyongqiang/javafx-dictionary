package application.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.utils.Word;

public class OperationHelper {
	final String fileName = "dictionary.txt";

	public OperationHelper(String json) {

	}
	
	public String clientCallback() {
		return "";
	}
	
	public String serverCallback() {
		return "";
	}

	@SuppressWarnings("unchecked")
	public String createSearchJson(String string) {
		JSONObject obj = new JSONObject();
		obj.put("operation", "search");
		obj.put("word", string);

		return obj.toJSONString();
	}

	public Word parseSearchResult(String jsonString) {
		JSONParser parser = new JSONParser();
		Word word = null;

		try {
			JSONObject object = (JSONObject) parser.parse(jsonString);
			String wordText = (String) object.get("word");
			String meaning = (String) object.get("meaning");

			word = new Word(wordText, meaning);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return word;
	}

	@SuppressWarnings("unchecked")
	public synchronized void addWordAndMeaningJson(Word word) {
		JSONObject obj = new JSONObject();
		obj.put("word", word.getWord());
		obj.put("meaning", word.getMeaning());

		String string = obj.toJSONString();

		File writename = new File(fileName);
		try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(string);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
