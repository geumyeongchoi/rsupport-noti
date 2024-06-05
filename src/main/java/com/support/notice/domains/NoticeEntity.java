package com.support.notice.domains;

import com.support.notice.notice.vo.NoticeSaveRequest;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_notice")
@EntityListeners(AuditingEntityListener.class)
public class NoticeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long notiSeq;

	@Column(nullable = false)
	private String title;

	private String content;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@CreatedDate
	private LocalDateTime createDate;

	@LastModifiedDate
	private LocalDateTime updateDate;

	@Column(nullable = false)
	private String createUser;

	@Column(nullable = false)
	private Integer viewCount;

	@BatchSize(size = 300)
	@OneToMany(mappedBy = "noticeEntity", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<NoticeFileEntity> noticeFileEntities = new ArrayList<>();

	public NoticeEntity(NoticeSaveRequest request){
		this.title = request.getTitle();
		this.content = request.getContent();
		this.startDate = request.getStartDate();
		this.endDate = request.getEndDate();
		this.createUser = request.getCreateUser();
		this.viewCount = 0; 	//생성시 0으로 초기화
	}

	/**
	 * 공지사항에 첨부파일 매핑(1:N)
	 * @param file
	 */
	public void addFile(NoticeFileEntity file) {
		this.noticeFileEntities.add(file);
	}

	/**
	 * 공지사항 수정
	 * @param title
	 * @param content
	 */
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	/**
	 * 조회 수 증가
	 */
	public void addViewCount() {
		this.viewCount++;
	}
}
