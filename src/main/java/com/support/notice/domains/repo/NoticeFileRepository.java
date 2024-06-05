package com.support.notice.domains.repo;

import com.support.notice.domains.NoticeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFileEntity, Long> {

}
