package services;

import java.util.ArrayList;
import java.util.List;

import main.DataStorage;
import models.File;
import models.Message;
import models.UserType;

public class MessageService {
	UserService userService;
	GroupService groupService;
	DataStorage dataStorage;

	public MessageService() {
		this.userService = new UserService();
		this.groupService = new GroupService();
		this.dataStorage = DataStorage.getInstance();
	}

	public void sendMessageToGroup(String content, int senderId, int receiverId) {
		Message message = new Message(content, senderId, receiverId);
		message.setReceiverType(UserType.Group);
		dataStorage.addMessage(message);
	}

	public void sendMessageToUser(String content, int senderId, int receiverId) {
		Message message = new Message(content, senderId, receiverId);
		message.setReceiverType(UserType.User);
		dataStorage.addMessage(message);
	}

	public void sendFileToGroup(String fileName, String filePath, int senderId, int receiverId) {
		File file = new File(fileName, filePath, senderId, receiverId);
		file.setReceiverType(UserType.Group);
		dataStorage.addFile(file);
	}

	public void sendFileToUser(String fileName, String filePath, int senderId, int receiverId) {
		File file = new File(fileName, filePath, senderId, receiverId);
		file.setReceiverType(UserType.User);
		dataStorage.addFile(file);
	}

	public List<Message> showAllMessageGroup(int groupId, int userId) {
		List<Message> messageList = new ArrayList<Message>();
		for (Message message : dataStorage.getMessageList()) {
			if (message.getReceiverType() == UserType.Group) {
				if ((message.getReceiverId() == groupId && message.getSenderId() == userId)) {
					messageList.add(message);
				}
			}
		}
		return messageList;
	}

	public List<Message> showAllMessageUser(int userId1, int userId2) {
		List<Message> messageList = new ArrayList<Message>();
		retrieveUserMessage(userId1, userId2, messageList);
		retrieveUserMessage(userId2, userId1, messageList);
		return messageList;
	}

	public List<Message> retrieveUserMessage(int userId1, int userId2, List<Message> userMessages) {
		List<Message> messagesList = dataStorage.getMessageList();
		for (Message message : messagesList) {
			if (message.getReceiverType() == UserType.User) {
				if (message.getSenderId() == userId1 && message.getReceiverId() == userId2) {
					userMessages.add(message);
				}
			}
		}
		return userMessages;

	}

	public List<Message> showKLatestMessageGroup(int groupId, int k) {
		List<Message> groupMessage = new ArrayList<Message>();
		List<Message> messageList = dataStorage.getMessageList();
		for (int i = messageList.size() - 1; i >= 0; i--) {
			Message message = messageList.get(i);
			if (message.getReceiverType() == UserType.Group && message.getReceiverId() == groupId) {
				groupMessage.add(message);
				k--;
			}
			if (k == 0) {
				return groupMessage;
			}
		}
		return groupMessage;
	}

	public List<Message> showLatestMessageGroupExceptM(int groupId, int k, int m) {
		List<Message> messageList = showKLatestMessageGroup(groupId, k);
		for (int i = 0; i < m; i++) {
			messageList.remove(i);
		}
		return messageList;
	}

	public List<File> showAllFileInGroup(int groupId) {
		List<File> fileList = dataStorage.getFileList();
		List<File> groupFileList = new ArrayList<File>();
		for (File file : fileList) {
			if (file.getReceiverType() == UserType.Group && file.getReceiverId() == groupId) {
				groupFileList.add(file);
			}
		}

		return groupFileList;
	}

	public void deleteMessage(int messageId) {
		ArrayList<Message> messageList = dataStorage.getMessageList();
		for (int i = 0; i < messageList.size(); i++) {
			if (messageList.get(i).getId() == messageId) {
				messageList.remove(messageList.get(i));
			}
		}
	}

	public void deleteFile(int fileId) {
		List<File> fileList = dataStorage.getFileList();
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getId() == fileId) {
				fileList.remove(fileList.get(i));
			}
		}
	}

	public List<Message> findMessageByKeywordInGroupChat(String keyword, int groupId, int userId) {
		List<Message> groupMessageList = showAllMessageGroup(groupId, userId);
		List<Message> containKeyWordMessages = new ArrayList<>();

		for (Message message : groupMessageList) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				containKeyWordMessages.add(message);
			}
		}
		return containKeyWordMessages;
	}

	public List<Message> findMessageByKeywordInUserChat(String keyword, int userId1, int userId2) {
		List<Message> userMessageList = showAllMessageUser(userId1, userId2);
		List<Message> containKeyWordMessages = new ArrayList<>();
		for (Message message : userMessageList) {
			if (message.getContent().toLowerCase().contains(keyword.toLowerCase())) {
				containKeyWordMessages.add(message);
			}
		}
		return containKeyWordMessages;
	}
}
