/**
 * create a socket to send a request to connect with the server
 */
package application.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

import application.utils.INetworkConnection;
import application.utils.MyResponse;
import javafx.application.Platform;

public class Client implements INetworkConnection {
	private ConnectionThread conectThread = new ConnectionThread();
	private int port;
	private String host;
	private Consumer<String> onReceiveCallback;
	private boolean conneFlag = true;
	private ClientController controller;

	public Client(ClientController controller, int port, String host, Consumer<String> onReceiveCallback) {
		this.controller = controller;
		this.port = port;
		this.host = host;
		this.onReceiveCallback = onReceiveCallback;
		this.conectThread.setDaemon(true);
		System.out.println("new Clinet created");
	}

	@Override
	public void startConnection() throws Exception {
		conectThread.start();
	}

	@Override
	public void send(String data) throws Exception {
		conectThread.output.writeUTF(data);
		conectThread.output.flush();
	}

	@Override
	public void closeConnection() throws Exception {
		conneFlag = false;
		conectThread.socket.close();
	}

	private class ConnectionThread extends Thread {
		private Socket socket;
		private DataOutputStream output;
		private DataInputStream input;

		@Override
		public void run() {
			try {
				socket = new Socket(host, port);
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
				socket.setTcpNoDelay(true);

				while (conneFlag) {
					String str = input.readUTF();
					ClientActionHelper helper = new ClientActionHelper();
					MyResponse response = helper.handleResponse(str);
					System.out.println(response.getMessage());
					
					if(response.getType().equals("search")) {
						if(response.getStatus() == 400) {
							Platform.runLater(()->{
								controller.createAlert("Warning", "Not found this word in database");
							});
						}else {
							onReceiveCallback.accept(response.getData());
						}
					}else {
						Platform.runLater(()->{
							controller.createAlert("Warning", response.getMessage());
						});
					}
						
				}
				
				socket.shutdownInput();
				socket.shutdownOutput();
			} catch (IOException e) {
				Platform.runLater(()->{
					controller.createAlert("Warning", "Connect Error");
				});
			}

		}
	}
}
