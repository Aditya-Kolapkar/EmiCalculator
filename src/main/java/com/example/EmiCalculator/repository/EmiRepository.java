package com.example.EmiCalculator.repository;

import com.example.EmiCalculator.entity.EmiCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmiRepository extends JpaRepository<EmiCalculation,Long> {

}
