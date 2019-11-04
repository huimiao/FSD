package com.ibm.fsd.mod.training.repository;

import com.ibm.fsd.mod.training.commons.TrainingStatus;
import com.ibm.fsd.mod.training.model.StudentTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StudentTrainingRepository extends JpaRepository<StudentTraining, Long> {
    @Modifying
    @Query(nativeQuery = true,
            value = "update student_training set amount_received = :amount, status = 'PAID', last_updated_by = :updatedBy,last_updated_timestamp=CURRENT_TIMESTAMP where cid = :id")
    int updatePayAmount(Long id, BigDecimal amount, String updatedBy);

    @Modifying
    @Query(nativeQuery = true,
            value = "update student_training set status = :status, last_updated_by = :updatedBy,last_updated_timestamp=CURRENT_TIMESTAMP where cid = :id")
    int updateStudentTrainingStatus(Long id, TrainingStatus status, String updatedBy);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update student_training set rating = :rating, last_updated_by = :updatedBy,last_updated_timestamp=CURRENT_TIMESTAMP where cid = :id")
    int rating(Long id, int rating, String updatedBy);

    @Query("select DISTINCT s from StudentTraining s left join s.training t where s.uusername = :uusername and s.status in ('PAID' ,'CONFIRMED', 'PROPOSED') and (t.endDate > CURRENT_DATE or (t.endDate = CURRENT_DATE and t.endTime>= current_time))")
    List<StudentTraining> findUserOnGoingTrainings(String uusername);

    @Query("select DISTINCT s from StudentTraining s left join s.training t where s.uusername = :uusername and (t.endDate < CURRENT_DATE or (t.endDate = CURRENT_DATE and t.endTime< current_time)) ")
    List<StudentTraining> findUserCompletedTrainings(String uusername);
}
