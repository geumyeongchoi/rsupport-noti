package com.support.notice.notice.service;

import com.support.notice.domains.NoticeEntity;
import com.support.notice.domains.NoticeFileEntity;
import com.support.notice.domains.repo.NoticeFileRepository;
import com.support.notice.domains.repo.NoticeRepository;
import com.support.notice.notice.vo.NoticeFileDto;
import com.support.notice.notice.vo.NoticeResponse;
import com.support.notice.notice.vo.NoticeSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Log4j2
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;

    @Value("${file.upload.path:C:/work/notice/files}")
    private String uploadPath;

    /**
     * 공지사항 전체 목록 조회
     * @return 공지사항 목록
     */
    public List<NoticeResponse> list() {
        return noticeRepository.findAll().stream()
                .map(NoticeResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 공지사항 상세 조회
     * @param notiSeq
     * @return
     */
    public NoticeResponse detail(long notiSeq) {
        NoticeEntity notice = noticeRepository.findById(notiSeq).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항 입니다. notiSeq = " + notiSeq));
        notice.addViewCount();
        return new NoticeResponse(notice);
    }

    /**
     * 공지사항 등록
     * @param request
     * @return
     */
    public long save(NoticeSaveRequest request, List<MultipartFile> files) {
        //공지사항 추가
        NoticeEntity notice = new NoticeEntity(request);
        files.forEach( file -> {
            //저장 파일명 생성
            try {
                String saveFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File saveFile = new File(uploadPath, saveFileName);

                file.transferTo(saveFile);

                System.out.println(file.getName());
                System.out.println(file.getOriginalFilename());

                NoticeFileEntity fileEntity = NoticeFileEntity.builder()
                            .notice(notice)
                            .orgFileNae(file.getOriginalFilename())
                            .saveFileName(saveFileName)
                            .fileSize(file.getSize())
                            .build();

                    notice.addFile(fileEntity);

            } catch (IOException e) {
                log.error("파일 등록에 실패하였습니다. fileName = " + file.getName());
            }
        });
        noticeRepository.save(notice);
        return notice.getNotiSeq();
    }

    /**
     * 공지사항 수정
     * @param notiSeq
     * @param title
     * @param content
     * @return
     */
    public long update(long notiSeq, String title, String content) {
        NoticeEntity notice = noticeRepository.findById(notiSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항 입니다. notiSeq = " + notiSeq));
        notice.update(title, content);
        return notice.getNotiSeq();
    }

    /**
     * 공지사항 파일 정보 조회
     * @param fileSeq
     * @return
     */
    public NoticeFileDto getNoticeFile(long fileSeq) {
        NoticeFileEntity file = noticeFileRepository.findById(fileSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파일 입니다. fileSeq = " + fileSeq));

        return new NoticeFileDto(file, uploadPath);
    }

    /**
     * 공지사항 삭제
     * @param notiSeq
     */
    public void delete(long notiSeq) {
        //공지사항의 첨부파일 삭제
        List<NoticeFileEntity> files = noticeRepository.findById(notiSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지사항 입니다. notiSeq = " + notiSeq))
                .getNoticeFileEntities();

        // storage의 첨부파일 삭제
        files.forEach(notiFile -> {
                    File file = new File(uploadPath + "/" + notiFile.getSaveName());
                    if (file.exists()) {
                        file.delete();
                        log.debug("{} 파일이 삭제 되었습니다.", notiFile.getSaveName());
                    } else {
                        log.debug("{} 파일이 존재하지 않습니다.", notiFile.getSaveName());
                    }
            });

        noticeRepository.deleteById(notiSeq);
    }
}
