package org.yascode.firstsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yascode.firstsystem.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
