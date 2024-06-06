package com.support.notice.notice.service;

import com.support.notice.domains.NoticeEntity;
import com.support.notice.domains.NoticeFileEntity;
import com.support.notice.domains.repo.NoticeRepository;
import com.support.notice.notice.vo.NoticeResponse;
import com.support.notice.notice.vo.NoticeSaveRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Test
    void list() {
        // Given
        NoticeSaveRequest req1 = new NoticeSaveRequest("title1", "content1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "user1");
        NoticeEntity notice1 = new NoticeEntity(req1);
        notice1.setNotiSeq(1);
        NoticeSaveRequest req2 = new NoticeSaveRequest("title2", "content2", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "user2");
        NoticeEntity notice2 = new NoticeEntity(req2);
        notice2.setNotiSeq(2);

        when(noticeRepository.findAll()).thenReturn(Arrays.asList(notice1, notice2));

        // When
        List<NoticeResponse> result = noticeService.list();

        // Then
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getNotiSeq());
        assertEquals(2, result.get(1).getNotiSeq());

        verify(noticeRepository, times(1)).findAll();
    }

    @Test
    void detail() {
        //given
        NoticeSaveRequest req = new NoticeSaveRequest("title1", "content1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "user1");
        NoticeEntity notice = new NoticeEntity(req);
        long notiSeq = notice.getNotiSeq();

        //when
        when(noticeRepository.findById(notiSeq)).thenReturn(Optional.of(notice));

        NoticeResponse result = noticeService.detail(notiSeq);

        assertEquals(notice.getNotiSeq(), result.getNotiSeq());
        verify(noticeRepository, times(1)).findById(notiSeq);
    }

    @Test
    void save() {
        // given
        String path = System.getProperty("user.dir");

        NoticeSaveRequest request = new NoticeSaveRequest("Title", "Content", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "User");
        MultipartFile mockFile = new MockMultipartFile(path + "\\files\\test.txt", "test.txt",
                "text/plain", "Test file content".getBytes());

        // when
        long savedNoticeId = noticeService.save(request, Arrays.asList(mockFile));

        // then
        assertNotNull(savedNoticeId);
        verify(noticeRepository, times(1)).save(any(NoticeEntity.class));
    }

    @Test
    void update() {
        //given
        String title = "title";
        String content = "content";
        NoticeSaveRequest req = new NoticeSaveRequest(title, content, LocalDateTime.now(), LocalDateTime.now().plusDays(1), "user");
        NoticeEntity notice = new NoticeEntity(req);
        long notiSeq = notice.getNotiSeq();

        //when
        when(noticeRepository.findById(notiSeq)).thenReturn(Optional.of(notice));

        String updateTitle = "Updated Title";
        String updateContent = "Updated Content";
        long result = noticeService.update(notiSeq, updateTitle, updateContent);

        assertEquals(notiSeq, result);
        verify(noticeRepository, times(1)).findById(notiSeq);
    }


    @Test
    void delete() {
        // Given
        NoticeFileEntity file1 = NoticeFileEntity.builder()
                .saveFileName("file1.txt")
                .build();
        NoticeFileEntity file2 = NoticeFileEntity.builder()
                .saveFileName("file2.txt")
                .build();

        List<NoticeFileEntity> files = Arrays.asList(file1, file2);

        NoticeSaveRequest request = new NoticeSaveRequest("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), "User");
        NoticeEntity noticeEntity = new NoticeEntity(request);

        //공지사항에 첨부파일 맵핑
        files.forEach(file -> {
            noticeEntity.addFile(file);
        });

        long notiSeq = noticeEntity.getNotiSeq();

        when(noticeRepository.findById(notiSeq)).thenReturn(Optional.of(noticeEntity));

        // When
        noticeService.delete(notiSeq);

        // Then
        verify(noticeRepository).findById(notiSeq);
        verify(noticeRepository).deleteById(notiSeq);
    }
}