package com.ibm.fsd.mod.training.repository;

import com.ibm.fsd.mod.training.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * Find ALL the on-going trainings related to the mentor
     *
     * @param musername mentor's username
     * @return
     */
    @Query("select DISTINCT t from Training t left join t.studentTrainings as s where t.musername = :musername and (t.endDate > CURRENT_DATE or (t.endDate = CURRENT_DATE and t.endTime>= current_time))")
    List<Training> findMentorOnGoingTrainings(String musername);

    @Query("select DISTINCT t from Training t left join t.studentTrainings as s where t.musername = :musername and (t.endDate < CURRENT_DATE or (t.endDate = CURRENT_DATE and t.endTime< current_time))")
    List<Training> findMentorCompletedTrainings(String musername);

    @Query("select DISTINCT t from Training t left join t.studentTrainings as s where (t.endDate > CURRENT_DATE or (t.endDate = CURRENT_DATE and t.endTime>= current_time))")
    List<Training> findAllMentors();
}
