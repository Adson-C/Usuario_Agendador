package com.adsonsa.useradson.business;

import com.adsonsa.useradson.business.converter.UsuarioConverter;
import com.adsonsa.useradson.business.dto.EnderecoDTO;
import com.adsonsa.useradson.business.dto.TelefoneDTO;
import com.adsonsa.useradson.business.dto.UsuarioDTO;
import com.adsonsa.useradson.infrastructure.entity.Endereco;
import com.adsonsa.useradson.infrastructure.entity.Telefone;
import com.adsonsa.useradson.infrastructure.entity.Usuario;
import com.adsonsa.useradson.infrastructure.exceptions.ConflictException;
import com.adsonsa.useradson.infrastructure.exceptions.ResourceNotFoundException;
import com.adsonsa.useradson.infrastructure.repository.EnderecoRepository;
import com.adsonsa.useradson.infrastructure.repository.TelefoneRepository;
import com.adsonsa.useradson.infrastructure.repository.UsuarioRepository;
import com.adsonsa.useradson.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil  jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
        return usuarioConverter.parausuarioDTO(
                usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email nao encontrado" + email)
               )
        );
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email nao encontrado" + email, e.getCause());
        }
    }
    // deletar usuario pelo email
    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
    public UsuarioDTO atualizaDadosUsuario(String token ,UsuarioDTO dto){
        // Extrair email do token
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        // metodo para criptar senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email nao encontrado" + email));
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

        return usuarioConverter.parausuarioDTO(usuarioRepository.save(usuario));
    }
    public EnderecoDTO atualizaEndereco(Long id_endereco, EnderecoDTO dto){
        Endereco entity = enderecoRepository.findById(id_endereco).orElseThrow(() ->
                new ResourceNotFoundException("id nao encontrado" + id_endereco));

        Endereco endereco = usuarioConverter.updateEndereco(dto, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }
    public TelefoneDTO atualizaTelefone(Long id_telefone, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(id_telefone).orElseThrow(() ->
                new ResourceNotFoundException("id nao encontrado" + id_telefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
