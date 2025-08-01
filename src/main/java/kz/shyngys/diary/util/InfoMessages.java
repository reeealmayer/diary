package kz.shyngys.diary.util;

import lombok.experimental.UtilityClass;

import static kz.shyngys.diary.util.ApiPaths.RECORDS_API_URL;

@UtilityClass
public class InfoMessages {

    public static final String GET_ALL_RECORDS = "Получение всех записей";
    public static final String GOT_ALL_RECORDS = "Получены все записи: {}";
    public static final String GET_RECORD_BY_ID = "Получение записи по id: {}";
    public static final String GOT_RECORD_BY_ID = "Получена запись с id = {} : {}";
    public static final String CREATE_RECORD = "Создание записи: {}";
    public static final String CREATED_RECORD = "Создана запись: {}";
    public static final String UPDATE_RECORD = "Обновление записи id {}: {}";
    public static final String UPDATED_RECORD = "Обновлена запись: {}";
    public static final String DELETE_RECORD = "Удаление записи id {}";
    public static final String DELETED_RECORD = "Удалена запись: {}";

    public static final String GET_ALL_RECORDS_API = "GET " + RECORDS_API_URL;
    public static final String GOT_ALL_RECORDS_API = "END GET " + RECORDS_API_URL;
    public static final String GET_RECORD_BY_ID_API = "GET " + RECORDS_API_URL + "/{}";
    public static final String GOT_RECORD_BY_ID_API = "END GET " + RECORDS_API_URL + "/{}";
    public static final String POST_CREATE_RECORD_API = "POST " + RECORDS_API_URL + " : {}";
    public static final String POST_CREATED_RECORD_API = "END POST " + RECORDS_API_URL;
    public static final String PUT_BEGIN_RECORD_API = "PUT " + RECORDS_API_URL + "/{} : {}";
    public static final String PUT_END_RECORD_API = "PUT END " + RECORDS_API_URL + "/{}";
    public static final String DELETE_RECORD_API = "DELETE " + RECORDS_API_URL + "/{}";
    public static final String DELETED_RECORD_API = "DELETED" + RECORDS_API_URL + "/{}";
}
