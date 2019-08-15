package net.limbomedia.esp;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Spring Web MVC configuration.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
  
  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    // Most basic view resolver to handle forward:...
    UrlBasedViewResolver resolver = new UrlBasedViewResolver();
    resolver.setViewClass(InternalResourceView.class);
        registry.viewResolver(resolver);
  }
  
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // font files for caching for internet explorer
      // registry.addResourceHandler("/**/*.eot", "/**/*.woff", "/**/*.ttf", "/**/*.woff2", "/**/*.otf").addResourceLocations("classpath:/web/", "classpath:/webpack/").setCachePeriod(1);
        
        registry.addResourceHandler("/**").addResourceLocations("classpath:/web/", "classpath:/webkit/").setCachePeriod(0);
        // registry.addResourceHandler("/**").addResourceLocations("classpath:/web/").setCachePeriod(0);
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.html");
    }
    
}