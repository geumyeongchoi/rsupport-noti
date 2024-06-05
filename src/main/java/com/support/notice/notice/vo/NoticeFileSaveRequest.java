package com.support.notice.notice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ApiModel(description = "공지사항 파일 모델")
@AllArgsConstructor
public class NoticeFileSaveRequest {

    @ApiModelProperty(value = "첨부파일 원본 파일명")
    private String orgName;

    @ApiModelProperty(value = "실제 저장된 파일명")
    private String saveName;

    @ApiModelProperty(value = "파일 사이즈")
    private long fileSize;

    private List<MultipartFile> files;

    
}
