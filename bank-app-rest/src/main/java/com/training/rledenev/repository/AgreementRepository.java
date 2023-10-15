package com.training.rledenev.repository;

import com.training.rledenev.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    @Query("select a from Agreement a where a.status = 'NEW'")
    List<Agreement> findAllNewAgreements();
}
