package com.ibm.fsd.mod.training.dto;

import com.ibm.fsd.mod.training.commons.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentTrainingDto {
    private Long id;
    private String musername;
    private String sname;
    private Integer yearOfExp;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    private BigDecimal fee = BigDecimal.ZERO;
    private String profile;
    private String uusername;
    private TrainingStatus status = TrainingStatus.PROPOSED;
    private int rating = 10;
    private BigDecimal amountReceived = BigDecimal.ZERO;
}
