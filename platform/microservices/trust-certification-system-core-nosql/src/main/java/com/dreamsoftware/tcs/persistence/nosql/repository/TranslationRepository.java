package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.TranslationEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.TranslationLanguageEnum;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface TranslationRepository extends MongoRepository<TranslationEntity, ObjectId> {

    /**
     * Find By Language Order by Name Asc
     *
     * @param language
     * @return
     */
    Iterable<TranslationEntity> findByLanguageOrderByNameAsc(final TranslationLanguageEnum language);

    /**
     *
     * @param name
     * @param language
     * @return
     */
    Optional<TranslationEntity> findByNameAndLanguage(final String name, final TranslationLanguageEnum language);

    /**
     *
     * @param name
     * @param language
     * @return
     */
    Long countByNameAndLanguage(final String name, final TranslationLanguageEnum language);
}
