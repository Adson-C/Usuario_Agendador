package com.adsonsa.useradson.business;

import com.adsonsa.useradson.business.converter.UsuarioConverter;
import com.adsonsa.useradson.business.dto.UsuarioDTO;
import com.adsonsa.useradson.infrastructure.entity.Usuario;
import com.adsonsa.useradson.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.parausuario(usuarioDTO);
        return usuarioConverter.parausuarioDTO(usuarioRepository.save(usuario));
    }

}
