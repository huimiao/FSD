package com.ibm.fsd.mod.technology.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = {"com.ibm.fsd.mod"})
@AutoConfigureMockMvc
@Slf4j
public class TechnologyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAllTechnologies() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/technologies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.technologies.length()").value(3))
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
}
