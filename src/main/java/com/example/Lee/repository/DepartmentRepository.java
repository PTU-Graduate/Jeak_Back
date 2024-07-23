package com.example.Lee.repository;

import com.example.Lee.model.DepartMentCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartMentCodeModel, String> {
    Optional<DepartMentCodeModel> findByMembDep(String membDep);
}
