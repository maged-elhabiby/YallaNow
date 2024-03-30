package org.ucalgary.events_service.Service;

import org.ucalgary.events_service.Repository.GroupUserRespository;
import org.ucalgary.events_service.Entity.GroupUsersEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class GroupUsersService {
    private final GroupUserRespository groupUserRespository;
    private static final Logger logger = LoggerFactory.getLogger(GroupUsersService.class);


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
            logger.info("User {} already in group {}", userId, groupId);
            return null;
        }
        GroupUsersEntity groupUser = groupUserRespository.save(new GroupUsersEntity(groupId, userId, role));
        logger.info("Added user {} with role {} to group {}", userId, role, groupId);
        return groupUser;
    }


    /**
     * Get a group user
     * @param groupId
     * @param userId
     * @return
     */
    @Transactional
    public Optional<GroupUsersEntity> getGroupUser(Integer groupId, String userId) {
        logger.info("Retrieving user {} from group {}", userId, groupId);
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
        if (user.isPresent()) {
            groupUserRespository.delete(user.get());
            logger.info("Removed user {} from group {}", userId, groupId);
        } else {
            logger.info("User {} not found in group {}", userId, groupId);
        }
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
            logger.info("Updated role of user {} to {} in group {}", userId, role, groupId);
        } else {
            logger.info("User {} not found in group {}", userId, groupId);
        }
    }

}
