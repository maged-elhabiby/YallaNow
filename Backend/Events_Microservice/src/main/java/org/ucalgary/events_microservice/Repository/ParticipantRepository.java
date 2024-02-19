   package org.ucalgary.events_microservice.Repository;

   import org.springframework.data.jpa.repository.JpaRepository;
   import org.springframework.stereotype.Repository;
   import org.ucalgary.events_microservice.Entity.ParticipantEntity;


   @Repository
   public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Integer>{

   }
