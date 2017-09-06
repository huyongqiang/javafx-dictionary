package application.client;

import application.utils.Validation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application {
	private static int port;
	private static String host;
	private ClientController controller;

	public static void main(String[] args) {
		Validation validation = new Validation();
		
		int argsNum = args.length;
		if (argsNum != 2) {
			System.out.println("Please enter correct paramaters");
			return;
		}
		if(!validation.validIP(args[0])){
			System.out.println("Please enter correct IP address");
			return;
		}
		if(!validation.validPort(Integer.parseInt(args[1]))) {
			System.out.println("Please enter correct port number");
			return;
		}
		
		host = args[0];
		port = Integer.parseInt(args[1]);

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
