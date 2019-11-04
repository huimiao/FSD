package com.ibm.fsd.mod.technology.controller;

import com.ibm.fsd.mod.technology.dto.GenericTechnologyResponse;
import com.ibm.fsd.mod.technology.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TechnologyController {
    @Autowired
    private TechnologyService technologyService;

    @GetMapping("/technologies")
    public GenericTechnologyResponse getAllTechnologies() {
        return new GenericTechnologyResponse(technologyService.findAllTechnologies());
    }
}
