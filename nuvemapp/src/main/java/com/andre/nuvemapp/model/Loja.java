package com.andre.nuvemapp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Loja {

    @Id
    private Long loja_id;

    @Column(nullable = false, length = 255)
    private String acessToken;

    private String scope;

    private LocalDateTime installedAt;
    private LocalDateTime updatedAt;

    public Loja(){}

    public Long getLoja_id() {
        return loja_id;
    }

    public void setLoja_id(Long loja_id) {
        this.loja_id = loja_id;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LocalDateTime getInstalledAt() {
        return installedAt;
    }

    public void setInstalledAt(LocalDateTime installedAt) {
        this.installedAt = installedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
