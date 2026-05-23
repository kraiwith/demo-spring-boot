package com.krai.demo_spring_boot.configs;

import com.krai.demo_spring_boot.interceptor.AuthInterceptor;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final @NonNull AuthInterceptor authInterceptor;
  private final MediaStorageProperties mediaStorageProperties;

  public WebConfig(
      @NonNull AuthInterceptor authInterceptor, MediaStorageProperties mediaStorageProperties) {
    this.authInterceptor = authInterceptor;
    this.mediaStorageProperties = mediaStorageProperties;
  }

  @Override
  public void addInterceptors(@NonNull InterceptorRegistry registry) {
    registry
        .addInterceptor(authInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/login",
            "/register",
            "/swagger-ui/**",
            "/swagger-ui-dark-mode.css",
            "/swagger-ui-dark-mode.js",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            mediaStorageProperties.getPublicPath() + "/**");
  }

  @Override
  public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
    Path uploadRoot = Paths.get(mediaStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    String publicPath = mediaStorageProperties.getPublicPath().replaceAll("/+$", "");
    String resourceLocation = uploadRoot.toUri().toString();
    if (!resourceLocation.endsWith("/")) {
      resourceLocation += "/";
    }

    registry
        .addResourceHandler(publicPath + "/**")
        .addResourceLocations(resourceLocation);
  }
}
