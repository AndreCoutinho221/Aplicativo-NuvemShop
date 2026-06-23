package com.andre.nuvemapp.entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_interno;

    private Long loja_id;

    @Column(nullable = false, length = 255)
    private String accessToken;

    private String scope;

    private LocalDateTime instaladoEm;
    private LocalDateTime atualizadoEm;


    public Loja(){}

    public Long getLoja_id() {
        return loja_id;
    }

    public void setLoja_id(Long loja_id) {
        this.loja_id = loja_id;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LocalDateTime getInstaladoEm() {
        return instaladoEm;
    }

    public void setInstaladoEm(LocalDateTime instaladoEm) {
        this.instaladoEm = instaladoEm;
    }

    public LocalDateTime getUpdatedAt(LocalDateTime now) {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getId_interno() {
        return id_interno;
    }

    public void setId_interno(Long id_interno) {
        this.id_interno = id_interno;
    }
}
