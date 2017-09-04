package application.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

import application.utils.INetworkConnection;
import application.utils.MyResponse;

public class Client implements INetworkConnection {
	private ConnectionThread conectThread = new ConnectionThread();
	private int port;
	private String host;
	private Consumer<String> onReceiveCallback;
	private boolean conneFlag = true;

	public Client(int port, String host, Consumer<String> onReceiveCallback) {
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
					
					if(response.getType().equals("search"))
						onReceiveCallback.accept(response.getData());
				}
				
				//socket.shutdownInput();
				//socket.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
