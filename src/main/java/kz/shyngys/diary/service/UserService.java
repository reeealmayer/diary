package kz.shyngys.diary.service;

import kz.shyngys.diary.model.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

//TODO: покрыть тестами
public interface UserService {

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    User save(User user);


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    User create(User user);

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    User getByUsername(String username);

    /**
     * Получение пользователя по ид
     * @param id пользователя
     * @return пользователь
     */
    User getById(Long id);

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    UserDetailsService userDetailsService();

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    User getCurrentUser();


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    void getAdmin();
}