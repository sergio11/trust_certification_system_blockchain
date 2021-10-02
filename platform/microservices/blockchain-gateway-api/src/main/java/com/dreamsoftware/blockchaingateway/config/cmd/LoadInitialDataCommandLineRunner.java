package com.dreamsoftware.blockchaingateway.config.cmd;

import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.AuthorityRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class LoadInitialDataCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadInitialDataCommandLineRunner.class);

    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {

        if (authorityRepository.count() == 0) {
            logger.debug("Load initial data for authority repository");

            final List<AuthorityEntity> authorityEntityList = new ArrayList<>();
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_CA)
                    .description("Certification Authority Role")
                    .build());
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_ADMIN)
                    .description("Admin Role")
                    .build());
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_STUDENT)
                    .description("Student Role")
                    .build());
            authorityRepository.saveAll(authorityEntityList);
        }

    }

}
