package com.project.sparta.alarm2.repository;

import com.project.sparta.alarm2.entity.Alarm;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlarmRespository extends JpaRepository<Alarm, Long>{

    @Query("select a from Alarm a where a.userId=:userId order by a.createAt desc")
    Page<Alarm> findMyAlarmList(@Param("userId")Long userId, Pageable pageable);

    Optional<Alarm> findByIdAndUserId(Long alarmId, Long userId);
}
