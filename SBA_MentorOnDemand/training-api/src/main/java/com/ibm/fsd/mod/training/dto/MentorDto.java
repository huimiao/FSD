package com.ibm.fsd.mod.training.dto;

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
public class MentorDto {
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
    private float avgRating = 0;
    private int registeredNum;
}
