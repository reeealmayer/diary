package kz.shyngys.diary.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public static final String RECORD_NOT_FOUND = "Не найдена запись с id %d";
    public static final String USER_HAS_NO_THIS_RECORD = "Пользователь с id %d не имеет прав на эту запись с id %d";
}
