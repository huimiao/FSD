package com.ibm.fsd.mod.technology.service;

import com.ibm.fsd.mod.technology.dto.TechnologyDto;
import com.ibm.fsd.mod.technology.model.Technology;
import com.ibm.fsd.mod.technology.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TechnologyService {
    private final ModelMapper modelMapper;
    private final TechnologyRepository technologyRepository;

    public List<TechnologyDto> findAllTechnologies() {
        return technologyRepository.findAll().stream()
                .map(t -> convertToDto(t))
                .collect(toList());
    }

    private TechnologyDto convertToDto(Technology technology) {
        if (technology == null)
            return null;
        return modelMapper.map(technology, TechnologyDto.class);
    }
}
