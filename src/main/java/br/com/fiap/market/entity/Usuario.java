package br.com.fiap.market.entity;


import br.com.fiap.market.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "TDS_TB_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER")
    private AuthProvider provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Column(name = "AVATAR_URL")
    private String avatarUrl;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "ROLE", nullable = false)
    private String role;
}
