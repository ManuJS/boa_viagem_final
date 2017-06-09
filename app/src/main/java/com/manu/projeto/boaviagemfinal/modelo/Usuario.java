package com.manu.projeto.boaviagemfinal.modelo;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emanu on 08/06/2017.
 */
@IgnoreExtraProperties

public class Usuario {

    String nome;
    String telefone;
    String uid;

    public Usuario(String userId, String nome, String telefone) {
    }

    public Usuario(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;

    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("nome", nome);
        result.put("telefone", telefone);

        return result;
    }
}
