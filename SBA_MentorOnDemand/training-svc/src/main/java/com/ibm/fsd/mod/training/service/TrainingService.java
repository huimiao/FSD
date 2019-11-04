package com.ibm.fsd.mod.training.service;

import com.ibm.fsd.mod.training.commons.RequestStatusType;
import com.ibm.fsd.mod.training.commons.TrainingStatus;
import com.ibm.fsd.mod.training.dto.MentorDto;
import com.ibm.fsd.mod.training.dto.StudentTrainingDto;
import com.ibm.fsd.mod.training.dto.TrainingDto;
import com.ibm.fsd.mod.training.model.StudentTraining;
import com.ibm.fsd.mod.training.model.Training;
import com.ibm.fsd.mod.training.repository.StudentTrainingRepository;
import com.ibm.fsd.mod.training.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TrainingService {
    private final ModelMapper modelMapper;
    private final TrainingRepository trainingRepository;
    private final StudentTrainingRepository studentTrainingRepository;

    /**
     * new training
     *
     * @param newTraining
     * @return
     */
    public TrainingDto newTraining(TrainingDto newTraining) {
        return convertToDto(trainingRepository.save(convertToModel(newTraining)));
    }

    public List<MentorDto> getAllMentorOnGoingTrainings() {
        return trainingRepository.findAllMentors().stream().map(t -> convertToMentorDto(t)).collect(toList());
    }

    public List<StudentTrainingDto> getUserTrainings(String username, RequestStatusType type) {
        List<StudentTraining> studentTrainings = new ArrayList<>();

        if (type == RequestStatusType.ONGOING) {
            studentTrainings = studentTrainingRepository.findUserOnGoingTrainings(username);
        } else if (type == RequestStatusType.COMPLETED) {
            studentTrainings = studentTrainingRepository.findUserCompletedTrainings(username);
        } else {
            new RuntimeException("invalid RequestStatusType should be defined in RequestStatusType");
        }

        return convertToStudentDto(studentTrainings);
    }

    public List<MentorDto> getMentorTrainings(String username, RequestStatusType type) {
        List<Training> studentTrainings = new ArrayList<>();

        if (type == RequestStatusType.ONGOING) {
            studentTrainings = trainingRepository.findMentorOnGoingTrainings(username);
        } else if (type == RequestStatusType.COMPLETED) {
            studentTrainings = trainingRepository.findMentorCompletedTrainings(username);
        } else {
            new RuntimeException("invalid RequestStatusType should be defined in RequestStatusType");
        }

        return studentTrainings.stream()
                .map(t -> convertToMentorDto(t))
                .collect(toList());
    }

    public void finalize(Long id, String username, BigDecimal fee) {
        studentTrainingRepository.updatePayAmount(id, fee, username);
    }

    public void proposeATraining(Long trainingId, String username) {
        Training training = trainingRepository.findById(trainingId).get();
        StudentTraining studentTraining = StudentTraining.builder()
                .training(training)
                .uusername(username)
                .status(TrainingStatus.PROPOSED)
                .lastUpdatedBy(username)
                .rating(10)
                .build();
        studentTrainingRepository.save(studentTraining);
    }

    public void rating(Long id, int rating, String username) {
        this.studentTrainingRepository.rating(id, rating, username);
    }

    private TrainingDto convertToDto(Training training) {
        if (training == null)
            return null;
        return modelMapper.map(training, TrainingDto.class);
    }

    private List<StudentTrainingDto> convertToStudentDto(Training training) {
        List<StudentTrainingDto> studentTrainingDtos = new ArrayList<>();
        if (training == null)
            return null;

        if (training.getStudentTrainings().size() > 0) {
            studentTrainingDtos = training.getStudentTrainings().stream().map(s -> {
                StudentTrainingDto studentTrainingDto = modelMapper.map(training, StudentTrainingDto.class);
                studentTrainingDto.setUusername(s.getUusername());
                studentTrainingDto.setStatus(s.getStatus());
                studentTrainingDto.setRating(s.getRating());
                studentTrainingDto.setAmountReceived(s.getAmountReceived());

                return studentTrainingDto;
            }).collect(Collectors.toList());
        } else {
            studentTrainingDtos.add(modelMapper.map(training, StudentTrainingDto.class));
        }
        return studentTrainingDtos;
    }

    private List<StudentTrainingDto> convertToStudentDto(List<StudentTraining> training) {
        return training.stream().map(t -> {
            StudentTrainingDto sdto = modelMapper.map(t.getTraining(), StudentTrainingDto.class);
            sdto.setRating(t.getRating());
            sdto.setUusername(t.getUusername());
            return sdto;
        }).collect(toList());
    }

    private MentorDto convertToMentorDto(Training training) {
        int sumRating = 10;
        float avgRating = 10.0f;
        if (training == null)
            return null;

        sumRating = training.getStudentTrainings().stream().collect(summingInt(StudentTraining::getRating));

        if (training.getStudentTrainings().size() == 0) {
            avgRating = 10.0f;
        } else {
            avgRating = (float) (Math.round(sumRating / training.getStudentTrainings().size() * 10)) / 10;
        }

        MentorDto mentorDto = modelMapper.map(training, MentorDto.class);
        mentorDto.setAvgRating(avgRating);
        mentorDto.setRegisteredNum(training.getStudentTrainings().size());

        return mentorDto;
    }

    private Training convertToModel(TrainingDto trainingDto) {
        if (trainingDto == null)
            return null;
        return modelMapper.map(trainingDto
                , Training.class);
    }
}
