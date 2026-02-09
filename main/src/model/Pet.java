package model;

import exception.ValidacaoException;
import model.enums.SexoPet;
import model.enums.TipoPet;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Pet {
    private String nomeCompleto;
    private TipoPet tipoPet;
    private SexoPet sexoPet;
    private Endereço endereço;
    private String idade;
    private String peso;
    private String raça;
    private final static String VALOR_PADRAO = "NAO_INFORMADO";


    public Pet(String nomeCompleto, TipoPet tipoPet, SexoPet sexoPet, Endereço endereço, String idade, String peso, String raça) {
        this.nomeCompleto = nomeCompleto;
        this.tipoPet = tipoPet;
        this.sexoPet = sexoPet;
        this.endereço = endereço;
        this.idade = idade;
        this.peso = peso;
        this.raça = raça;
    }


    public static String validarNome(String nomeCompleto) {
        if (isCampoVazio(nomeCompleto)) {
            return VALOR_PADRAO;
        }

        String regex = "^[A-Za-z]+(?:\\s+[A-Za-z]+)+$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(nomeCompleto).matches()) {
            throw new ValidacaoException("Nome inválido. Informe nome e sobrenome, somente letras ou deixe vazio (apenas aperte enter) >> ");
        }

        return nomeCompleto;
    }

    public static String validarPesoPet(String pesoString) {
        if (isCampoVazio(pesoString)) {
            return VALOR_PADRAO;
        }

        Pattern pattern = Pattern.compile("^\\d+$");
        double peso;

        if (pattern.matcher(pesoString).matches()) {
            peso = Double.parseDouble(pesoString);
            if (peso < 60 && peso > 0.5) {
                return pesoString;
            }
            throw new ValidacaoException("Peso inválido. Informe um valor válido ou deixe vazio (apenas aperte enter) >> ");
        }
        throw new ValidacaoException("Peso inválido. Informe um peso valido, somente numeros ou deixe vazio (apenas aperte enter) >> ");

    }

    public static String validarIdade(String idadeRecebida) {
        if (isCampoVazio(idadeRecebida)) {
            return VALOR_PADRAO;
        }

        String resultado = processarLogicaIdade(idadeRecebida);
        if (resultado != null) {
            return resultado;
        }

        throw new ValidacaoException("Por favor, informe uma idade válida ou deixe vazio (apenas aperte enter) >> ");

    }

    public static String processarLogicaIdade(String valor) {
        try {
            float idadeNum = Float.parseFloat(valor.replace(",", "."));
            if (idadeNum >= 1 && idadeNum <= 20) {
                return String.valueOf(idadeNum);
            } else if (idadeNum > 0 && idadeNum < 1) {
                float idadeMeses = (idadeNum * 10) / 12;
                return String.valueOf(idadeMeses);
            }
            return null;

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String validarRaca(String raca) {
        if (isCampoVazio(raca)) {
            return VALOR_PADRAO;
        }

        String nomeFormatado = raca.trim();
        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(nomeFormatado).matches()) {
            throw new ValidacaoException("Raça invalida. Digite apenas letras ou deixe vazio (apenas aperte enter) >> ");
        }

        return nomeFormatado;
    }

    private static boolean isCampoVazio(String campo){
        return campo == null || campo.isBlank();
    }

}



