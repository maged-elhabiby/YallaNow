package org.ucalgary.events_microservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_members")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupUsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "role")
    private String role;

    public GroupUsersEntity() {}

    public GroupUsersEntity(Integer groupId, String userId, String role) {
        this.groupId = groupId;
        this.userId = userId;
        this.role = role;
    }

    public Integer getMemberId() {return memberId;}
    public Integer getGroupId() {return groupId;}
    public String getUserId() {return userId;}
    public String getRole() {return role;}

    public void setGroupId(Integer groupId) {this.groupId = groupId;}
    public void setUserId(String userId) {this.userId = userId;}
    public void setRole(String role) {this.role = role;}
}
