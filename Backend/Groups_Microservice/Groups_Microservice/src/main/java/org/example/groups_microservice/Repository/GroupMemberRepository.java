package org.example.groups_microservice.Repository;

import org.example.groups_microservice.Entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This class is a repository for the GroupMember entity.
 * It is used to interact with the database.
 * It provides methods to store, retrieve, update and delete group member objects.
 */

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Integer>{
    Optional<GroupMemberEntity> findByUserID(Integer userID);
    List<GroupMemberEntity> findByGroupGroupID(Integer groupID);

    Optional<GroupMemberEntity> findByUserIDAndGroupGroupID(Integer userID, Integer groupID);

    //Optional<GroupMemberEntity> findByIdAndGroupId(Integer userID, Integer groupID);

    List<GroupMemberEntity> findAllByGroupGroupID(Integer groupID);
}
