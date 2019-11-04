package com.ibm.fsd.mod.training.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDto {
    private Long id;
    @NotBlank
    private String musername;
    @NotBlank
    private String sname;
    @Positive
    @NotNull
    private Integer yearOfExp;
    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private Time startTime;
    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
    private Time endTime;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    @Positive(message = "Should be positive amount")
    private BigDecimal fee = BigDecimal.ZERO;

    private String profile;
}
