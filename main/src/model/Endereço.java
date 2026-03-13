package model;

import exception.ValidacaoException;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Endereço {
    private String numeroDaCasa;
    private String cidade;
    private String rua;
    private static final String VALOR_PADRAO = "NAO_INFORMADO";

    public Endereço(String numeroDaCasa, String cidade, String rua) {
        this.numeroDaCasa = validarNumeroDaCasa(numeroDaCasa);
        this.cidade = validarCidade(cidade);
        this.rua = validarRua(rua);
    }

    public static String validarNumeroDaCasa(String numeroDaCasa) {

        if (isCampoVazio(numeroDaCasa)) {
            return VALOR_PADRAO;
        }
        String regex = "^\\d+[A-Za-z]?(?:\\s?-?\\s?\\d+)?$";
        Pattern pattern = Pattern.compile(regex);
        String nomeFormatado = numeroDaCasa.trim();

        if (nomeFormatado == null || nomeFormatado.isBlank()) {
            return VALOR_PADRAO;
        }

        if (!pattern.matcher(nomeFormatado).matches()) {
            throw new ValidacaoException("Numero inválido. Informe numero de casa válido >> ");
        }

        return nomeFormatado;

    }

    public static String validarCidade(String nomeDaCidade) {
        if (isCampoVazio(nomeDaCidade)) {
            throw new ValidacaoException("Campo vazio! Informe uma cidade >> ");
        }

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$";
        Pattern pattern = Pattern.compile(regex);
        String nomeFormatado = nomeDaCidade.trim();

        if (!pattern.matcher(nomeFormatado).matches()) {
            throw new ValidacaoException("Nome inválido. Informe uma cidade válida >> ");
        }

        return nomeFormatado;

    }

    public static String validarRua(String nomeDaRua) {
        if (isCampoVazio(nomeDaRua)){
            throw new ValidacaoException("Campo vazio! Informe uma rua >> ");
        }

        String regexRua = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9\\s.,\\-ºª']+$";
        Pattern pattern = Pattern.compile(regexRua);
        String nomeFormatado = nomeDaRua.trim();

        if (!pattern.matcher(nomeFormatado).matches()) {
            throw new ValidacaoException("Nome de rua inválido. Informe uma rua válida >> ");
        }

        return nomeFormatado;

    }

    @Override
    public String toString() {
        return rua + ", " + numeroDaCasa + ", " + cidade;
    }

    private static boolean isCampoVazio(String campo){
        return campo == null || campo.isBlank();
    }


}
