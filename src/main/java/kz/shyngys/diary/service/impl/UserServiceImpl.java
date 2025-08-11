package kz.shyngys.diary.service.impl;

import kz.shyngys.diary.exception.UserAlreadyExists;
import kz.shyngys.diary.model.Role;
import kz.shyngys.diary.model.domain.User;
import kz.shyngys.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static kz.shyngys.diary.util.ErrorMessages.USER_NOT_FOUND;
import static kz.shyngys.diary.util.ErrorMessages.USER_WITH_THIS_NAME_IS_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements kz.shyngys.diary.service.UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    @Override
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExists(USER_WITH_THIS_NAME_IS_ALREADY_EXISTS);
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    @Override
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Override
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
