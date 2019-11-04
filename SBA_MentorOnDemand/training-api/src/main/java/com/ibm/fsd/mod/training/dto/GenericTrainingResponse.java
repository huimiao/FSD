package com.ibm.fsd.mod.training.dto;

import com.ibm.fsd.mod.common.api.BaseResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericTrainingResponse extends BaseResponse {
    private TrainingDto trainingDto;
}
