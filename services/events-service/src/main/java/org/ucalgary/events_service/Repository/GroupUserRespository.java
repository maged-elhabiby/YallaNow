package org.ucalgary.events_service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ucalgary.events_service.Entity.GroupUsersEntity;

@Repository
public interface GroupUserRespository extends JpaRepository<GroupUsersEntity, Integer> {
    Optional<GroupUsersEntity> findByGroupIdAndUserId(Integer groupId, String userId);
    
}
