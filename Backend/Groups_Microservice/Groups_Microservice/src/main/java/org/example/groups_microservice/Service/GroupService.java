package org.example.groups_microservice.Service;

import jakarta.transaction.Transactional;
import org.example.groups_microservice.DTO.GroupDTO;
import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Exceptions.GroupAlreadyExistsException;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Repository.GroupRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class is a service for the Group entity.
 * It is used to interact with the repository.
 * It provides methods to store, retrieve, update and delete group objects.
 */

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * createGroup method is used to create a new group.
     *
     * @param groupDTO - the group object to be created
     * @return the created group entity
     * @throws GroupAlreadyExistsException if the group already exists or is invalid
     */
    @Transactional
    public GroupEntity createGroup(GroupDTO groupDTO) throws GroupAlreadyExistsException {
        groupRepository.findByGroupName(groupDTO.getGroupName())
                .ifPresent(s -> {
                    try {
                        throw new GroupAlreadyExistsException("Group already exists with name: " + groupDTO.getGroupName());
                    } catch (GroupAlreadyExistsException e) {
                        throw new RuntimeException(e);
                    }
                });

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setGroupName(groupDTO.getGroupName());

        // Link GroupMembers to the Group
        List<GroupMemberEntity> groupMembers = groupDTO.getGroupMembers().stream()
                .map(dto -> convertToGroupMemberEntity(dto, groupEntity))
                .collect(Collectors.toList());
        groupEntity.setGroupMembers(groupMembers);

        return groupRepository.save(groupEntity);
    }

    private GroupMemberEntity convertToGroupMemberEntity(GroupMemberDTO dto, GroupEntity groupEntity) {

        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setRole(dto.getRole());
        groupMemberEntity.setGroup(groupEntity);
        return groupMemberEntity;
    }

    /**
     * getGroup method is used to retrieve a group by its ID.
     *
     * @param groupID - the ID of the group to be retrieved
     * @return the group entity
     * @throws GroupNotFoundException if the group does not exist
     */
    public GroupEntity getGroup(int groupID) throws GroupNotFoundException {
        return groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));
    }

    /**
     * getGroups method is used to retrieve all groups.
     * @return a list of group entities
     */
    public List<GroupEntity> getGroups() {
        return groupRepository.findAll();
    }

    /**
     * updateGroup method is used to update a group.
     *
     * @param groupID - the ID of the group to be updated
     * @param groupDTO - the group object with updated values
     * @return the updated group entity
     * @throws GroupNotFoundException if the group does not exist or is invalid
     */
    @Transactional
    public GroupEntity updateGroup(int groupID, GroupDTO groupDTO) throws GroupNotFoundException {
        GroupEntity groupEntity = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));

        groupEntity.setGroupName(groupDTO.getGroupName());
        List<GroupMemberEntity> groupMembers = groupDTO.getGroupMembers().stream()
                .map(dto -> convertToGroupMemberEntity(dto, groupEntity))
                .collect(Collectors.toList());
        groupEntity.setGroupMembers(groupMembers);
        return groupRepository.save(groupEntity);
    }

    /**
     * deleteGroup method is used to delete a group by its ID.
     *
     * @param groupID - the ID of the group to be deleted
     * @throws GroupNotFoundException if the group does not exist
     */
    @Transactional
    public void deleteGroup(int groupID) throws GroupNotFoundException {
        GroupEntity groupEntity = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));
        groupRepository.delete(groupEntity);
    }
}
