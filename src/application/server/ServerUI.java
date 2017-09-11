package application.server;

import application.utils.Validation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerUI extends Application {
	private static int port;
	private static String fileName;
	private ServerController controller;

	public static void main(String[] args) {
		Validation validation = new Validation();

		int argsNum = args.length;
		if (argsNum != 2) {
			System.out.println("Please enter correct paramaters");
			return;
		}
		if (!validation.validFile(args[1])) {
			System.out.println("DB file doesn't exist, please enter correct file name");
			return;
		}
		if (!validation.validPort(Integer.parseInt(args[0]))) {
			System.out.println("Please enter correct port number");
			return;
		}
		
		port = Integer.parseInt(args[0]);
		fileName = args[1];

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("server_ui.fxml"));
		controller = new ServerController(port, fileName);
		loader.setController(controller);
		Parent root = loader.load();
		Scene scene = new Scene(root, 500, 375);

		primaryStage.setTitle("Dictionary Server Logs");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		controller.requestCloseSocket();
	}
}
