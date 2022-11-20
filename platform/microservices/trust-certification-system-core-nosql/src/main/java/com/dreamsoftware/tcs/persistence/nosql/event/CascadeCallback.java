package com.dreamsoftware.tcs.persistence.nosql.event;

import java.lang.reflect.Field;

import com.dreamsoftware.tcs.persistence.nosql.annotation.CascadeSave;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Data
@RequiredArgsConstructor
public class CascadeCallback implements ReflectionUtils.FieldCallback {

    private final Object source;
    private final MongoOperations mongoOperations;

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);
        if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
            final Object fieldValue = field.get(getSource());
            if (fieldValue != null) {
                final FieldCallback callback = new FieldCallback();
                ReflectionUtils.doWithFields(fieldValue.getClass(), callback);
                getMongoOperations().save(fieldValue);
            }
        }
    }
}