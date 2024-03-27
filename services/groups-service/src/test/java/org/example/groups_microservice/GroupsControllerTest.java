package org.example.groups_microservice;

import org.example.groups_microservice.Controller.GroupsController;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.Exceptions.*;
import org.example.groups_microservice.Service.GroupService;
import org.example.groups_microservice.Entity.EventEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GroupsControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupsController groupsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGroups() {
        // Arrange
        List<GroupEntity> groups = new ArrayList<>();
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");

        List<EventEntity> events = new ArrayList<>();
        events.add(new EventEntity(1,1,groupEntity));

        List<GroupMemberEntity> groupMembers = new ArrayList<>();
        groupMembers.add(new GroupMemberEntity(1,"1",null, "Guy", groupEntity));

        groups.add(new GroupEntity(1, "Test Group", groupMembers, events, false));

        when(groupService.getGroups()).thenReturn(groups);

        // Act
        ResponseEntity<List<GroupDTO>> response = groupsController.getGroups();

        // Assert
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetGroup() throws GroupNotFoundException {
        // Arrange
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");

        when(groupService.getGroup(groupID)).thenReturn(groupEntity);

        // Act
        ResponseEntity<GroupDTO> response = groupsController.getGroup(groupID);

        // Assert
        assertEquals(groupID, response.getBody().getGroupID());
    }

//create Delete Update

    @Test
    public void testDeleteGroup() throws GroupNotFoundException, EventNotFoundException, MemberNotFoundException, NotAuthorizationException {
        // Arrange
        int groupID = 1;

        // Act
        ResponseEntity<Void> response = groupsController.deleteGroup(groupID, "1");

        // Assert
        verify(groupService, times(1)).deleteGroup(groupID);
    }

}