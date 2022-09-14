package com.dreamsoftware.tcs.runners;

import com.dreamsoftware.tcs.config.properties.TranslationsProperties;
import com.dreamsoftware.tcs.services.ITranslationsService;
import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImportDefaultTranslationsRunner implements CommandLineRunner {

    private final ITranslationsService translationsService;
    private final TranslationsProperties translationslProperties;

    @Override
    public void run(String... args) throws Exception {
        log.debug("ImportDefaultTranslationsRunner CALLED!");
        try {
            translationsService.save(getDefaultTranslationFile());
        } catch (final Exception ex) {
            log.error("ImportDefaultTranslationsRunner -> " + ex.getMessage());
        }
    }

    private File getDefaultTranslationFile() {
        final File defaultTranslationFile = new File(translationslProperties.getDefaultFileLocation());
        if (!defaultTranslationFile.exists() || !defaultTranslationFile.canRead()) {
            throw new IllegalStateException("Default Translation File can not be found");
        }
        return defaultTranslationFile;
    }

}
