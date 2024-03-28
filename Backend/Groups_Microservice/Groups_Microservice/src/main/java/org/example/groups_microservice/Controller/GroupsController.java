package org.example.groups_microservice.Controller;
import org.example.groups_microservice.DTO.EventDTO;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.DTO.UserRole;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Exceptions.*;
import org.example.groups_microservice.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.groups_microservice.Service.GroupPubSub.*;


@RestController
@RequestMapping("/microservice/groups")
public class GroupsController {
    private final GroupService groupService;

    @Autowired
    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getGroups() {
        // get all groups
        List<GroupEntity> groups = groupService.getGroups();
        // convert to DTO
        List<GroupDTO> groupDTOS = groups.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupDTOS);
    }
    /**
     * convertToDto method is used to convert a group entity to a group DTO.
     * @param groupEntity - the group entity to be converted
     * @return the group DTO
     */
    private GroupDTO convertToDto(GroupEntity groupEntity) {
        GroupDTO dto = new GroupDTO();
        dto.setGroupID(groupEntity.getGroupID());
        dto.setGroupName(groupEntity.getGroupName());
        dto.setIsPrivate(groupEntity.getIsPrivate());
        dto.setGroupMembers(groupEntity.getGroupMembers().stream()
                .map(groupMemberEntity -> {
                    GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
                    groupMemberDTO.setGroupMemberID(groupMemberEntity.getGroupMemberID()); // Corrected here
                    groupMemberDTO.setUserID(groupMemberEntity.getUserID());
                    groupMemberDTO.setUserName(groupMemberEntity.getUserName());
                    groupMemberDTO.setGroupID(groupMemberEntity.getGroup().getGroupID());
                    groupMemberDTO.setRole(groupMemberEntity.getRole());
                    return groupMemberDTO;
                })
                .collect(Collectors.toList()));
        dto.setEvents(groupEntity.getEvents().stream()
                .map(eventEntity -> {
                    EventDTO eventDTO = new EventDTO();
                    eventDTO.setEventID(eventEntity.getEventID());
                    eventDTO.setGlobalEventID(eventEntity.getGlobalEventID());
                    eventDTO.setGroupID(eventEntity.getGroup().getGroupID());
                    eventDTO.setGroupName(eventEntity.getGroup().getGroupName());
                    return eventDTO;
                })
                .collect(Collectors.toList()));
        dto.setMemberCount(groupEntity.getGroupMembers().size());
        return dto;
    }

    /**
     * createGroup method is used to create a new group.
     * @param groupDTO - the group object to be created
     * @return the created group entity
     * @throws GroupAlreadyExistsException if the group already exists or is invalid
      */
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) throws GroupAlreadyExistsException {
        GroupEntity groupEntity = groupService.createGroup(groupDTO);
        publishGroupByMember(groupEntity, "ADD");
        return ResponseEntity.ok(convertToDto(groupEntity));
    }

    /**
     * getGroup method is used to retrieve 1 group by its ID.
     * @param groupID - the ID of the group
     * @return the group entity
     */

    @GetMapping("/{groupID}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable Integer groupID) throws GroupNotFoundException {
        GroupEntity groupEntity = groupService.getGroup(groupID);
        return ResponseEntity.ok(convertToDto(groupEntity));
    }



    /**
     * deleteGroup method is used to delete a group by its ID.
     * @param groupID - the ID of the group
     */
    @DeleteMapping("/{groupID}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer groupID, @RequestAttribute("Id") String userID ) throws GroupNotFoundException, EventNotFoundException, MemberNotFoundException, NotAuthorizationException {
        // check if the user is authorized to delete the group by seeing if they are the ADMIN ROLE
        GroupEntity groupEntity = groupService.getGroup(groupID);
        for (GroupMemberEntity groupMemberEntity : groupEntity.getGroupMembers()) {
            if (groupMemberEntity.getUserID().equals(userID) && groupMemberEntity.getRole().equals(UserRole.ADMIN)) {
                break;
            } else {
                throw new NotAuthorizationException("User is not authorized to delete group");
            }
        }
        groupService.deleteGroup(groupID);
        return ResponseEntity.ok().build();
    }

    /**
     * updateGroup method is used to update a group by its ID.
     * @param groupID - the ID of the group
     * @param groupDTO - the group object with updated values
     * @return the updated group entity
     */
    @PutMapping("/{groupID}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Integer groupID, @RequestBody GroupDTO groupDTO,@RequestAttribute("Id") String userId, @RequestAttribute("Name") String username ) throws GroupNotFoundException, NotAuthorizationException {
        GroupEntity groupEntity = groupService.updateGroup(groupID, groupDTO, userId, username);
        publishGroupByMember(groupEntity, "UPDATE");
        return ResponseEntity.ok(convertToDto(groupEntity));
    }

    /**
     * get groups that a user is part of through userID without showing group members or events
     */
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<GroupDTO>> getGroupsByUserID(@PathVariable String userID) {
        // check if the user is authorized to get the groups
        List<GroupEntity> groups = groupService.getGroupsByUserID(userID);
        List<GroupDTO> groupDTOS = groups.stream()
                .map(this::convertToDtoWithNoMembersOrEvents)
                .collect(Collectors.toList());
        //get member count and role
        for (GroupDTO groupDTO : groupDTOS) {
            for (GroupEntity groupEntity : groups) {
                for (GroupMemberEntity groupMemberEntity : groupEntity.getGroupMembers()) {
                    if (groupMemberEntity.getUserID().equals(userID)) {
                        groupDTO.setMemberCount(groupEntity.getGroupMembers().size());
                    }
                }
            }
        }

        return ResponseEntity.ok(groupDTOS);

    }
    private GroupDTO convertToDtoWithNoMembersOrEvents(GroupEntity groupEntity) {
        GroupDTO dto = new GroupDTO();
        dto.setGroupID(groupEntity.getGroupID());
        dto.setGroupName(groupEntity.getGroupName());
        dto.setIsPrivate(groupEntity.getIsPrivate());
        return dto;
    }





}
