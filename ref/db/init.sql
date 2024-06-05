-- 데이터베이스 생성
create database notice;


-- 공지사항 테이블 생성
CREATE TABLE public.tb_notice (
                                  noti_seq int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                  title varchar NOT NULL,
                                  "content" text NULL,
                                  start_date timestamp NOT NULL,
                                  end_date timestamp NOT NULL,
                                  create_user varchar NOT NULL,
                                  create_date timestamp NOT NULL,
                                  update_date timestamp NOT NULL,
                                  view_count int4 NOT NULL DEFAULT 0,
                                  CONSTRAINT tb_notice_pk PRIMARY KEY (noti_seq)
);

-- Column comments
COMMENT ON COLUMN public.tb_notice.title IS '공지사항 제목';
COMMENT ON COLUMN public.tb_notice."content" IS '공지사항 내용';
COMMENT ON COLUMN public.tb_notice.start_date IS '공지 시작일';
COMMENT ON COLUMN public.tb_notice.end_date IS '공지 종료일시';
COMMENT ON COLUMN public.tb_notice.create_user IS '작성자';
COMMENT ON COLUMN public.tb_notice.create_date IS '공지사항 생성일시';
COMMENT ON COLUMN public.tb_notice.update_date IS '공지사항 수정일시';
COMMENT ON COLUMN public.tb_notice.view_count IS '조회 수';




-- 공지사항 첨부파일 테이블 생성
CREATE TABLE public.tb_notice_file (
                                       file_seq int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                       noti_seq int8 NOT NULL,
                                       org_name varchar NOT NULL,
                                       save_name varchar NOT NULL,
                                       file_size int8 NOT NULL,
                                       create_date timestamp NOT NULL,
                                       CONSTRAINT tb_notice_file_pk PRIMARY KEY (file_seq),
                                       CONSTRAINT tb_notice_file_fk FOREIGN KEY (noti_seq) REFERENCES public.tb_notice(noti_seq)
);
COMMENT ON TABLE public.tb_notice_file IS '공지사항의 첨부파일';

-- Column comments

COMMENT ON COLUMN public.tb_notice_file.file_seq IS '첨부파일 고유ID';
COMMENT ON COLUMN public.tb_notice_file.noti_seq IS '공지사항 고유ID';
COMMENT ON COLUMN public.tb_notice_file.org_name IS '첨부파일 원본 파일명';
COMMENT ON COLUMN public.tb_notice_file.save_name IS '실제 저장된 파일명';
COMMENT ON COLUMN public.tb_notice_file.file_size IS '파일 크기';
COMMENT ON COLUMN public.tb_notice_file.create_date IS '생성일시';


