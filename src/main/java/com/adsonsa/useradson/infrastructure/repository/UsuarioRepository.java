package com.adsonsa.useradson.infrastructure.repository;


import com.adsonsa.useradson.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // verificar se o email ja existe
    boolean existsByEmail(String email);

    // buscar usuario pelo email
    Optional<Usuario> findByEmail(String email);

    // deletar usuario pelo email
    @Transactional
    void deleteByEmail(String email);
}
