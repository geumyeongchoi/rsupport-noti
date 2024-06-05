package com.support.notice.notice.vo;

import com.support.notice.domains.NoticeFileEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@ApiModel(description = "공지사항 첨부파일 조회 모델")
public class NoticeFileResponse {

    @ApiModelProperty(value = "파일 고유 ID")
    private final long fileSeq;

    @ApiModelProperty(value = "기존 파일명")
    private final String orgFileName;

    @ApiModelProperty(value = "생성일시")
    private final LocalDateTime createDate;

    public NoticeFileResponse(NoticeFileEntity file) {
        this.fileSeq = file.getFileSeq();
        this.orgFileName = file.getOrgName();
        this.createDate = file.getCreateDate();
    }
}
