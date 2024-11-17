package com.adsonsa.useradson.business.converter;

import com.adsonsa.useradson.business.dto.EnderecoDTO;
import com.adsonsa.useradson.business.dto.TelefoneDTO;
import com.adsonsa.useradson.business.dto.UsuarioDTO;
import com.adsonsa.useradson.infrastructure.entity.Endereco;
import com.adsonsa.useradson.infrastructure.entity.Telefone;
import com.adsonsa.useradson.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
    // converter DTO para Entidade Usuario
    public Usuario parausuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }
    // converter DTO para Entidade Telefone
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefonesDTOS) {
        return telefonesDTOS.stream().map(this::paraTelefone).toList();
    }
    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd()).build();
    }


    // converter DTO para Entidade Endereco
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecosDTOS) {
    // stream em vez de usar forEach
        return enderecosDTOS.stream().map(this::paraEndereco).toList();
    }
    public Endereco paraEndereco(EnderecoDTO enderecosDTO) {
        return Endereco.builder().
                rua(enderecosDTO.getRua())
                .numero(enderecosDTO.getNumero())
                .complemento(enderecosDTO.getComplemento())
                .cidade(enderecosDTO.getCidade())
                .estado(enderecosDTO.getEstado())
                .cep(enderecosDTO.getCep())
                .build();
    }
    public UsuarioDTO parausuarioDTO(Usuario usuarioDTO) {
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuarioDTO.getTelefones()))
                .build();
    }
    // converter DTO para Entidade Telefone
    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefonesDTOS) {
        return telefonesDTOS.stream().map(this::paraTelefoneDTO).toList();
    }
    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO) {
        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd()).build();
    }


    // converter DTO para Entidade Endereco
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecosDTOS) {
        // stream em vez de usar forEach
        return enderecosDTOS.stream().map(this::paraEnderecoDTO).toList();
    }
    public EnderecoDTO paraEnderecoDTO(Endereco enderecosDTO) {
        return EnderecoDTO.builder().
                rua(enderecosDTO.getRua())
                .numero(enderecosDTO.getNumero())
                .complemento(enderecosDTO.getComplemento())
                .cidade(enderecosDTO.getCidade())
                .estado(enderecosDTO.getEstado())
                .cep(enderecosDTO.getCep())
                .build();
    }
}
