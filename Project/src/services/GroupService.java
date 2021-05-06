package services;

import main.DataStorage;
import models.Group;
import models.GroupType;
import models.PrivateGroup;
import models.PublicGroup;
import models.User;

public class GroupService {
	DataStorage dataStorage;
	UserService userService;

	public GroupService() {
		this.dataStorage = DataStorage.getInstance();
		this.userService = new UserService();
	}

	public boolean createGroup(GroupType groupType, int userId) {
		User tempUser = userService.getUserByUserId(userId);
		if (groupType.equals(GroupType.Private)) {
			PrivateGroup group = new PrivateGroup();
			group.addAdmin(tempUser);
			group.addMember(tempUser);
			dataStorage.addGroup(group);
			return true;
		} else if (groupType.equals(GroupType.Public)) {
			PublicGroup group = new PublicGroup();
			group.addAdmin(tempUser);
			group.addMember(tempUser);
			dataStorage.addGroup(group);
			return true;
		}
		return false;
	}

	public boolean addMember(int inviterId, int inviteeId, int groupId) {
		User inviteeUser = userService.getUserByUserId(inviteeId);
		Group group = getGroupByGroupId(groupId);
		if (group.getGroupType().equals(GroupType.Private)) {
			if (checkAdminPrivateGroup(inviterId, groupId)) {
				group.addMember(inviteeUser);
				return true;
			}
		} else if (checkMemberGroup(inviterId, groupId)) {
			group.addMember(inviteeUser);
			return true;
		}
		return false;
	}

	public boolean addMemberByInviteCode(String inviteCode, int id) {
		User user = userService.getUserByUserId(id);
		Group group = getGroupByInviteCode(inviteCode);
		if (group != null) {
			group.addMember(user);
			return true;
		}
		return false;
	}

	private boolean checkAdminPrivateGroup(int userId, int groupId) {
		Group group = getGroupByGroupId(groupId);
		User admin = userService.getUserByUserId(userId);
		if (group != null) {
			if (group.getAdminList().contains(admin)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean checkMemberGroup(int userId, int groupId) {
		Group group = getGroupByGroupId(groupId);
		User user = userService.getUserByUserId(userId);
		if (group != null) {
			if (group.getUserList().contains(user)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean leaveGroup(int userId, int groupId) {
		User user = userService.getUserByUserId(userId);
		boolean result = false;
		Group group = getGroupByGroupId(groupId);
		if (checkMemberGroup(userId, groupId)) {
			group.removeMember(user);
			result = true;
		}
		if (checkAdminPrivateGroup(userId, groupId)) {
			group.removeAdmin(user);
			result = true;
		}
		return result;
	}

	public Group getGroupByGroupId(int id) {
		Group tempGroup = null;
		for (Group group : dataStorage.getGroupList()) {
			if (group.getId() == id) {
				tempGroup = group;
			}
		}
		return tempGroup;
	}

	public Group getGroupByInviteCode(String inviteCode) {
		Group tempGroup = null;
		for (Group group : dataStorage.getGroupList()) {
			if (group.getInviteCode().equals(inviteCode)) {
				tempGroup = group;
			}
		}
		return tempGroup;
	}

}
