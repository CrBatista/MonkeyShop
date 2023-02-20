package com.monkeyshop.customer.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
public class S3Service {

  @Value("${aws.s3.bucketName}")
  public String bucketName;

  public Integer FILE_URL_EXPIRATION_MINUTES = 5;

  private final AmazonS3 amazonS3;

  @Autowired
  public S3Service(AmazonS3 amazonS3) {
    this.amazonS3 = amazonS3;
  }

  public String uploadFile(MultipartFile file) throws IOException {
    String key = file.getOriginalFilename();
    PutObjectRequest request = new PutObjectRequest(bucketName, key, file.getInputStream(), null);
    amazonS3.putObject(request);
    return key;
  }

  public String generatePresignedURL(String key) {
    Date expiration = new Date(System.currentTimeMillis() + (FILE_URL_EXPIRATION_MINUTES * 60 * 1000));
    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucketName, key)
            .withMethod(HttpMethod.GET)
            .withExpiration(expiration);
    return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
  }

  public void deleteFile(String key) {
    amazonS3.deleteObject(bucketName, key);
  }
}
