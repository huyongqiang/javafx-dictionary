package application.utils;

public class MyResponse {
	private String type;
	private int status;
	private String message;
	private String data;
	
	public MyResponse(String type, int status, String message, String data) {
		this.setStatus(status);
		this.setType(type);
		this.setMessage(message);
		this.setData(data);
	}
	
	public MyResponse(String type, int status, String message) {
		this.setType(type);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
