package com.ibm.fsd.mod.training.dto;

import com.ibm.fsd.mod.common.api.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericMentorResponse extends BaseResponse {
    private List<MentorDto> mentors;
}
