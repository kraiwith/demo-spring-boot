package com.krai.demo_spring_boot.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.media")
public class MediaStorageProperties {
  private String uploadDir = "uploads";
  private String publicPath = "/media";
  private long maxImageSizeBytes = 10 * 1024 * 1024;
  private long maxVideoSizeBytes = 100 * 1024 * 1024;

  public String getUploadDir() {
    return uploadDir;
  }

  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }

  public String getPublicPath() {
    return publicPath;
  }

  public void setPublicPath(String publicPath) {
    this.publicPath = publicPath;
  }

  public long getMaxImageSizeBytes() {
    return maxImageSizeBytes;
  }

  public void setMaxImageSizeBytes(long maxImageSizeBytes) {
    this.maxImageSizeBytes = maxImageSizeBytes;
  }

  public long getMaxVideoSizeBytes() {
    return maxVideoSizeBytes;
  }

  public void setMaxVideoSizeBytes(long maxVideoSizeBytes) {
    this.maxVideoSizeBytes = maxVideoSizeBytes;
  }
}
