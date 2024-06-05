package com.support.notice.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_notice_file")
@EntityListeners(AuditingEntityListener.class)
public class NoticeFileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fileSeq;

	@Column(nullable = false)
	private String orgName;

	@Column(nullable = false)
	private String saveName;

	@Column(nullable = false)
	private long fileSize;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "noti_seq")
	private NoticeEntity noticeEntity;


	@Builder
	public NoticeFileEntity(NoticeEntity notice, String orgFileNae, String saveFileName, long fileSize) {
		this.noticeEntity = notice;
		this.orgName = orgFileNae;
		this.saveName = saveFileName;
		this.fileSize = fileSize;
	}

}
