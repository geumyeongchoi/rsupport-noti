package com.support.notice.notice.service;

import com.support.notice.domains.NoticeEntity;
import com.support.notice.domains.NoticeFileEntity;
import com.support.notice.domains.repo.NoticeFileRepository;
import com.support.notice.domains.repo.NoticeRepository;
import com.support.notice.notice.vo.NoticeFileDto;
import com.support.notice.notice.vo.NoticeResponse;
import com.support.notice.notice.vo.NoticeSaveRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
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

    @Mock
    private NoticeFileRepository noticeFileRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Value("${file.upload.path:C:/work/notice/files}")
    private String uploadPath;

    @Test
    void list() {
        // Given
        NoticeEntity notice1 = new NoticeEntity();
        notice1.setNotiSeq(6);
        NoticeEntity notice2 = new NoticeEntity();
        notice2.setNotiSeq(1);

        when(noticeRepository.findAll()).thenReturn(Arrays.asList(notice1, notice2));

        // When
        List<NoticeResponse> result = noticeService.list();

        // Then
        assertEquals(2, result.size());
        assertEquals(6, result.get(0).getNotiSeq());
        assertEquals(1, result.get(1).getNotiSeq());

        verify(noticeRepository, times(1)).findAll();
    }

    @Test
    void detail() {
        NoticeEntity notice = new NoticeEntity();
        long notiSeq = 1;
        notice.setNotiSeq(notiSeq);
        when(noticeRepository.findById(notiSeq)).thenReturn(Optional.of(notice));

        NoticeResponse result = noticeService.detail(notiSeq);

        assertEquals(notiSeq, result.getNotiSeq());
        verify(noticeRepository, times(1)).findById(notiSeq);
    }

    @Test
    void save() {
        // given
        NoticeSaveRequest request = new NoticeSaveRequest("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), "User");
        MultipartFile mockFile = new MockMultipartFile("testFile.txt", "testFile.txt",
                "text/plain", "Test file content".getBytes());

        // when
        long savedNoticeId = noticeService.save(request, Arrays.asList(mockFile));

        // then
        assertNotNull(savedNoticeId);
        verify(noticeRepository, times(1)).save(any(NoticeEntity.class));
        verify(noticeFileRepository, times(1)).save(any(NoticeFileEntity.class));
    }

    @Test
    void update() {
        NoticeEntity notice = new NoticeEntity();
        long notiSeq = 1;
        notice.setNotiSeq(notiSeq);
        when(noticeRepository.findById(notiSeq)).thenReturn(Optional.of(notice));

        long result = noticeService.update(notiSeq, "Updated Title", "Updated Content");

        assertEquals(notiSeq, result);
        verify(noticeRepository, times(1)).findById(notiSeq);
    }

    @Test
    void getNoticeFile() throws IllegalAccessException, NoSuchFieldException {
        //given
        long fileSeq = 1;

        NoticeFileEntity fileEntity = NoticeFileEntity.builder()
                .notice(null)
                .orgFileNae("org.txt")
                .saveFileName("save.txt")
                .fileSize(1024L)
                .build();

        Field fileSeqField = NoticeFileEntity.class.getDeclaredField("fileSeq");
        fileSeqField.setAccessible(true);
        fileSeqField.set(fileEntity, fileSeq);

        // when
        NoticeFileDto result = noticeService.getNoticeFile(fileSeq);


        // then
        assertNotNull(result);
        assertEquals(fileSeq, result.getFileSeq());
        assertEquals("org.txt", result.getOrgFileName());
        assertEquals(uploadPath + "/save.txt", result.getFilePath());
        verify(noticeFileRepository, times(1)).findById(fileSeq);
    }


    @Test
    void delete() {
        // Given
        long notiSeq = 1L;

        NoticeFileEntity file1 = NoticeFileEntity.builder()
                .saveFileName("file1.txt")
                .build();
        NoticeFileEntity file2 = NoticeFileEntity.builder()
                .saveFileName("file2.txt")
                .build();

        List<NoticeFileEntity> files = Arrays.asList(file1, file2);

        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeFileEntities(files);

        when(noticeRepository.findById(notiSeq)).thenReturn(Optional.of(noticeEntity));

        File mockFile1 = mock(File.class);
        File mockFile2 = mock(File.class);

        when(mockFile1.exists()).thenReturn(true);
        when(mockFile1.delete()).thenReturn(true);
        when(mockFile2.exists()).thenReturn(false);

        // When
        noticeService.delete(notiSeq);

        // Then
        verify(noticeRepository).findById(notiSeq);
        verify(noticeRepository).deleteById(notiSeq);

        verify(mockFile1).delete();
        verify(mockFile1).exists();
        verify(mockFile2).exists();
    }
}