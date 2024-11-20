package com.adsonsa.useradson.business;

import com.adsonsa.useradson.business.converter.UsuarioConverter;
import com.adsonsa.useradson.business.dto.UsuarioDTO;
import com.adsonsa.useradson.infrastructure.entity.Usuario;
import com.adsonsa.useradson.infrastructure.exceptions.ConflictException;
import com.adsonsa.useradson.infrastructure.exceptions.ResourceNotFoundException;
import com.adsonsa.useradson.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.parausuario(usuarioDTO);
        return usuarioConverter.parausuarioDTO(usuarioRepository.save(usuario));
    }

    // verificacao de email
    public void emailExiste(String email) {
        try {
            boolean existe = verificarEmailExiste(email);
            if (existe) {
                throw new ConflictException("Email ja cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email ja cadastrado", e.getCause());
        }

    }

    // chamar o metodo se o email ja existe
    public boolean verificarEmailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email nao encontrado" + email));
    }
    // deletar usuario pelo email
    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

}
