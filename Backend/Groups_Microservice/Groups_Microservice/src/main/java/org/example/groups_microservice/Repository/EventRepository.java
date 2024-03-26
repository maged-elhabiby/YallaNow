package org.example.groups_microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.example.groups_microservice.Entity.EventEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer>{

    Optional<EventEntity> findByEventID(Integer eventID);

    List<EventEntity> findByGroupGroupName(String groupName);
    List<EventEntity> findByGroupGroupID(Integer groupID);



}
