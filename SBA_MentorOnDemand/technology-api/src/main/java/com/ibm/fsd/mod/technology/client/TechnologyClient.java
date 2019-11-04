package com.ibm.fsd.mod.technology.client;

import com.ibm.fsd.mod.technology.TechnologyConstant;
import com.ibm.fsd.mod.technology.dto.GenericTechnologyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = TechnologyConstant.SERVICE_NAME, path = "/v1")
public interface TechnologyClient {
    @GetMapping("/technologies")
    public GenericTechnologyResponse getAllTechnologies() ;
}
