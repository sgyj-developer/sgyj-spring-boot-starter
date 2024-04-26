package com.yeseung.sgyjspringbootstarter.domain;

import lombok.Builder;

@Builder
public record CustomFile(
    String path, // 파일 경로
    String filename, // 파일명
    String originalFilename, // 원본 파일명
    int size, // 파일 사이즈
    String contentType  // 컨텐츠 타입
) {

}
