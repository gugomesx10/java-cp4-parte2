package br.com.fiap.market.repository;

import br.com.fiap.market.entity.Usuario;
import br.com.fiap.market.enums.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByProviderAndProviderId(
            AuthProvider provider,
            String providerId
    );
    Optional<Usuario> findByUsernameAndProvider(
            String username,
            AuthProvider provider
    );
}
