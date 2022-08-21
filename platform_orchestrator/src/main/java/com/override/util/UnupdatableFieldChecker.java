package com.override.util;

import com.override.annotation.Unupdatable;
import com.override.exception.UnupdatableDataException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class UnupdatableFieldChecker<T> {

    public void executeCheckOfFieldChanges(T currentValue, T newValue) throws UnupdatableDataException {

        try {
            for(Field field : currentValue.getClass().getDeclaredFields()) {

                if (field.isAnnotationPresent(Unupdatable.class)) {

                    field.setAccessible(true);

                    if((field.get(currentValue) != null) &&
                            (field.get(currentValue) != field.get(newValue))) {
                        throw new UnupdatableDataException("Attempt to change data in the locked field");
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    //The method checks that data is written to fields that were previously empty
    public boolean executeCheckOfFillingInField(T currentValue, T newValue) {

        try {

            for(Field field: currentValue.getClass().getDeclaredFields()) {

                field.setAccessible(true);

                if (field.isAnnotationPresent(Unupdatable.class) &&
                        field.get(currentValue) == null && field.get(newValue) != null) {
                    return true;
                }

            }

        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return false;

    }
}
