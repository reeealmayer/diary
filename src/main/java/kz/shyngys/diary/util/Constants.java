package kz.shyngys.diary.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String GET_ALL_RECORDS = "Получение всех записей";
    public static final String GOT_ALL_RECORDS = "Получены все записи: {}";

    public static final String RECORDS_API_URL = "/api/v1/records";

    public static final String GET_ALL_RECORDS_API = "GET " + RECORDS_API_URL;
    public static final String GOT_ALL_RECORDS_API = "END GET " + RECORDS_API_URL;
}
