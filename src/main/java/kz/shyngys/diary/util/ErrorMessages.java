package kz.shyngys.diary.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public static final String RECORD_NOT_FOUND = "Не найдена запись с id %d";
    public static final String USER_HAS_NO_THIS_RECORD = "Пользователь с id %d не имеет прав на эту запись с id %d";
    public static final String USER_WITH_THIS_NAME_IS_ALREADY_EXISTS = "Пользователь с таким именем (%d) уже существует";
    public static final String USER_NOT_FOUND = "Пользователь не найден";
}
