package model.enums;

import exception.ValidacaoException;

import java.util.Scanner;

public enum TipoPet {
    GATO("Gato"),
    CACHORRO("Cachorro");

    private String tipoPet;

    TipoPet(String descricao) {
        this.tipoPet = descricao;
    }

    public String getTipoPet() {
        return tipoPet;
    }

    public static TipoPet validarTipoPet(String texto) {
        if (texto == null || texto.isBlank()){
            throw new ValidacaoException("Campo vazio! Por favor, digite um tipo válido (Gato/Cachorro) >> ");
        }

        for (TipoPet pet : values()) {
            if (pet.tipoPet.equalsIgnoreCase(texto.trim())) {
                return pet;
            }
        }
        throw new ValidacaoException("Tipo inválido! Por favor, digite um tipo válido (Gato/Cachorro) >> ");
    }

}
