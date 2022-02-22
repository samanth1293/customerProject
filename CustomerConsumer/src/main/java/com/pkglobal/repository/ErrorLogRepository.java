package com.pkglobal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pkglobal.models.ErrorLog;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

}
