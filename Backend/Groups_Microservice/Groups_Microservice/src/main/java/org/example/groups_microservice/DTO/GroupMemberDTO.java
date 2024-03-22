package org.example.groups_microservice.DTO;

public class GroupMemberDTO {

    private int userID;
    private String userName;
    private UserRole role;
    private GroupDTO group;

    public GroupMemberDTO(int userID, String userName, UserRole role, GroupDTO group) {
        this.userID = userID;
        this.userName = userName;
        this.role = role;
        this.group = group;
    }

    public GroupMemberDTO() {

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }




}
