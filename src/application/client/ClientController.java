package application.client;

import application.utils.Word;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
			client = new Client(this, port, host, (data) -> {
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
		if(searchField.getText().length() <= 0) {
			createAlert("Warning", "Enter something please...");
			return;
		}
		
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
		if(addWordField.getText().length() <= 0 || addMeaningField.getText().length() <= 0) {
			createAlert("Warning", "Enter something please...");
			return;
		}
		
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
		if(removeWordField.getText().length() <= 0) {
			createAlert("Warning", "Enter something please...");
			return;
		}
		
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
	
	public void createAlert(String title, String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText(message);
		alert.show();
	}
}
