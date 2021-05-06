package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.DataStorage;
import models.File;
import models.Group;
import models.GroupType;
import models.Message;
import models.User;
import services.GroupService;
import services.MessageService;
import services.UserService;

public class UnitTest {
	DataStorage dataStorage;
	UserService userService = new UserService();
	GroupService groupService = new GroupService();
	MessageService messageService = new MessageService();
	String contentMessage1 = "Hello";
	String contentMessage2 = "Hi";
	String fileName = "Flowers";
	String filePath = "flowers.jpg";

	@BeforeEach
	void setUp() throws Exception {
		dataStorage = DataStorage.getInstance();
		userService.createNewUser("Nguyen", "Le", "nguyenle", "12345678", "Male", "03101999");
		userService.createNewUser("Ngoc", "Thanh", "ngocthanh", "99999999", "Female", "13031999");
		userService.createNewUser("Van", "Ngoc", "vanngoc", "99999999", "Female", "09081999");
		groupService.createGroup(GroupType.Private, 0);
		groupService.createGroup(GroupType.Public, 0);
		groupService.createGroup(GroupType.Private, 1);
	}

	@Test
	public void addUserTest() {
		assertTrue(userService.createNewUser("Le", "Thanh", "thanhle123", "12345678", "Male", "03101999"));
	}

	@Test
	public void addExistUserTest() {
		assertFalse(userService.createNewUser("Nguyen", "Le", "nguyenle", "12345678", "Male", "03101999"));
	}

	@Test
	public void addPrivateGroupTest() {
		assertTrue(groupService.createGroup(GroupType.Private, 1));
	}

	@Test
	public void addPublicGroupTest() {
		assertTrue(groupService.createGroup(GroupType.Public, 1));
	}

	@Test
	public void loginWithTruePasswordTest() {
		assertTrue(userService.login("nguyenle", "12345678"));
	}

	@Test
	public void loginWithWrongPasswordTest() {
		assertFalse(userService.login("nguyenle", "123456s8"));
	}

	@Test
	public void loginNoExistTest() {
		assertFalse(userService.login("nguyenlekhanh", "12345678"));
	}

	@Test
	public void checkHashPasswordtest() {
		assertTrue(userService.checkHashPassword("nguyenle", "12345678"));
	}

	@Test
	public void getUserByUsernameTest() {
		User user = userService.getUserByUsername("nguyenle");
		assertEquals(0, user.getId());
	}

	@Test
	public void getUserByIDTest() {
		User user = userService.getUserByUserId(0);
		assertEquals("nguyenle", user.getUsername());
	}

	@Test
	public void setAliastest() {
		User user1 = userService.getUserByUsername("nguyenle");
		User user2 = userService.getUserByUsername("ngocthanh");
		assertTrue(userService.setAlias(user1, user2, "BigPig!!!"));
	}

	@Test
	public void addPrivateMemberTest() {
		User user1 = userService.getUserByUsername("nguyenle");
		User user2 = userService.getUserByUsername("ngocthanh");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), 0));
	}

	@Test
	public void addPublicMemberTest() {
		User user1 = userService.getUserByUsername("nguyenle");
		User user2 = userService.getUserByUsername("ngocthanh");
		assertTrue(groupService.addMember(user1.getId(), user2.getId(), 1));
	}

	@Test
	public void addMemberByInviteCodeTest() {
		User user1 = userService.getUserByUsername("nguyenle");
		assertTrue(
				groupService.addMemberByInviteCode(dataStorage.getGroupList().get(1).getInviteCode(), user1.getId()));
	}

	@Test
	public void checkAdminPrivateGroupTest() {
		User user1 = userService.getUserByUsername("nguyenle");
		assertTrue(groupService.checkMemberGroup(user1.getId(), 0));
	}

	@Test
	public void leaveGroupTest() {
		User user1 = userService.getUserByUsername("nguyenle");
		Group group = groupService.getGroupByGroupId(0);
		assertTrue(groupService.leaveGroup(user1.getId(), group.getId()));
	}

	@Test
	public void sendMessageToGroupTest() {
		messageService.sendMessageToGroup(contentMessage1, 1, 0);
		List<Message> messageList = dataStorage.getMessageList();
		assertEquals("Hello", messageList.get(messageList.size() - 1).getContent());
	}

	@Test
	public void sendMessageToUserTest() {
		messageService.sendMessageToUser(contentMessage1, 1, 0);
		List<Message> messageList = dataStorage.getMessageList();
		assertEquals("Hello", messageList.get(messageList.size() - 1).getContent());
	}

	@Test
	public void sendFileToUserTest() {
		messageService.sendFileToUser(fileName, filePath, 0, 0);
		;
		List<File> fileList = dataStorage.getFileList();
		assertEquals("Flowers", fileList.get(fileList.size() - 1).getFileName());
	}

	@Test
	public void sendFileToGroupTest() {
		messageService.sendFileToGroup(fileName, filePath, 1, 0);
		List<File> fileList = dataStorage.getFileList();
		assertEquals("Flowers", fileList.get(fileList.size() - 1).getFileName());
	}

	@Test
	public void showAllMessageGroupTest() {
		for (int i = 0; i < 4; i++) {
			messageService.sendMessageToGroup(contentMessage1, 0, 0);
		}
		List<Message> messageList = messageService.showAllMessageGroup(0,0);
		assertEquals(4, messageList.size());

	}

	@Test
	public void showAllMessageUserTest() {
		for (int i = 0; i < 12; i++) {
			messageService.sendMessageToUser(contentMessage1, 1, 0);

		}
		List<Message> messageList = messageService.showAllMessageUser(0, 1);
		assertEquals(12, messageList.size());
	}

	@Test
	public void showKLatestMessageGroupTest() {

		for (int i = 0; i < 10; i++) {
			messageService.sendMessageToGroup(contentMessage1, 0, 0);
		}
		List<Message> messageList = messageService.showKLatestMessageGroup(0, 3);
		assertEquals(3, messageList.size());

	}

	@Test
	public void showAllFileInGroupTest() {
		Group group = groupService.getGroupByGroupId(0);
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, 0, 0);
		}
		List<File> fileList = messageService.showAllFileInGroup(group.getId());
		assertEquals(2, fileList.size());

	}

	@Test
	public void deleteMessageTest() {
		for (int i = 0; i < 2; i++) {
			messageService.sendMessageToGroup(contentMessage1, 1, 2);
		}
		int size = dataStorage.getMessageList().size();
		messageService.deleteMessage(0);
		assertEquals(size - 1, dataStorage.getMessageList().size());
	}

	@Test
	public void deleteFileTest() {
		for (int i = 0; i < 2; i++) {
			messageService.sendFileToGroup(fileName, filePath, 1, 1);
		}
		int size = dataStorage.getFileList().size();
		messageService.deleteFile(0);
		;
		assertEquals(size - 1, dataStorage.getFileList().size());
	}

	@Test
	public void findMessageByKeywordInGroupChatTest() {
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToGroup(contentMessage1, 1, 2);
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToGroup(contentMessage2, 1, 2);
		}
		List<Message> messageList = messageService.findMessageByKeywordInGroupChat("Hi", 2,1);
		assertEquals(3, messageList.size());
	}

	@Test
	public void findMessageByKeywordInUserChatTest() {
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser(contentMessage1, 0, 1);
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser(contentMessage2, 0, 2);
		}
		for (int i = 0; i < 3; i++) {
			messageService.sendMessageToUser(contentMessage2, 1, 0);
		}

		List<Message> messageList = messageService.findMessageByKeywordInUserChat("Hi", 0, 1);
		assertEquals(3, messageList.size());
	}

}
