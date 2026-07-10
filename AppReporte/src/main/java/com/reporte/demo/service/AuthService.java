package com.reporte.demo.service;

import com.reporte.demo.dto.AuthResponse;
import com.reporte.demo.dto.LoginDTO;
import com.reporte.demo.dto.RegistroDTO;
import com.reporte.demo.dto.UsuarioDTO;

public interface AuthService {
	UsuarioDTO register(RegistroDTO request);

	AuthResponse login(LoginDTO request);
	
}
