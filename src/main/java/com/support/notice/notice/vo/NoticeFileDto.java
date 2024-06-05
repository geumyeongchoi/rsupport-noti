package com.support.notice.notice.vo;

import com.support.notice.domains.NoticeFileEntity;
import lombok.Getter;

@Getter
public class NoticeFileDto {
    private final long fileSeq;

    private final String orgFileName;

    private final String filePath;

    public NoticeFileDto(NoticeFileEntity file, String uploadPath) {
        this.fileSeq = file.getFileSeq();
        this.orgFileName = file.getOrgName();
        this.filePath = uploadPath + "/" + file.getSaveName();
    }
}
