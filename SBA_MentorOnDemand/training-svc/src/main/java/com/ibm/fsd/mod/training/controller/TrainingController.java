package com.ibm.fsd.mod.training.controller;

import com.ibm.fsd.mod.common.api.BaseResponse;
import com.ibm.fsd.mod.training.commons.RequestStatusType;
import com.ibm.fsd.mod.training.dto.GenericMentorResponse;
import com.ibm.fsd.mod.training.dto.GenericStudentTrainingsResponse;
import com.ibm.fsd.mod.training.dto.GenericTrainingResponse;
import com.ibm.fsd.mod.training.dto.TrainingDto;
import com.ibm.fsd.mod.training.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    /**
     * Mentor creates new Training api
     *
     * @param newTraining The training information
     * @return
     */
    @PostMapping(value = "/training",
            produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    public GenericTrainingResponse newTraining(@Validated @RequestBody TrainingDto newTraining) {
        return new GenericTrainingResponse(trainingService.newTraining(newTraining));
    }

    @GetMapping(value = "/training/user",
            produces = "application/json;charset=UTF-8")
    public GenericStudentTrainingsResponse getAllStudentTrainingsByStatus(@RequestParam String username, @RequestParam RequestStatusType status) {
        return new GenericStudentTrainingsResponse(trainingService.getUserTrainings(username, status));
    }

    @GetMapping(value = "/training/mentor",
            produces = "application/json;charset=UTF-8")
    public GenericMentorResponse getMentorTrainingsByStatus(@RequestParam String username, @RequestParam RequestStatusType status) {
        return new GenericMentorResponse(trainingService.getMentorTrainings(username, status));
    }

    @GetMapping(value = "/training/all",
            produces = "application/json;charset=UTF-8")
    public GenericMentorResponse getAllMentorOnGoingTrainings() {
        return new GenericMentorResponse(trainingService.getAllMentorOnGoingTrainings());
    }

    /**
     * API that create a new training record by submit a proprose request the mentor
     *
     * @param id
     * @param username
     * @return
     */
    @PostMapping(value = "/training/{id}/{username}")
    public BaseResponse proposeATraining(@PathVariable Long id, @PathVariable String username) {
        trainingService.proposeATraining(id, username);

        return BaseResponse.builder().build();
    }

    @PostMapping(value = "/training/finalize/{id}/{username}")
    public BaseResponse finalizeTraining(@PathVariable Long id, @PathVariable String username, @RequestParam BigDecimal fee) {
        trainingService.finalize(id, username, fee);

        return BaseResponse.builder().build();
    }

    @PostMapping(value = "/training/rating/{id}/{rating}")
    public BaseResponse rating(@PathVariable Long id, @PathVariable int rating, @RequestParam String username) {
        trainingService.rating(id, rating, username);
        return BaseResponse.builder().build();
    }
}
