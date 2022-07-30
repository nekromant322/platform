package com.override.util;

import com.override.annotation.Unupdatable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CheckerUnupdatableField<T> {

    public boolean unupdatableFieldIsChanged(T currentValue, T newValue) {

        try {
            for(Field field : currentValue.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Unupdatable.class)) {

                    field.setAccessible(true);
                    if( (field.get(currentValue) != null) &&
                            (field.get(currentValue) != field.get(newValue))) {
                        return true;
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
