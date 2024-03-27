package org.example.groups_microservice;

import org.example.groups_microservice.DTO.UserRole;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Entity.EventEntity;
import org.example.groups_microservice.Service.GroupPubSub;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class GroupPubSubTest {

    @Mock
    private GroupEntity groupEntity;

    @Mock
    private GroupMemberEntity groupMemberEntity;

    @Mock
    private EventEntity eventEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void convertGroupToJsonReturnsCorrectJson() throws Exception {
        when(groupEntity.getGroupID()).thenReturn(1);
        when(groupEntity.getGroupName()).thenReturn("Test Group");
        when(groupEntity.getIsPrivate()).thenReturn(false);
        when(groupEntity.getGroupMembers()).thenReturn(Arrays.asList(groupMemberEntity));
        when(groupEntity.getEvents()).thenReturn(Arrays.asList(eventEntity));

        when(groupMemberEntity.getUserID()).thenReturn(1);
        when(groupMemberEntity.getGroupMemberID()).thenReturn(1);
        when(groupMemberEntity.getUserName()).thenReturn("Test User");
        when(groupMemberEntity.getRole()).thenReturn(UserRole.ADMIN);

        when(eventEntity.getEventID()).thenReturn(1);
        when(eventEntity.getGlobalEventID()).thenReturn(1);

        JSONObject result = GroupPubSub.convertGroupToJson(groupEntity);

        assertEquals(1, result.getInt("groupID"));
        assertEquals("Test Group", result.getString("groupName"));
        assertEquals(false, result.getBoolean("isPrivate"));
        assertEquals(1, result.getJSONArray("groupMembers").getJSONObject(0).getInt("userId"));
        assertEquals(1, result.getJSONArray("events").getJSONObject(0).getInt("eventId"));
    }

    @Test
    public void convertGroupToJsonHandlesEmptyGroup() throws Exception {
        when(groupEntity.getGroupID()).thenReturn(1);
        when(groupEntity.getGroupName()).thenReturn("Test Group");
        when(groupEntity.getIsPrivate()).thenReturn(false);

        JSONObject result = GroupPubSub.convertGroupToJson(groupEntity);

        assertEquals(1, result.getInt("groupID"));
        assertEquals("Test Group", result.getString("groupName"));
        assertEquals(false, result.getBoolean("isPrivate"));
        assertEquals(0, result.getJSONArray("groupMembers").length());
        assertEquals(0, result.getJSONArray("events").length());
    }
    @Test
    public void publishSendsGroupToExternalSystem() {
        // Arrange
        GroupPubSub groupPubSub = Mockito.mock(GroupPubSub.class);
        GroupEntity testGroup = new GroupEntity(1, "Test Group", null, null, true);


        // Act
        groupPubSub.publishGroupID(1, "Test Group");

        // Assert
        Mockito.verify(groupPubSub).publishGroupID(1, "Test Group");
    }
}