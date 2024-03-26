package org.example.groups_microservice.Entity;

import jakarta.persistence.*;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.DTO.UserRole;
/**
 * GroupMemberEntity, an entity class for group members in the groups microservice.
 */
@Entity
@Table(name = "group_members")
public class GroupMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Integer groupMemberID;

    @Column(name = "user_id")
    private Integer userID;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "username")
    private String userName;


    // Relationships with other entities
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = true) // Maps this relationship to the 'group_id' column in 'group_members' table
    private GroupEntity group;


    public GroupMemberEntity() {
    }

    public GroupMemberEntity(Integer groupMemberID,Integer userID, UserRole role,String username, GroupEntity group) {
        this.userID = userID;
        this.groupMemberID = groupMemberID;
        this.role = role;
        this.userName = username;
        this.group = group;
    }

    // Getters and Setters

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }



    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getGroupMemberID() {
        return groupMemberID;
    }

    public void setGroupMemberID(Integer groupMemberID) {
        this.groupMemberID = groupMemberID;
    }









}
