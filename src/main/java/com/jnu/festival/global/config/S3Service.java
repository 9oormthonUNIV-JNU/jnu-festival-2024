package com.jnu.festival.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.region}")
    private String region;

    private final S3Client s3Client;

    public String upload(MultipartFile file, String directoryName) throws IOException {
        String filename = directoryName + "/" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        return s3Client.utilities().getUrl(getUrlRequest).toString();
    }

    public List<String> uploadImages(List<MultipartFile> files, String directoryName) throws IOException{
        if (files == null || files.isEmpty() || files.get(0).isEmpty()) {
            return null;
        }

        List<String> urlList = new ArrayList<>();

        for (MultipartFile image : files) {
            // 이미지 파일을 S3에 저장하고 URL 반환
            String url = upload(image, directoryName);
            // 반환된 URL을 리스트에 추가
            urlList.add(url);
        }

        return urlList;
    }

    public void delete(String url) throws IOException {
        String filename = url.substring(61);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
