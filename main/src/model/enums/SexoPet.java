package model.enums;

import exception.ValidacaoException;

import java.util.Scanner;

public enum SexoPet {
    FEMEA("Femea"),
    MACHO("Macho");

    private String sexoPet;

    SexoPet(String sexoPet) {
        this.sexoPet = sexoPet;
    }

    public String getSexoPet() {
        return sexoPet;
    }

    public static SexoPet validarSexoPet(String texto) {
        if (texto == null || texto.isBlank()){
            throw new ValidacaoException("Campo vazio! Por favor, digite um tipo válido (Gato/Cachorro) ou deixe em branco >> ");
        }

        for (SexoPet s : values()) {
            if (s.sexoPet.equalsIgnoreCase(texto.trim())) {
                return s;
            }
        }
        throw new ValidacaoException("Sexo inválido! Por favor, digite um tipo válido (Femea/Macho) ou deixe em branco >> ");

    }


}
