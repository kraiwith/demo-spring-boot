package com.krai.demo_spring_boot.services;

import com.krai.demo_spring_boot.configs.MediaStorageProperties;
import com.krai.demo_spring_boot.dtos.ProductMediaResponseDto;
import com.krai.demo_spring_boot.dtos.ProductMediaUpdateRequestDto;
import com.krai.demo_spring_boot.enums.MediaTypeEnum;
import com.krai.demo_spring_boot.models.MediaModel;
import com.krai.demo_spring_boot.models.ProductModel;
import com.krai.demo_spring_boot.repository.MediaRepository;
import com.krai.demo_spring_boot.repository.ProductRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

@Service
public class MediaService {
  private static final Set<String> IMAGE_TYPES =
      Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
  private static final Set<String> VIDEO_TYPES = Set.of("video/mp4", "video/webm", "video/quicktime");
  private static final Set<String> IMAGE_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".webp", ".gif");
  private static final Set<String> VIDEO_EXTENSIONS = Set.of(".mp4", ".webm", ".mov");

  private final MediaRepository mediaRepository;
  private final ProductRepository productRepository;
  private final MediaStorageProperties properties;

  public MediaService(
      MediaRepository mediaRepository,
      ProductRepository productRepository,
      MediaStorageProperties properties) {
    this.mediaRepository = mediaRepository;
    this.productRepository = productRepository;
    this.properties = properties;
  }

  @Transactional
  public List<ProductMediaResponseDto> uploadProductMedia(Long productId, List<MultipartFile> files) {
    if (files == null || files.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one media file is required");
    }

    ProductModel product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

    return files.stream().map(file -> uploadOne(product, file)).toList();
  }

  @Transactional(readOnly = true)
  public List<ProductMediaResponseDto> getProductMedia(Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    return mediaRepository.findByProductModelIdOrderBySortOrderAscIdAsc(productId).stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  public ProductMediaResponseDto updateMedia(Long mediaId, ProductMediaUpdateRequestDto request) {
    MediaModel media =
        mediaRepository
            .findById(mediaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

    if (request.getAltText() != null) {
      media.setAltText(request.getAltText());
    }
    if (request.getSortOrder() != null) {
      media.setSortOrder(request.getSortOrder());
    }

    return toResponse(mediaRepository.save(media));
  }

  @Transactional
  public void deleteMedia(Long mediaId) {
    MediaModel media =
        mediaRepository
            .findById(mediaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

    mediaRepository.delete(media);
    deleteStoredFile(media.getStorageKey());
  }

  private ProductMediaResponseDto uploadOne(ProductModel product, MultipartFile file) {
    validateFile(file);

    String originalFileName = sanitizeFileName(file.getOriginalFilename());
    String contentType = normalizeContentType(file.getContentType());
    MediaTypeEnum mediaType = resolveType(contentType);
    String extension = getExtension(originalFileName);
    String storedFileName = UUID.randomUUID() + extension;
    String storageKey = "products/" + product.getId() + "/" + storedFileName;
    Path targetPath = resolveUploadPath(storageKey);

    try {
      Files.createDirectories(targetPath.getParent());
      Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not store media file");
    }

    MediaModel media = new MediaModel();
    media.setProduct(product);
    media.setUrl(buildPublicUrl(storageKey));
    media.setStorageKey(storageKey);
    media.setOriginalFileName(originalFileName);
    media.setContentType(contentType);
    media.setSize(file.getSize());
    media.setType(mediaType);

    return toResponse(mediaRepository.save(media));
  }

  private void validateFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media file cannot be empty");
    }

    String contentType = normalizeContentType(file.getContentType());
    MediaTypeEnum type = resolveType(contentType);
    String extension = getExtension(sanitizeFileName(file.getOriginalFilename()));

    if (!isAllowedExtension(type, extension)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported media file extension");
    }

    if (type == MediaTypeEnum.VIDEO && file.getSize() > properties.getMaxVideoSizeBytes()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Video file is too large");
    }
    if ((type == MediaTypeEnum.IMAGE || type == MediaTypeEnum.GIF)
        && file.getSize() > properties.getMaxImageSizeBytes()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image file is too large");
    }
  }

  private MediaTypeEnum resolveType(String contentType) {
    if ("image/gif".equals(contentType)) {
      return MediaTypeEnum.GIF;
    }
    if (IMAGE_TYPES.contains(contentType)) {
      return MediaTypeEnum.IMAGE;
    }
    if (VIDEO_TYPES.contains(contentType)) {
      return MediaTypeEnum.VIDEO;
    }

    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported media type");
  }

  private Path resolveUploadPath(String storageKey) {
    Path uploadRoot = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
    Path target = uploadRoot.resolve(storageKey).normalize();
    if (!target.startsWith(uploadRoot)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid media path");
    }
    return target;
  }

  private boolean isAllowedExtension(MediaTypeEnum type, String extension) {
    if (type == MediaTypeEnum.VIDEO) {
      return VIDEO_EXTENSIONS.contains(extension);
    }
    return IMAGE_EXTENSIONS.contains(extension);
  }

  private void deleteStoredFile(String storageKey) {
    if (storageKey == null || storageKey.isBlank()) {
      return;
    }

    try {
      Files.deleteIfExists(resolveUploadPath(storageKey));
    } catch (IOException exception) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete media file");
    }
  }

  private String buildPublicUrl(String storageKey) {
    String encodedStorageKey =
        UriUtils.encodePath(storageKey.replace("\\", "/"), java.nio.charset.StandardCharsets.UTF_8);
    String publicPath = properties.getPublicPath().replaceAll("/+$", "");
    return publicPath + "/" + encodedStorageKey;
  }

  private String sanitizeFileName(String fileName) {
    if (fileName == null || fileName.isBlank()) {
      return "upload";
    }

    return Paths.get(fileName).getFileName().toString().replaceAll("[^A-Za-z0-9._-]", "_");
  }

  private String getExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
      return "";
    }
    return fileName.substring(dotIndex).toLowerCase(Locale.ROOT);
  }

  private String normalizeContentType(String contentType) {
    return contentType == null ? "" : contentType.toLowerCase(Locale.ROOT);
  }

  private ProductMediaResponseDto toResponse(MediaModel media) {
    return new ProductMediaResponseDto(
        media.getId(),
        media.getUrl(),
        media.getType(),
        media.getOriginalFileName(),
        media.getContentType(),
        media.getSize(),
        media.getAltText(),
        media.getSortOrder(),
        media.getCreatedAt(),
        media.getUpdatedAt());
  }
}
