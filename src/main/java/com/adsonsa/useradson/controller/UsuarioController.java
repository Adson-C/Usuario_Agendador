package com.adsonsa.useradson.controller;

import com.adsonsa.useradson.business.UsuarioService;
import com.adsonsa.useradson.business.dto.EnderecoDTO;
import com.adsonsa.useradson.business.dto.TelefoneDTO;
import com.adsonsa.useradson.business.dto.UsuarioDTO;
import com.adsonsa.useradson.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwt;

    // metodo para cadastrar um novo usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }


    // metodo para fazer login
    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwt.generateToken(authentication.getName());
    }

    // metodo para buscar usuario pelo email
    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }
    // deletar usuario pelo email
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarPorEmail(@PathVariable String email){
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }
    // update de usuario
    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                                           @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, dto));
    }
    // metodo para atualizar endereco id
    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,@RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }
    // metodo para atualizar telefone id
    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto, @RequestParam("id") Long id){
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }
}
