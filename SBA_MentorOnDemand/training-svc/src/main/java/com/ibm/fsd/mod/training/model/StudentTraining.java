package com.ibm.fsd.mod.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibm.fsd.mod.training.commons.TrainingStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_training",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cid", "uusername"}))
@DynamicInsert
@Builder
@AllArgsConstructor
public class StudentTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128)
    private String uusername;
    @Enumerated(EnumType.STRING)
    private TrainingStatus status = TrainingStatus.PROPOSED;
    @Column(columnDefinition = "int default 10")
    private int rating = 0;
    @Column(name = "amount_received", columnDefinition = "decimal default 0")
    private BigDecimal amountReceived = BigDecimal.ZERO;
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
    @Column(name = "last_updated_timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date lastUpdatedTimeStamp;

    @JsonIgnore
    @ManyToOne(targetEntity = Training.class)
    @JoinColumn(name = "cid")
    private Training training;

    @Transient
    private Long cid;
}
