package application.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application{
	private int port = 9099;
	private String host = "localhost";
	private ClientController controller;
	
	public static void main(String[] args) {	
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("client_ui.fxml"));
	    controller = new ClientController(host, port);
	    loader.setController(controller);
	    Parent root = loader.load();
        Scene scene = new Scene(root, 500, 375);
    
        primaryStage.setTitle("Dictionary Client");
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		controller.requestCloseSocket();
	}
	
	
}
