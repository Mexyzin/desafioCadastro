package services;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;
import repository.PetRepository;
import ui.MenuBusca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static ui.MenuBusca.buscarPet;

public class PetService {

    public void cadastrarPet(Scanner sc) {
        FormularioService formularioService = new FormularioService();

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
        System.out.print(pergunta + " ");
        while (true) {
            String entrada = sc.nextLine();
            try {
                return validador.apply(entrada);
            } catch (IllegalArgumentException e) {
                System.out.print("Erro: " + e.getMessage());
            }
        }
    }

    public void alterarPet(Scanner sc) {
        PetRepository petRepository = new PetRepository();

        List<Pet> todosPets = petRepository.listarPets();
        if (todosPets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }

        List<Pet> pets = buscarPet(sc);

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados. Tente novamente.");
            return;
        }

        System.out.print("\nEscolha o número do pet >> ");
        int escolha = -1;
        while (true) {
            try {
                escolha = Integer.parseInt(sc.nextLine()) - 1;
                if (escolha >= 0 && escolha < pets.size()) {
                    break;
                }
                System.out.print("Opção inválida. Escolha um número entre 1 e " + pets.size() + " >> ");

            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido >> ");
            }
        }

        Pet petAntigo = pets.get(escolha);

        String[] camposEditados = MenuBusca.menuAlterarDados(sc, petAntigo);

        List<String> respostaMescladas = petRepository.realizarMergeDeDados(petAntigo, camposEditados);

        String novoNome = respostaMescladas.get(0);
        TipoPet novoTipo = TipoPet.validarTipoPet(respostaMescladas.get(1));
        SexoPet novoSexo = SexoPet.validarSexoPet(respostaMescladas.get(2));

        String enderecoStr = respostaMescladas.get(3);
        String[] partesEnd = enderecoStr.split(", ");
        String rua = partesEnd.length > 0 ? partesEnd[0] : "";
        String numero = partesEnd.length > 1 ? partesEnd[1] : "";
        String cidade = partesEnd.length > 2 ? partesEnd[2] : "";
        Endereço novoEndereco = new Endereço(numero, cidade, rua);

        String novaIdade = respostaMescladas.get(4);
        String novoPeso = respostaMescladas.get(5);
        String novaRaca = respostaMescladas.get(6);

        Pet petNovo = new Pet(novoNome, novoTipo, novoSexo, novoEndereco, novaIdade, novoPeso, novaRaca);

        try {
            petRepository.alterarDadosPet(petAntigo, petNovo, respostaMescladas);
            System.out.println("\n Pet atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("\n Erro ao atualizar pet: " + e.getMessage());
        }
    }

    public void deletarPet(Scanner sc) {
        List<Pet> pets = buscarPet(sc);

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com os critérios informados. Tente novamente.");
            return;
        }

        int escolha = escolherPet(pets, sc);

        if (confirmarExclusao(pets.get(escolha), sc)){
            PetRepository petRepository = new PetRepository();
            petRepository.deletarArquivoPet(pets.get(escolha).getNomeArquivo());
            System.out.println("\nPet excluído com sucesso.");
        }else {
            System.out.println("Operação cancelada.");
        }
    }

    public void listarTodosPets(){
        PetRepository petRepository = new PetRepository();
        List<Pet> todosPets = petRepository.listarPets();

        if (todosPets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
        }

        System.out.println();
        for (int i = 0; i < todosPets.size(); i++) {
            Pet p = todosPets.get(i);
            String linha = String.format("%d. %s - %s - %s - %s - %s anos - %skg - %s",
                    (i + 1),
                    p.getNomeCompleto(),
                    p.getTipoPet().getTipoPet(),
                    p.getSexoPet().getSexoPet(),
                    p.getEndereço().toString(),
                    p.getIdade(),
                    p.getPeso(),
                    p.getRaça());
            System.out.println(linha);
        }
    }

    private int escolherPet(List<Pet> pets, Scanner sc) {
        System.out.print("\nEscolha o número do pet >> ");

        while (true) {
            try {
                int escolha = Integer.parseInt(sc.nextLine()) - 1;
                if (escolha >= 0 && escolha < pets.size()) {
                    return escolha;
                }
                System.out.print("Opção inválida. Escolha um número entre 1 e " + pets.size() + " >> ");

            } catch (NumberFormatException e) {
                System.out.print("Por favor, digite um número válido >> ");
            }
        }
    }

    private boolean confirmarExclusao(Pet pet, Scanner sc) {
        System.out.print("Você deseja realmente excluir o pet " + pet.getNomeCompleto() + "? (SIM/NAO) >> ");

        while (true) {

            String confirmação = sc.nextLine();

            if (confirmação.equalsIgnoreCase("SIM")) {
                return true;
            }

            if (confirmação.equalsIgnoreCase("NAO")) {
                System.out.println("Operação cancelada.");
                return false;
            }

            System.out.print("Opção inválida. Por favor, escolha entre SIM para concluir a operação e NAO para cancelar >> ");
        }
    }

}
