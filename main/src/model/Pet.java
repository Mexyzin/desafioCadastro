package model;

import exception.ValidacaoException;
import model.enums.SexoPet;
import model.enums.TipoPet;

import java.util.regex.Pattern;

public class Pet {
    private String nomeCompleto;
    private TipoPet tipoPet;
    private SexoPet sexoPet;
    private Endereço endereco;
    private String idade;
    private String peso;
    private String raca;
    private String nomeArquivo;
    private static final Pattern REGEX_NOME = Pattern.compile("^[A-Za-z]+(?:\\s+[A-Za-z]+)+$");
    private static final Pattern REGEX_PESO = Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Pattern REGEX_RACA = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)*$");
    private final static String VALOR_PADRAO = "NAO_INFORMADO";

    public Pet(String nomeCompleto, TipoPet tipoPet, SexoPet sexoPet, Endereço endereco, String idade, String peso, String raca) {
        this.nomeCompleto = validarNome(nomeCompleto);
        this.tipoPet = tipoPet;
        this.sexoPet = sexoPet;
        this.endereco = endereco;
        this.idade = validarIdade(idade);
        this.peso = validarPesoPet(peso);
        this.raca = validarRaca(raca);
    }

    public static String validarNome(String nomeCompleto) {
        if (isCampoVazio(nomeCompleto)) {
            return VALOR_PADRAO;
        }

        if (!REGEX_NOME.matcher(nomeCompleto).matches()) {
            throw new ValidacaoException("Nome inválido. Informe nome e sobrenome, somente letras ou deixe vazio (apenas aperte enter) >> ");
        }

        return nomeCompleto;
    }

    public static String validarPesoPet(String pesoString) {
        if (isCampoVazio(pesoString) || pesoString.equals("NAO_INFORMADO")) {
            return VALOR_PADRAO;
        }

        double peso;

        if (REGEX_PESO.matcher(pesoString).matches()) {
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

        if (!REGEX_RACA.matcher(nomeFormatado).matches()) {
            throw new ValidacaoException("Raça invalida. Digite apenas letras ou deixe vazio (apenas aperte enter) >> ");
        }

        return nomeFormatado;
    }

    private static boolean isCampoVazio(String campo){
        return campo == null || campo.isBlank();
    }

    @Override
    public String toString() {
        return "Pet{" +
                "nomeCompleto='" + nomeCompleto + '\'' +
                ", tipoPet=" + tipoPet +
                ", sexoPet=" + sexoPet +
                ", endereço=" + endereco +
                ", idade='" + idade + '\'' +
                ", peso='" + peso + '\'' +
                ", raça='" + raca + '\'' +
                '}';
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public TipoPet getTipoPet() {
        return tipoPet;
    }

    public SexoPet getSexoPet() {
        return sexoPet;
    }

    public Endereço getEndereco() {
        return endereco;
    }

    public String getIdade() {
        return idade;
    }

    public String getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}



