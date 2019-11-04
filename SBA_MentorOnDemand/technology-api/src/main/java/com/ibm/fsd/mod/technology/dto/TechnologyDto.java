package com.ibm.fsd.mod.technology.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TechnologyDto {
    private Long id;

    @NotBlank
    private String name;

    private String toc;

    private String prerequisites;
}
