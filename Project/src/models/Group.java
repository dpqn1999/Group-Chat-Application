package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Group {
	private final String inviteCode = UUID.randomUUID().toString();
	private static int count = 0;
	private int id;
	protected GroupType groupType;
	private List<User> userList;
	private List<User> adminList;

	public Group() {
		id = count++;
		userList = new ArrayList<>();
		adminList = new ArrayList<>();
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<User> adminList) {
		this.adminList = adminList;
	}
	
	public void addAdmin(User user) {
		adminList.add(user);
	}
	
	public void addMember(User user) {
		userList.add(user);
	}
	
	
	public void removeMember(User user) {
		userList.remove(user);
	}
	
	public void removeAdmin(User user) {
		adminList.remove(user);
	}

}