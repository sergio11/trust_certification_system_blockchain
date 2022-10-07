package com.dreamsoftware.tcs.runners;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AuthorityEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthorityRepository;
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
public class LoadInitialDataRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadInitialDataRunner.class);

    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {

        if (authorityRepository.count() == 0) {
            logger.debug("Load initial data for authority repository");

            final List<AuthorityEntity> authorityEntityList = new ArrayList<>();
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_CA_MEMBER)
                    .description("Certification Authority Member Role")
                    .build());
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_ADMIN)
                    .description("Admin Role")
                    .build());
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_STUDENT)
                    .description("Student Role")
                    .build());
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_CA_ADMIN)
                    .description("Certification Authority Admin Role")
                    .build());
            authorityEntityList.add(AuthorityEntity.builder()
                    .type(AuthorityEnum.ROLE_CHECKER)
                    .description("Checker Role")
                    .build());
            authorityRepository.saveAll(authorityEntityList);
        }

    }

}
