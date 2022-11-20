package com.zerobase.fastlms.History.repository;

import com.zerobase.fastlms.History.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<LoginHistory, Long> {

}
