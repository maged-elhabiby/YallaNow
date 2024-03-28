package org.example.groups_microservice.DTO;

public class GroupMemberDTO {

    private Integer groupMemberID;
    private String userID;
    private String userName;
    private UserRole role;

    private Integer groupID;


    public GroupMemberDTO(Integer groupMemberID,String userID, String userName, UserRole role, GroupDTO group, Integer groupID) {
        this.groupMemberID = groupMemberID;
        this.userID = userID;
        this.userName = userName;
        this.role = role;
        this.groupID = group.getGroupID();


    }

    public GroupMemberDTO() {
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getGroupMemberID() {
        return groupMemberID;
    }

    public void setGroupMemberID(Integer groupMemberID) {
        this.groupMemberID = groupMemberID;
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


    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }






}
