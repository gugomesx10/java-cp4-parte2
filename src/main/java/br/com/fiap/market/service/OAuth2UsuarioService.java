package br.com.fiap.market.service;


import br.com.fiap.market.entity.Usuario;
import br.com.fiap.market.enums.AuthProvider;
import br.com.fiap.market.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuth2UsuarioService extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oauthUser = super.loadUser(userRequest);
        String nome = oauthUser.getAttribute("name");
        String login = oauthUser.getAttribute("login");
        String avatarUrl = oauthUser.getAttribute("avatar_url");

        String providerId = oauthUser
                .getAttribute("id")
                .toString();

        AuthProvider provider = AuthProvider.GITHUB;

        Optional<Usuario> usuarioExistente =
                usuarioRepository.findByProviderAndProviderId(
                        provider, providerId
                );

        Usuario usuarioBanco;

        if (usuarioExistente.isEmpty()) {

            Usuario usuario = new Usuario();

            usuario.setProvider(provider);
            usuario.setProviderId(providerId);
            usuario.setNome(nome != null ? nome : login);
            usuario.setUsername("github_" + providerId);
            usuario.setAvatarUrl(avatarUrl);
            usuario.setRole("USER");

            usuarioBanco = usuarioRepository.save(usuario);
        } else {
            usuarioBanco = usuarioExistente.get();
        }

        Set<GrantedAuthority> authorities =
                new HashSet<>(oauthUser.getAuthorities());

        authorities.add(
                new SimpleGrantedAuthority("ROLE_" + usuarioBanco.getRole())
        );

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(
                authorities,
                oauthUser.getAttributes(),
                userNameAttributeName
        );
    }
}
