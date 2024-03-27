package org.example.groups_microservice;

import org.checkerframework.checker.units.qual.g;
import org.example.groups_microservice.Controller.GroupMemberController;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Exceptions.MemberNotFoundException;
import org.example.groups_microservice.Service.GroupMemberService;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Controller.GroupsController;

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

public class GroupMemberControllerTest {

    @Mock
    private GroupMemberService groupMemberService;

    @InjectMocks
    private GroupMemberController groupMemberController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGroupMembers() throws GroupNotFoundException {
        // Arrange
        int groupID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        List<GroupMemberEntity> groupMembers = new ArrayList<>();
        groupMembers.add(new GroupMemberEntity(1,1,null, null, groupEntity));

        when(groupMemberService.getGroupMembers(groupID)).thenReturn(groupMembers);

        // Act
        ResponseEntity<List<GroupMemberDTO>> response = groupMemberController.getGroupMembers(groupID);

        // Assert
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetGroupMember() throws GroupNotFoundException, MemberNotFoundException {
        // Arrange
        int groupID = 1;
        int userID = 1;
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        GroupMemberEntity groupMember = new GroupMemberEntity(1,1,null, null, groupEntity);

        when(groupMemberService.getGroupMember(groupID, userID)).thenReturn(groupMember);

        // Act
        ResponseEntity<GroupMemberDTO> response = groupMemberController.getGroupMember(groupID, userID);

        // Assert
        assertEquals(groupID, response.getBody().getGroupID());
        assertEquals(userID, response.getBody().getUserID());
    }

    @Test
    public void testAddGroupMember() throws GroupNotFoundException {
        // Arrange
        int groupID = 1;
        int userID = 1;
        GroupDTO groupDTO = new GroupDTO();
        GroupMemberDTO GroupMemberDTO = new GroupMemberDTO(1,1,null,null, groupDTO,1);
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupID(groupID);
        groupEntity.setGroupName("Test Group");
        GroupMemberEntity groupMember = new GroupMemberEntity(1,1,null, null, groupEntity);

        when(groupMemberService.addGroupMember(groupID, GroupMemberDTO)).thenReturn(groupMember);

        // Act
        ResponseEntity<GroupMemberDTO> response = groupMemberController.addGroupMember(groupID, GroupMemberDTO);

        // Assert
        assertEquals(groupID, response.getBody().getGroupID());
        assertEquals(userID, response.getBody().getUserID());
    }

    @Test
    public void testRemoveGroupMember() throws GroupNotFoundException, MemberNotFoundException {
        // Arrange
        int groupID = 1;
        int userID = 1;

        // Act
        ResponseEntity<Void> response = groupMemberController.removeGroupMember(groupID, userID);

        // Assert
        verify(groupMemberService, times(1)).removeGroupMember(groupID, userID);
    }

}
