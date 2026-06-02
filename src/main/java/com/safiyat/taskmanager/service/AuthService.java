package com.safiyat.taskmanager.service;

import com.safiyat.taskmanager.dto.AuthResponseDTO;
import com.safiyat.taskmanager.dto.LoginRequestDTO;
import com.safiyat.taskmanager.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}