package com.support.notice.notice.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(description = "공지사항 생성 모델")
@AllArgsConstructor
@ToString
public class NoticeSaveRequest {

    @ApiModelProperty(value = "제목")
    private String title;

    @ApiModelProperty(value = "내용")
    private String content;

    @ApiModelProperty(value = "공지사항 시작일시")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "공지사항 종료일시")
    private LocalDateTime endDate;

    @ApiModelProperty(value = "작성자")
    private String createUser;

}
