package org.example.groups_microservice.Repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.groups_microservice.Entity.GroupEntity;

import java.util.Optional;

/**
 * This class is a repository for the Group entity.
 * It is used to interact with the database.
 * It provides methods to store, retrieve, update and delete group objects.
 */

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer>{

    Optional<GroupEntity> findByGroupID(Integer groupID);
    Optional<GroupEntity> findByGroupName(String groupName);
}



