	package models;

public class Message {
	private static int count = 0;
	private int id;
	private String content;
	private int senderId;
	private int receiverId;
	private UserType receiverType;

	public Message(String content, int senderId, int receiverId) {
		id = count++;
		this.content = content;
		this.senderId = senderId;
		this.receiverId = receiverId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public UserType getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(UserType receiverType) {
		this.receiverType = receiverType;
	}

}