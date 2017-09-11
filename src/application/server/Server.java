/**
 * create Server Socket which listens to each client requests
 */
package application.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;
import javax.net.ServerSocketFactory;
import application.utils.INetworkConnection;

public class Server implements INetworkConnection {
	
	private ConnectionThread conneThead = new ConnectionThread();
	private int port;
	private Consumer<String> onReceiveCallback;
	private ServerActionHelper helper;

	public Server(int port, Consumer<String> onReceiveCallback, String fileName) {
		this.port = port;
		this.onReceiveCallback = onReceiveCallback;
		conneThead.setDaemon(true);
		helper = new ServerActionHelper(fileName);
		
		System.out.println("Server created");
	}
	
	@Override
	public void startConnection() throws Exception {
		conneThead.start();
	}

	@Override
	public void send(String msg) throws Exception {
		
	}

	@Override
	public void closeConnection() throws Exception {
		conneThead.server.close();
	}

	private class ConnectionThread extends Thread {
		private ServerSocketFactory factory = ServerSocketFactory.getDefault();
		private ServerSocket server;

		@Override
		public void run() {
			try {
				server = factory.createServerSocket(port);
				onReceiveCallback.accept(dataParse() + ": server created");
				while (true) {
					Socket client = server.accept();
					// Start a new thread for a connection
					onReceiveCallback.accept(dataParse() + ": a new client connected");
					Thread thread = new Thread(() -> serveClient(client));
					thread.setPriority(MAX_PRIORITY);
					thread.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void serveClient(Socket client) {
		try {
			Socket clientSocket = client;
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
			while(true) {
				String str = input.readUTF();
				String jsonString = helper.handleRequest(str);
				
				onReceiveCallback.accept(dataParse() + ": request - " + str);
				output.writeUTF(jsonString);
				output.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String dataParse() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date());
	}
}
