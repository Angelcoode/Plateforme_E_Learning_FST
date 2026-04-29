package com.fst.elearning.repository;

import com.fst.elearning.entity.ReponseApprenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseApprenantRepository extends JpaRepository<ReponseApprenant, Long> {
}
