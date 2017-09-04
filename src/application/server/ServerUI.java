package application.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerUI extends Application{
	private int port = 9099;
	private String fileName = "dictionary.db";
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("server_ui.fxml"));
	    ServerController controller = new ServerController(port, fileName);
	    loader.setController(controller);
	    Parent root = loader.load();
        Scene scene = new Scene(root, 500, 375);
    
        primaryStage.setTitle("Dictionary Server");
        primaryStage.setScene(scene);
        primaryStage.show();
	}	
}
