package com.krai.demo_spring_boot.configs;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

@Configuration
public class SwaggerUiConfig {

  @Bean
  public SwaggerIndexTransformer swaggerIndexTransformer(
      SwaggerUiConfigProperties swaggerUiConfig,
      SwaggerUiOAuthProperties swaggerUiOAuthProperties,
      SwaggerWelcomeCommon swaggerWelcomeCommon,
      ObjectMapperProvider objectMapperProvider) {
    return new DarkModeSwaggerIndexTransformer(
        swaggerUiConfig, swaggerUiOAuthProperties, swaggerWelcomeCommon, objectMapperProvider);
  }

  private static class DarkModeSwaggerIndexTransformer extends SwaggerIndexPageTransformer {
    private static final String DARK_MODE_STYLESHEET =
        "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./swagger-ui-dark-mode.css\" />\n";
    private static final String DARK_MODE_SCRIPT =
        "    <script src=\"./swagger-ui-dark-mode.js\" charset=\"UTF-8\"></script>\n";

    DarkModeSwaggerIndexTransformer(
        SwaggerUiConfigProperties swaggerUiConfig,
        SwaggerUiOAuthProperties swaggerUiOAuthProperties,
        SwaggerWelcomeCommon swaggerWelcomeCommon,
        ObjectMapperProvider objectMapperProvider) {
      super(swaggerUiConfig, swaggerUiOAuthProperties, swaggerWelcomeCommon, objectMapperProvider);
    }

    @Override
    public Resource transform(
        HttpServletRequest request, Resource resource, ResourceTransformerChain transformerChain)
        throws IOException {
      Resource transformed = super.transform(request, resource, transformerChain);
      if (!"index.html".equals(resource.getFilename())) {
        return transformed;
      }

      String html = new String(transformed.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
      html = html.replace("  </head>", DARK_MODE_STYLESHEET + "  </head>");
      html = html.replace("  </body>", DARK_MODE_SCRIPT + "  </body>");
      return new TransformedResource(resource, html.getBytes(StandardCharsets.UTF_8));
    }
  }
}
