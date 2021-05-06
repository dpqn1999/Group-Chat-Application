package main;

import java.util.ArrayList;

import models.File;
import models.Group;
import models.Message;
import models.User;

public class DataStorage {
	private static DataStorage instance;
	private ArrayList<User> userList;
	private ArrayList<Group> groupList;
	private ArrayList<File> fileList;
	private ArrayList<Message> messageList;

	private DataStorage() {
		userList = new ArrayList<>();
		groupList = new ArrayList<>();
		fileList = new ArrayList<>();
		messageList = new ArrayList<>();
	}

	public static DataStorage getInstance() {
		if (instance == null) {
			instance = new DataStorage();
		}
		return instance;
	}

	public ArrayList<User> getUserList() {
		return userList;
	}

	public ArrayList<Group> getGroupList() {
		return groupList;
	}

	public ArrayList<File> getFileList() {
		return fileList;
	}

	public ArrayList<Message> getMessageList() {
		return messageList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public void setGroupList(ArrayList<Group> groupList) {
		this.groupList = groupList;
	}

	public void setFileList(ArrayList<File> fileList) {
		this.fileList = fileList;
	}

	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}

	public void addMessage(Message message) {
		messageList.add(message);
	}

	public void addFile(File file) {
		fileList.add(file);
	}

	public void addUser(User user) {
		userList.add(user);
	}

	public void addGroup(Group group) {
		groupList.add(group);
	}
}
