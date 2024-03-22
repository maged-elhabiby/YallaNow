package org.example.groups_microservice.Controller;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Exceptions.GroupAlreadyExistsException;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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
        List<GroupEntity> groups = groupService.getGroups();
        List<GroupDTO> groupDTOS = groups.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(groupDTOS);
    }

    private GroupDTO convertToDto(GroupEntity groupEntity) {
        GroupDTO dto = new GroupDTO();
        dto.setGroupID(groupEntity.getGroupID());
        dto.setGroupName(groupEntity.getGroupName());
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
        return ResponseEntity.ok(convertToDto(groupEntity));
    }

    /**
     * getGroup method is used to retrieve a group by its ID.
     * @param groupID - the ID of the group
     * @return the group entity
     */
    @GetMapping("/{groupID}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable int groupID) throws GroupNotFoundException {
        GroupEntity groupEntity = groupService.getGroup(groupID);
        return ResponseEntity.ok(convertToDto(groupEntity));
    }

    /**
     * deleteGroup method is used to delete a group by its ID.
     * @param groupID - the ID of the group
     */
    @DeleteMapping("/{groupID}")
    public ResponseEntity<Void> deleteGroup(@PathVariable int groupID) throws GroupNotFoundException {
        groupService.deleteGroup(groupID);
        return ResponseEntity.noContent().build();
    }

    /**
     * updateGroup method is used to update a group by its ID.
     * @param groupID - the ID of the group
     * @param groupDTO - the group object with updated values
     * @return the updated group entity
     */
    @PutMapping("/{groupID}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable int groupID, @RequestBody GroupDTO groupDTO) throws GroupNotFoundException {
        GroupEntity groupEntity = groupService.updateGroup(groupID, groupDTO);
        return ResponseEntity.ok(convertToDto(groupEntity));
    }
}
