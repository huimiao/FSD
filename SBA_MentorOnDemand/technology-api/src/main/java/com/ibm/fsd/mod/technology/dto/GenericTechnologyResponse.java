package com.ibm.fsd.mod.technology.dto;

import com.ibm.fsd.mod.common.api.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericTechnologyResponse extends BaseResponse {
    private List<TechnologyDto> technologies;
}
