package com.fst.elearning.repository;

import com.fst.elearning.entity.Cours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    Page<Cours> findAll(Pageable pageable);
}
