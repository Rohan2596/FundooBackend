package com.bridgelabz.fundoo.labels.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.labels.model.Labels;

public interface LabelRespository extends JpaRepository<Labels, String> {
public Optional<Labels> findByLabelName(String labelName);
}
