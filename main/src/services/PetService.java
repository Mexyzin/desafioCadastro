package services;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;
import repository.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class PetService {

    public void cadastrarPet() {
        FormularioService formularioService = new FormularioService();
        Scanner sc = new Scanner(System.in);

        List<String> listForm = formularioService.obterPerguntasDoFormulario();
        List<String> listFormEndereco = formularioService.obterPerguntasDoFormularioEndereco();
        List<String> respostas = new ArrayList<>();

        String nome = perguntarAteValido(listForm.get(0), sc, Pet::validarNome);
        respostas.add(nome);
        TipoPet tipo = perguntarAteValido(listForm.get(1), sc, TipoPet::validarTipoPet);
        respostas.add(tipo.getTipoPet());
        SexoPet sexo = perguntarAteValido(listForm.get(2), sc, SexoPet::validarSexoPet);
        respostas.add(sexo.getSexoPet());

        System.out.println(listForm.get(3));
        String numeroDaCasa = perguntarAteValido("  " + listFormEndereco.get(0), sc, Endereço::validarNumeroDaCasa);
        String nomeDaCidade = perguntarAteValido("  " + listFormEndereco.get(1), sc, Endereço::validarCidade);
        String nomeDaRua = perguntarAteValido("  " + listFormEndereco.get(2), sc, Endereço::validarRua);

        Endereço endereço = new Endereço(numeroDaCasa, nomeDaCidade, nomeDaRua);
        respostas.add(endereço.toString());

        String idade = perguntarAteValido(listForm.get(4), sc, Pet::validarIdade);
        respostas.add(idade);
        String peso = perguntarAteValido(listForm.get(5), sc, Pet::validarPesoPet);
        respostas.add(peso);
        String raca = perguntarAteValido(listForm.get(6), sc, Pet::validarRaca);
        respostas.add(raca);

        Pet pet = new Pet(nome, tipo, sexo, endereço, idade, peso, raca);
        PetRepository petRepository = new PetRepository();
        petRepository.salvarPet(pet, respostas);
    }

    public static <T> T perguntarAteValido(String pergunta, Scanner sc, Function<String, T> validador) {
        while (true) {
            System.out.print(pergunta + " ");
            String entrada = sc.nextLine();
            try {
                return validador.apply(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        PetService petService = new PetService();
        petService.cadastrarPet();
    }

}
