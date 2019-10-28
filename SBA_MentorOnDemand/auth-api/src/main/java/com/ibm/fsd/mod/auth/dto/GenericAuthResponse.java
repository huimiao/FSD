package com.ibm.fsd.mod.auth.dto;

import com.ibm.fsd.mod.common.api.BaseResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenericAuthResponse extends BaseResponse {
    private TokenUserDto tokenUserDto;
}
