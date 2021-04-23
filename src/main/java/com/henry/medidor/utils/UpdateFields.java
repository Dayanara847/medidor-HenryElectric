package com.henry.medidor.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

public class UpdateFields {

    public static Object updateObject(Object objectNew, Object objectOld) {
        try {
            Field[] fields = objectNew.getClass().getDeclaredFields();

            for (Field field : fields) {
                Field fieldsObject = objectNew.getClass().getDeclaredField(field.getName());
                fieldsObject.setAccessible(true);
                if (fieldsObject.get(objectNew) == null) {
                    fieldsObject.set(objectNew, fieldsObject.get(objectOld));
                }
            }
            return objectNew;
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
