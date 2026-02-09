package services;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class PetService {

    public void cadastrarPet() {
        FormularioService formularioService = new FormularioService();
        Scanner sc = new Scanner(System.in);

        List<String> listForm = formularioService.obterPerguntasDoFormulario();
        List<String> listFormEndereco = formularioService.obterPerguntasDoFormularioEndereco();

        String nome = perguntarAteValido(listForm.get(0), sc, Pet::validarNome);
        TipoPet tipo = perguntarAteValido(listForm.get(1), sc, TipoPet::validarTipoPet);
        SexoPet sexo = perguntarAteValido(listForm.get(2), sc, SexoPet::validarSexoPet);

        System.out.println(listForm.get(3));
        String numeroDaCasa = perguntarAteValido(listFormEndereco.get(0), sc, Endereço::validarNumeroDaCasa);
        String nomeDaCidade = perguntarAteValido(listFormEndereco.get(1), sc, Endereço::validarCidade);
        String nomeDaRua = perguntarAteValido(listFormEndereco.get(2), sc, Endereço::validarRua);

        Endereço endereço = new Endereço(numeroDaCasa, nomeDaCidade, nomeDaRua);

        String idade = perguntarAteValido(listForm.get(4), sc, Pet::validarIdade);
        String peso = perguntarAteValido(listForm.get(5), sc, Pet::validarPesoPet);
        String raca = perguntarAteValido(listForm.get(6), sc, Pet::validarRaca);

        Pet pet = new Pet(nome, tipo, sexo, endereço, idade, peso, raca);
    }

    private <T> T perguntarAteValido(String pergunta, Scanner sc, Function<String, T> validador) {
        System.out.print(pergunta);
        while (true) {
            String entrada = sc.nextLine();
            try {
                return validador.apply(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }


    private static boolean isCampoVazio(String campo) {
        return campo == null || campo.isBlank();
    }


}
