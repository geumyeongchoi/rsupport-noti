package com.support.notice.notice.controller;

import com.support.notice.notice.service.NoticeService;
import com.support.notice.notice.vo.NoticeFileDto;
import com.support.notice.notice.vo.NoticeResponse;
import com.support.notice.notice.vo.NoticeSaveRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Api(value = "공지사항 API ")
@Log4j2
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @ApiOperation(value = "공지사항 목록 조회")
    @GetMapping(value = "/notice/list")
    public List<NoticeResponse> list() {
        List<NoticeResponse> rtn = noticeService.list();
        return rtn;
    }

    @ApiOperation(value = "공지사항 상세 조회")
    @GetMapping(value = "/notice/{notiSeq}")
    public NoticeResponse get(@ApiParam(value = "공지사항 고유ID", required = true) @PathVariable long notiSeq)  {
        return noticeService.detail(notiSeq);
    }

    @ApiOperation(value = "공지사항 첨부파일 다운로드")
    @GetMapping(value = "/notice/file-down/{fileSeq}")
    public void get(@ApiParam(value = "파일 고유 ID", required = true) @PathVariable long fileSeq, HttpServletResponse response)  {
        try {
            NoticeFileDto file = noticeService.getNoticeFile(fileSeq);
            if (ObjectUtils.isEmpty(file) == false){
                String fileName = file.getOrgFileName();
                byte[] files = FileUtils.readFileToByteArray(new File(file.getFilePath()));

                response.setContentType("application/octet-stream");
                response.setContentLength(files.length);
                response.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8))+"\";");
                response.setHeader("Content-Transfer-Encoding","binary");

                response.getOutputStream().write(files);
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "공지사항 등록")
    @PostMapping(value = "/notice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long save(@RequestPart NoticeSaveRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        return noticeService.save(request, files);
    }

    @ApiOperation(value = "공지사항 수정")
    @PutMapping(value = "/notice/{notiSeq}")
    public long update(@PathVariable long notiSeq,
                @RequestParam String title,
                @RequestParam String content) {
        return noticeService.update(notiSeq, title, content);
    }


    @ApiOperation(value = "공지사항 삭제")
    @DeleteMapping(value = "/notice/{notiSeq}")
    public void delete(@PathVariable long notiSeq){
        noticeService.delete(notiSeq);
    }

}
