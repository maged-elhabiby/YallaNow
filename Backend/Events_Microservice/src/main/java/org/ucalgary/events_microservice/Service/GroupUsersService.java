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

    @Transactional
    public void addGroupUser(Integer groupId, String userId, String role) {
        groupUserRespository.save(new GroupUsersEntity(groupId, userId, role));
    }

    @Transactional
    public Optional<GroupUsersEntity> getGroupUser(Integer groupId, String userId) {
        return groupUserRespository.findByGroupIdAndUserId(groupId, userId);
    }

    @Transactional
    public void removeGroupUser(Integer groupId, String userId) {
        Optional<GroupUsersEntity> user = getGroupUser(groupId, userId);
        if (user.isPresent()) {
            groupUserRespository.delete(user.get());
        }
    }

    @Transactional
    public void updateGroupUserRole(Integer groupId, String userId, String role) {
        Optional<GroupUsersEntity> user = getGroupUser(groupId, userId);
        if (user.isPresent()) {
            user.get().setRole(role);
            groupUserRespository.save(user.get());
        }
    }

}
