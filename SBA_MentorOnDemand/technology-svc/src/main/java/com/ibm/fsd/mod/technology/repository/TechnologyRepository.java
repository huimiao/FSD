package com.ibm.fsd.mod.technology.repository;

import com.ibm.fsd.mod.technology.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
