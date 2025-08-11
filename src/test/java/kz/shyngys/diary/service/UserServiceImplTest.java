package kz.shyngys.diary.service;

import kz.shyngys.diary.exception.UserAlreadyExists;
import kz.shyngys.diary.model.Role;
import kz.shyngys.diary.model.domain.User;
import kz.shyngys.diary.repository.UserRepository;
import kz.shyngys.diary.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl service;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole(Role.ROLE_USER);
    }

    @Test
    void save_ShouldReturnSavedUser() {
        when(repository.save(user)).thenReturn(user);

        User saved = service.save(user);

        assertEquals(user, saved);
        verify(repository, times(1)).save(user);
    }

    @Test
    void create_ShouldCreateUser_WhenUsernameNotExists() {
        when(repository.existsByUsername("testuser")).thenReturn(false);
        when(repository.save(user)).thenReturn(user);

        User created = service.create(user);

        assertEquals(user, created);
        verify(repository, times(1)).save(user);
    }

    @Test
    void create_ShouldThrow_WhenUsernameExists() {
        when(repository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(UserAlreadyExists.class, () -> service.create(user));
        verify(repository, never()).save(any());
    }

    @Test
    void getByUsername_ShouldReturnUser_WhenExists() {
        when(repository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User found = service.getByUsername("testuser");

        assertEquals(user, found);
    }

    @Test
    void getByUsername_ShouldThrow_WhenNotExists() {
        when(repository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.getByUsername("testuser"));
    }

    @Test
    void getById_ShouldReturnUser_WhenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        User found = service.getById(1L);

        assertEquals(user, found);
    }

    @Test
    void getById_ShouldThrow_WhenNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void getCurrentUser_ShouldReturnAuthenticatedUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);

        when(repository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User currentUser = service.getCurrentUser();

        assertEquals(user, currentUser);
    }

    @Test
    void getAdmin_ShouldChangeRoleToAdmin() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);
        when(repository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);

        service.getAdmin();

        assertEquals(Role.ROLE_ADMIN, user.getRole());
        verify(repository, times(1)).save(user);
    }
}