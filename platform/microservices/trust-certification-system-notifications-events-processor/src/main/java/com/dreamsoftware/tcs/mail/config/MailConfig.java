package com.dreamsoftware.tcs.mail.config;

import com.dreamsoftware.tcs.mail.config.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
public class MailConfig {

    private final MailProperties mailProperties;

    /**
     * Provide Mail Template Resolver
     *
     * @return
     */
    @Bean
    public ITemplateResolver provideMailTemplateResolver() {
        FileTemplateResolver fileTemplateResolver = new FileTemplateResolver();
        fileTemplateResolver.setPrefix(mailProperties.getMailTemplatesBaseDir());
        fileTemplateResolver.setSuffix(".html");
        fileTemplateResolver.setTemplateMode(TemplateMode.HTML);
        fileTemplateResolver.setCharacterEncoding("UTF-8");
        fileTemplateResolver.setOrder(1);
        fileTemplateResolver.setCheckExistence(true);
        return fileTemplateResolver;
    }

}
