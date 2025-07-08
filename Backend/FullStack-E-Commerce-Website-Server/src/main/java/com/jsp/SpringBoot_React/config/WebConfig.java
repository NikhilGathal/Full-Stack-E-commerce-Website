package com.jsp.SpringBoot_React.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow cross-origin requests from http://localhost:3000 (React app)
        registry.addMapping("/**")
        .allowedOrigins(
                "http://localhost:5173",  // Local React frontend during development
                "https://shopee-mocha.vercel.app/"  // Deployed React frontend on Vercel
            )
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow specific HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow credentials (e.g., cookies or authorization headers)
    }
	

}
