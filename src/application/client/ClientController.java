package application.client;

import application.utils.Word;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ClientController {
	@FXML
	Text result;
	@FXML
	TextField searchField;
	@FXML
	TextField addWordField, addMeaningField, removeWordField;
	
	private ClientActionHelper helper = new ClientActionHelper();

	private Client client;

	public ClientController(String host, int port) {
		try {
			client = new Client(port, host, (data) -> {
				Platform.runLater(() -> {
					result.setText(data);
				});
			});
			client.startConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void searchBtnOnPress(ActionEvent event) {
		try {
			String jsonString = helper.createSearchJson(searchField.getText());
			client.send(jsonString);
			searchField.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void addBtnOnPress(ActionEvent event){
		try {
			String jsonString = helper.createAddJson(new Word(addWordField.getText(), addMeaningField.getText()));
			client.send(jsonString);
			addMeaningField.setText("");
			addWordField.setText("");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void removeBtnOnPress(ActionEvent event) {
		try {
			String jsonString = helper.createDeleteJson(removeWordField.getText());
			client.send(jsonString);
			removeWordField.setText("");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void requestCloseSocket() {
		try {
			client.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
