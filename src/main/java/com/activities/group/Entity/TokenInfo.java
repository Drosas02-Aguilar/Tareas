/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.activities.group.Entity;

import java.time.LocalDateTime;

public class TokenInfo {
    private final Integer idUsuario;
    private final LocalDateTime expiry;

    public TokenInfo(int userId, LocalDateTime expiry) {
        this.idUsuario = userId;
        this.expiry = expiry;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiry);
    }
}