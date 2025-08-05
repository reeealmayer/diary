package kz.shyngys.diary.service;

import kz.shyngys.diary.dto.JwtAuthenticationResponseDto;
import kz.shyngys.diary.dto.SignInRequestDto;
import kz.shyngys.diary.dto.SignUpRequestDto;

public interface AuthenticationService {
    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    JwtAuthenticationResponseDto signUp(SignUpRequestDto request);


    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    JwtAuthenticationResponseDto signIn(SignInRequestDto request);
}
