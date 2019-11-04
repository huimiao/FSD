package com.ibm.fsd.mod.training.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "training",
        uniqueConstraints = @UniqueConstraint(columnNames = {"musername", "sname"}))
@DynamicInsert
@Builder
@AllArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128)
    private String musername;
    @Column(length = 128)
    private String sname;
    @Column(name = "year_of_exp")
    private Integer yearOfExp;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "end_time")
    private Time endTime;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    private BigDecimal fee;
    @Column(length = 2048)
    private String profile;

    @OneToMany(mappedBy = "training")
    List<StudentTraining> studentTrainings;
}
