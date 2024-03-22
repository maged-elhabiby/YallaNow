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

import java.util.List;

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
    public void removeGroupMember(int groupID, int userID) throws  MemberNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdAndGroupId(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));

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
    public List<GroupMemberEntity> getGroupMembers(int groupID) throws GroupNotFoundException {
        GroupEntity groupEntity = groupRepository.findById(groupID)
                .orElseThrow(() -> new GroupNotFoundException("Group does not exist with ID: " + groupID));

        return groupMemberRepository.findAllByGroupId(groupID);
    }

    /**
     * getGroupMember method is used to retrieve a member of a group by its ID.
     *
     * @param groupID - the ID of the group
     * @param userID - the ID of the member
     * @return the group member entity
     * @throws MemberNotFoundException if the member does not exist
     */
    public GroupMemberEntity getGroupMember(int groupID, int userID) throws MemberNotFoundException {
        return groupMemberRepository.findByIdAndGroupId(userID, groupID)
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
    public GroupMemberEntity updateGroupMember(int groupID, int userID, GroupMemberDTO groupMemberDTO) throws  MemberNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdAndGroupId(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));

        groupMemberEntity.setRole(groupMemberDTO.getRole());
        return groupMemberRepository.save(groupMemberEntity);
    }

    /**
     * deleteGroupMember method is used to delete a member of a group by its ID.
     * @param groupID - the ID of the group
     * @param userID - the ID of the member to be deleted
     * @throws  MemberNotFoundException if the group does not exist or the member does not exist
     */
    @Transactional
    public void deleteGroupMember(int groupID, int userID) throws MemberNotFoundException {
        GroupMemberEntity groupMemberEntity = groupMemberRepository.findByIdAndGroupId(userID, groupID)
                .orElseThrow(() -> new MemberNotFoundException("Member does not exist with ID: " + userID + " in group: " + groupID));

        groupMemberRepository.delete(groupMemberEntity);
    }

}
