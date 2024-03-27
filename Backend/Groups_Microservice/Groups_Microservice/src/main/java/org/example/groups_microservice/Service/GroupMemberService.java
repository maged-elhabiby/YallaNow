package org.example.groups_microservice.Service;

import jakarta.transaction.Transactional;
import org.example.groups_microservice.DTO.GroupMemberDTO;
import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.example.groups_microservice.Entity.GroupEntity;
import org.example.groups_microservice.Exceptions.GroupNotFoundException;
import org.example.groups_microservice.Exceptions.MemberNotFoundException;
import org.example.groups_microservice.Repository.GroupRepository;
import org.springframework.stereotype.Service;
import org.example.groups_microservice.Repository.GroupMemberRepository;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

@Service
public class GroupMemberService {
    final GroupMemberRepository groupMemberRepository;
    final GroupRepository groupRepository;

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
    public GroupMemberEntity addGroupMember(int groupID, GroupMemberDTO groupMemberDTO) throws GroupNotFoundException {
        GroupEntity groupEntity = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));

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
    public void removeGroupMember(Integer groupID, Integer userID) throws  MemberNotFoundException {
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
    public GroupMemberEntity getGroupMember(Integer groupID, Integer userID) throws MemberNotFoundException {
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
    public GroupMemberEntity updateGroupMember(Integer groupID, Integer userID, GroupMemberDTO groupMemberDTO) throws MemberNotFoundException, GroupNotFoundException {

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
    public void deleteGroupMember(Integer groupID, Integer userID) throws MemberNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByUserIDAndGroupGroupID(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));

        groupMemberRepository.delete(groupMemberEntity);
    }

    @Transactional
    public void updateGroupMembers(GroupEntity groupEntity, List<GroupMemberDTO> groupMembers) {
        Map<Integer, GroupMemberEntity> existingMembersById = groupEntity.getGroupMembers().stream()
                .filter(member -> member.getUserID() != null)
                .collect(Collectors.toMap(GroupMemberEntity::getUserID, member -> member));
        for (GroupMemberDTO dto : groupMembers) {
            if (dto.getUserID() != null) {
                GroupMemberEntity existingMember = existingMembersById.get(dto.getUserID());
                // If the member exists, update the role
                if (existingMember != null) {
                    mapDTOToEntity(dto, existingMember);
                } else {
                    // If the member does not exist, create a new member
                    GroupMemberEntity newMember = new GroupMemberEntity();
                    mapDTOToEntity(dto, newMember);
                    newMember.setGroup(groupEntity);
                    groupEntity.getGroupMembers().add(newMember);
                    groupEntity.setMemberCount(groupEntity.getGroupMembers().size());

                }
            }
            groupRepository.save(groupEntity);
        }

    }
    private void mapDTOToEntity(GroupMemberDTO dto, GroupMemberEntity entity) {
        entity.setGroupMemberID(dto.getGroupMemberID());
        entity.setRole(dto.getRole());
        entity.setUserID(dto.getUserID());
        entity.setUserName(dto.getUserName());


    }
    @Transactional
    public List<GroupMemberEntity> getGroupsByUserID(Integer userID) {
        return groupMemberRepository.findAllGroupByUserID(userID);
    }
}
