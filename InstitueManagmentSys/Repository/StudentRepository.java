package com.thedynamicdoers.InstitueManagmentSys.Repository;

import com.thedynamicdoers.InstitueManagmentSys.Model.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
