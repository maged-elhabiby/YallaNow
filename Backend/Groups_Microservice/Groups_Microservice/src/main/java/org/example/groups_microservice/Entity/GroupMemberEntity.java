package org.example.groups_microservice.Entity;

import jakarta.persistence.*;
import org.example.groups_microservice.DTO.UserRole;
/**
 * GroupMemberEntity, an entity class for group members in the groups microservice.
 */
@Entity
@Table(name = "group_members")
public class GroupMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userID;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;



    // Relationships with other entities
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false) // Maps this relationship to the 'group_id' column in 'group_members' table
    private GroupEntity group;


    public GroupMemberEntity() {
    }

    public GroupMemberEntity(int userID, UserRole role) {
        this.userID = userID;
        this.role = role;
    }

    // Getters and Setters

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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









}
