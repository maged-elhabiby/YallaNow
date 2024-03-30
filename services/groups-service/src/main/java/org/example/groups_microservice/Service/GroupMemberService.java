package org.example.groups_microservice.Service;

import jakarta.transaction.Transactional;
import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Exceptions.MemberAlreadyInGroupException;
import org.example.groups_microservice.Exceptions.MemberNotFoundException;
import org.example.groups_microservice.Repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.example.groups_microservice.Repository.GroupMemberRepository;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GroupMemberService {
    final GroupMemberRepository groupMemberRepository;
    final GroupRepository groupRepository;
    private static final Logger logger = LoggerFactory.getLogger(GroupMemberService.class);

    public GroupMemberService(GroupMemberRepository groupMemberRepository, GroupRepository groupRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
    }
    /**
     * addGroupMember method is used to add a member to a group.
     *
     * @param groupID - the ID of the group
     * @param groupMemberDTO - the member to be added
     * @return the updated group entity
     * @throws GroupNotFoundException if the group does not exist or the member is invalid
     */
    @Transactional
    public GroupMemberEntity addGroupMember(int groupID, GroupMemberDTO groupMemberDTO) throws GroupNotFoundException, MemberAlreadyInGroupException {
        logger.info("Adding member with ID: {} to group with ID: {}", groupMemberDTO.getUserID(), groupID);
        GroupEntity groupEntity = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));
        if (groupMemberRepository.findByUserIDAndGroupGroupID(groupMemberDTO.getUserID(), groupID).isPresent()) {
            throw new MemberAlreadyInGroupException("Member already exists with ID: " + groupMemberDTO.getUserID() + " in group: " + groupID);
        }

        GroupMemberEntity groupMemberEntity = new GroupMemberEntity();
        groupMemberEntity.setRole(groupMemberDTO.getRole());
        groupMemberEntity.setUserID(groupMemberDTO.getUserID());
        groupMemberEntity.setUserName(groupMemberDTO.getUserName());
        groupMemberEntity.setGroupMemberID(groupMemberDTO.getGroupMemberID());
        groupMemberEntity.setGroup(groupEntity);
        return groupMemberRepository.save(groupMemberEntity);
    }

    /**
     * removeGroupMember method is used to remove a member from a group.
     *
     * @param groupID - the ID of the group
     * @param userID - the ID of the member to be removed
     * @throws MemberNotFoundException if the group does not exist or the member is invalid
     */
    @Transactional
    public void removeGroupMember(Integer groupID, String userID) throws  MemberNotFoundException {
        logger.info("Removing member with ID: {} from group with ID: {}", userID, groupID);
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByUserIDAndGroupGroupID(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));
        GroupPubSub.publishGroupMember(groupMemberEntity, "DELETE");
        groupMemberRepository.delete(groupMemberEntity);
    }
    /**
     * getGroupMembers method is used to retrieve all members of a group.
     *
     * @param groupID - the ID of the group
     * @return a list of group member entities
     * @throws GroupNotFoundException if the group does not exist
     *
     */
    public List<GroupMemberEntity> getGroupMembers(Integer groupID) throws GroupNotFoundException {
        logger.info("Getting all members of group with ID: {}", groupID);
        GroupEntity groupEntity = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));
        return groupMemberRepository.findAllByGroupGroupID(groupID);
    }

    /**
     * getGroupMember method is used to retrieve a member of a group by its ID.
     *
     * @param groupID - the ID of the group
     * @param userID - the ID of the member
     * @return the group member entity
     * @throws MemberNotFoundException if the member does not exist
     */
    public GroupMemberEntity getGroupMember(Integer groupID, String userID) throws MemberNotFoundException {
        logger.info("Getting member with ID: {} from group with ID: {}", userID, groupID);
        return groupMemberRepository.findByUserIDAndGroupGroupID(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));
    }

    /**
     * updateGroupMember method is used to update a member of a group.
     *
     * @param groupID - the ID of the group
     * @param userID - the ID of the member
     * @param groupMemberDTO - the member object with updated values
     * @return the updated group member entity
     * @throws MemberNotFoundException if the member does not exist
     */
    @Transactional
    public GroupMemberEntity updateGroupMember(Integer groupID, String userID, GroupMemberDTO groupMemberDTO) throws MemberNotFoundException, GroupNotFoundException {
        logger.info("Updating member with ID: {} in group with ID: {}", userID, groupID);
        GroupEntity group = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByUserIDAndGroupGroupID(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));
        groupMemberEntity.setRole(groupMemberDTO.getRole());
        groupMemberEntity.setGroupMemberID(groupMemberDTO.getGroupMemberID());
        groupMemberEntity.setUserName(groupMemberDTO.getUserName());
        groupMemberEntity.setUserID(groupMemberDTO.getUserID());
        groupMemberEntity.setGroup(group);
        group.setMemberCount(group.getGroupMembers().size());
        groupRepository.save(group);
        return groupMemberRepository.save(groupMemberEntity);

    }

    /**
     * deleteGroupMember method is used to delete a member of a group by its ID.
     * @param groupID - the ID of the group
     * @param userID - the ID of the member to be deleted
     * @throws  MemberNotFoundException if the group does not exist or the member does not exist
     */
    @Transactional
    public void deleteGroupMember(Integer groupID, String userID) throws MemberNotFoundException {
        logger.info("Deleting member with ID: {} from group with ID: {}", userID, groupID);
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByUserIDAndGroupGroupID(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));
        groupMemberRepository.delete(groupMemberEntity);
    }

    /**
     * updateGroupMembers method is used to update the members of a group.
     * @param groupEntity - the group entity
     * @param groupMembers - the list of group member DTOs
     */
    @Transactional
    public void updateGroupMembers(GroupEntity groupEntity, List<GroupMemberDTO> groupMembers) {
        logger.info("Updating members of group with ID: {}", groupEntity.getGroupID());
        Map<String, GroupMemberEntity> existingMembersById = groupEntity.getGroupMembers().stream()
                .filter(member -> member.getUserID() != null)
                .collect(Collectors.toMap(GroupMemberEntity::getUserID, member -> member));

        for (GroupMemberDTO dto : groupMembers) {
            if (dto.getUserID() != null) {
                GroupMemberEntity existingMember = existingMembersById.get(dto.getUserID());
                // If the member exists, update the role
                if (existingMember != null) {
                    mapDTOToEntity(dto, existingMember);
                    groupMemberRepository.save(existingMember);
                } else {
                    // If the member does not exist, create a new member
                    GroupMemberEntity newMember = new GroupMemberEntity();
                    mapDTOToEntity(dto, newMember);
                    newMember.setGroup(groupEntity);
                    groupEntity.getGroupMembers().add(newMember);
                    groupEntity.setMemberCount(groupEntity.getGroupMembers().size());
                    groupMemberRepository.save(newMember);
                }
            }
        }
        groupRepository.save(groupEntity);
    }

    /**
     * mapDTOToEntity method is used to map a group member DTO to a group member entity.
     * @param dto - the group member DTO
     * @param entity - the group member entity
     */
    private void mapDTOToEntity(GroupMemberDTO dto, GroupMemberEntity entity) {
        entity.setGroupMemberID(dto.getGroupMemberID());
        entity.setRole(dto.getRole());
        entity.setUserID(dto.getUserID());
        entity.setUserName(dto.getUserName());
    }

    /**
     * getGroupsByUserID method is used to retrieve all groups by a user ID.
     * @param userID - the ID of the user
     * @return a list of group member entities
     */
    @Transactional
    public List<GroupMemberEntity> getGroupsByUserID(String userID) {
        logger.info("Getting all groups by user with ID: {}", userID);
        return groupMemberRepository.findAllGroupByUserID(userID);
    }
}
