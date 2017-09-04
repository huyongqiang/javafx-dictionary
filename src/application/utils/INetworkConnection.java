package application.utils;

public interface INetworkConnection {
	void startConnection() throws Exception;
	void send(String msg) throws Exception;
	void closeConnection() throws Exception;
}
