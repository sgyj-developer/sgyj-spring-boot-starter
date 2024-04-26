package com.yeseung.sgyjspringbootstarter.util;

import com.yeseung.sgyjspringbootstarter.advice.exceptions.BadRequestException;
import com.yeseung.sgyjspringbootstarter.domain.CustomFile;
import com.yeseung.sgyjspringbootstarter.properties.BasePathProperties;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author seungglee
 * @apiNote 파일 처리 util
 * @apiNote 파일에관련된 처리(I/O)를 마치고 해당 파일에 대한 정보를
 * @apiNote fileVO 리스트 정보를 리턴해준다.
 */
@Component
@RequiredArgsConstructor
public class FileUtil {

    private final BasePathProperties basePathProperties;

    public List<CustomFile> makeCustomFile(MultipartHttpServletRequest request) {
        return makeCustomFile(request, "");
    }

    /**
     * @param multipartHttpServletRequest 파일 요청 객체
     * @return List<CustomFile>
     */
    public List<CustomFile> makeCustomFile(MultipartHttpServletRequest multipartHttpServletRequest, String filePath) {
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
        List<CustomFile> comFiles = new ArrayList<>();                                // 여러개 파일일 때를 대비한 list
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();

            List<MultipartFile> fileList = multipartHttpServletRequest.getFiles(fileName);
            for (MultipartFile mFile : fileList) {
                comFiles.add(makeCustomFileOne(mFile, filePath));
            }
        }
        return comFiles;
    }

    public CustomFile makeCustomFileOne(MultipartFile multipartFile, String filePath) {
        String sourceFileName = multipartFile.getOriginalFilename();                // 실제 파일 이름
        assert sourceFileName != null;
        String sourceFileNameExtension = getExtension(sourceFileName).toLowerCase();
        String destinationFileName = UUID.randomUUID() + "." + sourceFileNameExtension;

        filePath = basePathProperties.getUploadDir() + filePath;
        File file = new File(filePath + System.lineSeparator() + destinationFileName);
        createFile(multipartFile, file);
        return CustomFile.builder()
            .filename(destinationFileName)
            .size((int)multipartFile.getSize())
            .originalFilename(sourceFileName)
            .contentType(multipartFile.getContentType())
            .path(filePath)
            .build();
    }

    private String getExtension(String sourceFileName) {
        if (sourceFileName.contains(".")) {
            return sourceFileName.substring(sourceFileName.lastIndexOf("."));
        }
        return "";
    }

    private void createFile(MultipartFile mFile, File file) {
        if (mFile.getSize() != 0 && !file.exists()
            && file.getParentFile().mkdirs()) {                                          // file null check
            // 해당 경로가 존재하는지 판단 후 경로에 해당하는 디렉토리들을 생성
            try {
                if (file.createNewFile()) {
                    throw new BadRequestException("파일 생성에 실패했습니다.");
                }
            } catch (IOException e) {
                throw new BadRequestException("파일 생성 중 오류가 발생했습니다. " + e.getMessage());
            }

        }
        try {
            mFile.transferTo(file);  // 실제 경로에 multipartFile 을 전송
        } catch (IllegalStateException | IOException e) {
            throw new BadRequestException("파일을 실제 경로에 전송하는 중 오류가 발생했습니다. " + e.getMessage());
        }
    }

    public boolean deleteFile(String fileNm) {
        return deleteFile(fileNm, "");
    }

    /**
     * 실제 파일 삭제
     *
     * @param filename 파일 이름
     * @param filePath 파일 경로
     * @return 파일 삭제 여부
     */
    public boolean deleteFile(String filename, String filePath) {
        String savePath = basePathProperties.getUploadDir();

        if (StringUtils.isNotEmpty(filePath)) {
            savePath += filePath;
        }
        Path path = FileSystems.getDefault().getPath(savePath, filename);

        try {
            Files.delete(path);
        } catch (IOException | SecurityException e) {
            return false;
        }
        return true;
    }

    /**
     * 파일 다운로드
     *
     * @param customFile : 파일 객체
     * @return ResponseEntity<Resource>
     */
    public ResponseEntity<Resource> download(CustomFile customFile) throws IOException {
        String fileNm = customFile.path() + "/" + customFile.filename();
        File file = new File(fileNm);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" +
                        new String(customFile.originalFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"")
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType(customFile.contentType()))
            .body(resource);
    }

}
