package com.ibm.fsd.mod.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableFeignClients(basePackages = {"com.ibm.fsd.mod"})
@AutoConfigureMockMvc
@Slf4j
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetAccountDetailByUsername() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/v1/account/huimiao@cn.ibm.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.account.username").value("huimiao@cn.ibm.com"))
                .andExpect(jsonPath("$.account.password").value("123456"))
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetAccountDetailByUsernameFailed() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/v1/account/huimiao@ibm.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.account").isEmpty())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetAccountProfileByUsername() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/account/huimiao@cn.ibm.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.account.username").value("huimiao@cn.ibm.com"))
                .andExpect(jsonPath("$.account.password").value("xxxx"))
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testRegister() throws Exception {
        String userDto = "{\n" +
                "\t\"username\": \"hui@ibm.com\",\n" +
                "\t\"password\": \"123\",\n" +
                "\t\"firstName\": \"Hui\",\n" +
                "\t\"lastName\": \"Miao\",\n" +
                "\t\"contactNumber\": 123,\n" +
                "\t\"isMentor\": \"Y\"\n" +
                "}";
        MvcResult mvcResult = mockMvc.perform(post("/v1/signup").contentType(MediaType.APPLICATION_JSON).content(userDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.account.username").value("hui@ibm.com"))
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
}
