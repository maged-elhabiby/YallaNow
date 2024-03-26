package org.example.groups_microservice.Controller;


import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Exceptions.MemberNotFoundException;
import org.example.groups_microservice.Service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/microservice/groups/{groupID}/members")
public class GroupMemberController {
    final GroupMemberService groupMemberService;
    @Autowired
    public GroupMemberController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    /**
     * getGroupMembers method is used to retrieve all members of a group.
     * @param groupID - the ID of the group
     * @return a list of group member entities
     * @throws GroupNotFoundException if the group does not exist
     */
    @GetMapping
    public ResponseEntity<List<GroupMemberDTO>> getGroupMembers(@PathVariable int groupID) throws GroupNotFoundException {
        List<GroupMemberEntity> groupMembers = groupMemberService.getGroupMembers(groupID);
        List<GroupMemberDTO> groupMemberDTOs = groupMembers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupMemberDTOs);
    }


    private GroupMemberDTO convertToDto(GroupMemberEntity groupMemberEntity) {
        GroupMemberDTO dto = new GroupMemberDTO();
        dto.setRole(groupMemberEntity.getRole());
        dto.setGroupID(groupMemberEntity.getGroup().getGroupID());
        dto.setUserName(groupMemberEntity.getUserName());
        dto.setGroupMemberID(groupMemberEntity.getGroupMemberID());
        dto.setUserID(groupMemberEntity.getUserID());
        return dto;
    }

    /**
     * addGroupMember method is used to add a member to a group.
     * @param groupID - the ID of the group
     * @param groupMemberDTO - the member to be added
     * @return the updated group entity
     * @throws GroupNotFoundException if the group does not exist or the member is invalid
     */
    @PostMapping
    public ResponseEntity<GroupMemberDTO> addGroupMember(@PathVariable int groupID, @RequestBody GroupMemberDTO groupMemberDTO) throws GroupNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberService.addGroupMember(groupID, groupMemberDTO);
        return ResponseEntity.ok(convertToDto(groupMemberEntity));
    }

    /**
     * removeGroupMember method is used to remove a member from a group.
     * @param groupID - the ID of the group
     * @param userID - the ID of the member to be removed
     * @throws MemberNotFoundException if the group does not exist or the member is invalid
     */

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> removeGroupMember(@PathVariable Integer groupID, @PathVariable Integer userID) throws MemberNotFoundException {
        groupMemberService.removeGroupMember(groupID, userID);
        return ResponseEntity.noContent().build();
    }

    /**
     * getGroupMember method is used to retrieve a member by its ID.
     * @param groupID - the ID of the group
     * @param userID - the ID of the member
     * @return the group member entity
     * @throws MemberNotFoundException if the member does not exist
     */

    @GetMapping("/{userID}")
    public ResponseEntity<GroupMemberDTO> getGroupMember(@PathVariable Integer groupID, @PathVariable Integer userID) throws MemberNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberService.getGroupMember(groupID, userID);
        return ResponseEntity.ok(convertToDto(groupMemberEntity));
    }

    /**
     * updateGroupMember method is used to update a member by its ID.
     * @param groupID - the ID of the group
     * @param userID - the ID of the member
     * @param groupMemberDTO - the member object with updated values
     * @return the updated group member entity
     * @throws MemberNotFoundException if the member does not exist
     */

    @PutMapping("/{userID}")
    public ResponseEntity<GroupMemberDTO> updateGroupMember(@PathVariable Integer groupID, @PathVariable Integer userID, @RequestBody GroupMemberDTO groupMemberDTO) throws MemberNotFoundException, GroupNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberService.updateGroupMember(groupID, userID, groupMemberDTO);
        return ResponseEntity.ok(convertToDto(groupMemberEntity));
    }

}
