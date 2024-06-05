package com.support.notice.notice.vo;

import com.support.notice.domains.NoticeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ApiModel(description = "공지사항 조회 모델")
public class NoticeResponse {

    @ApiModelProperty(value = "공지사항 고유 ID")
    private final long notiSeq;

    @ApiModelProperty(value = "제목")
    private final String title;

    @ApiModelProperty(value = "내용")
    private final String content;

    @ApiModelProperty(value = "작성자")
    private final String createUser;

    @ApiModelProperty(value = "작성일시")
    private final LocalDateTime createDate;

    @ApiModelProperty(value = "조회수")
    private final int viewCount;

    @ApiModelProperty(value = "첨부파일")
    private final List<NoticeFileResponse> files;

    public NoticeResponse(NoticeEntity notice) {
        this.notiSeq = notice.getNotiSeq();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createUser = notice.getCreateUser();
        this.createDate = notice.getCreateDate();
        this.viewCount = notice.getViewCount();
        this.files = notice.getNoticeFileEntities().stream()
                .map(NoticeFileResponse::new)
                .collect(Collectors.toList());
    }
}
