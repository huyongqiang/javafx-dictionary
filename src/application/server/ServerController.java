package application.server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerController {

	@FXML
	TextArea output;

	private Server server;

	public ServerController(int port, String fileName) {
		System.out.println(port);
		try {
			server = new Server(port, (data) -> {
				Platform.runLater(() -> {
					output.appendText(data + "\n");
				});
			}, fileName);
			server.startConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
