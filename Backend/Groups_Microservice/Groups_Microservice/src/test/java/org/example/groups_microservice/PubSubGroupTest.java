package org.example.groups_microservice;

import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.DTO.UserRole;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.GroupsMicroserviceApplication;
import org.example.groups_microservice.Service.GroupPubSub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = {GroupsMicroserviceApplication.class})
public class PubSubGroupTest {

    @Autowired
    private GroupPubSub groupsPubService;

    @BeforeEach
    public void setup() throws Exception {
        groupsPubService.initializePubSub("yallanow-413400", "group");
    }

    @Test
    public void testPublishGroup() throws Exception {

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(1);
        groupEntity.setGroupName("Test Group");
        groupEntity.setIsPrivate(false);
        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setUserID(1);
        groupMemberEntity.setGroupMemberID(1);
        groupMemberEntity.setGroup(groupEntity);
        groupMemberEntity.setUserName("Test User");
        groupMemberEntity.setRole(UserRole.ADMIN);
        groupEntity.setGroupMembers(List.of(groupMemberEntity));







        for (int i = 0; i < 5; i++) {
            //GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
            //groupMemberDTO.setUserID(i);
            //Integer userID = groupMemberDTO.getUserID();
            groupsPubService.publishGroupMember(groupMemberEntity, "ADD");
            //groupsPubService.publishGroup(groupEntity, "ADD");
            //groupsPubService.publishGroupID(groupDTO.getGroupID(),"ADD");
            Thread.sleep(5000);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        groupsPubService.shutdown();
    }
}