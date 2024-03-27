package org.ucalgary.events_microservice.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.ucalgary.events_microservice.Entity.GroupUsersEntity;
import org.ucalgary.events_microservice.Repository.GroupUserRespository;

import jakarta.transaction.Transactional;

@Service
public class GroupUsersService {
    private final GroupUserRespository groupUserRespository;

    public GroupUsersService(GroupUserRespository groupUserRespository) {
        this.groupUserRespository = groupUserRespository;
    }

    /**
     * Add a user to a group
     * @param groupId
     * @param userId
     * @param role
     * @return
     */
    @Transactional
    public GroupUsersEntity addGroupUser(Integer groupId, String userId, String role) {
        if (getGroupUser(groupId, userId).isPresent()) {
            return null;
        }
        return groupUserRespository.save(new GroupUsersEntity(groupId, userId, role));
    }

    /**
     * Get a group user
     * @param groupId
     * @param userId
     * @return
     */
    @Transactional
    public Optional<GroupUsersEntity> getGroupUser(Integer groupId, String userId) {
        return groupUserRespository.findByGroupIdAndUserId(groupId, userId);
    }

    /**
     * Remove a user from a group
     * @param groupId
     * @param userId
     */
    @Transactional
    public void removeGroupUser(Integer groupId, String userId) {
        Optional<GroupUsersEntity> user = getGroupUser(groupId, userId);
        user.ifPresent(groupUserRespository::delete);
    }

    /**
     * Update the role of a user in a group
     * @param groupId
     * @param userId
     * @param role
     */
    @Transactional
    public void updateGroupUserRole(Integer groupId, String userId, String role) {
        Optional<GroupUsersEntity> user = getGroupUser(groupId, userId);
        if (user.isPresent()) {
            user.get().setRole(role);
            groupUserRespository.save(user.get());
        }
    }

}
