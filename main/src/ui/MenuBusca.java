package ui;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;
import repository.PetRepository;
import services.BuscarPetService;
import services.PetService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuBusca {

    public static List<Pet> buscarPet(Scanner sc) {
        PetRepository petRepository = new PetRepository();
        BuscarPetService buscarPetService = new BuscarPetService();


        System.out.println("\n==================================");
        System.out.println("          BUSCA DE PETS         ");
        System.out.println("==================================");

        TipoPet tipoBuscado = PetService.perguntarAteValido(
                "Digite o tipo de animal (Gato/Cachorro) [Obrigatório]:",
                sc, TipoPet::validarTipoPet);
        Map<String, String> filtros = new HashMap<>();

        while (filtros.size() < 2) {
            System.out.println("\nDeseja adicionar um critério de busca? (Filtros extra: " + filtros.size() + "/2");
            System.out.println("1 - Nome");
            System.out.println("2 - Sexo");
            System.out.println("3 - Idade");
            System.out.println("4 - Peso");
            System.out.println("5 - Raça");
            System.out.println("6 - Endereço");
            System.out.println("0 - Concluir e Buscar");
            System.out.print("Escolha uma opção >> ");

            String opcaoStr = sc.nextLine();
            int opcao = -1;
            try {
                opcao = Integer.parseInt(opcaoStr);
            } catch (NumberFormatException e) {
                System.out.println("Opcao inválida.");
                continue;
            }

            if (opcao == 0) {
                break;
            }

            switch (opcao) {
                case 1:
                    String nome = PetService.perguntarAteValido(
                            "Digite o trecho do Nome para busca:",
                            sc,
                            MenuBusca::validarTextoBusca);
                    filtros.put("nome", nome);
                    break;
                case 2:
                    String sexo = PetService.perguntarAteValido(
                            " Digite o Sexo (Femea/Macho):",
                            sc,
                            SexoPet::validarSexoPet).getSexoPet();
                    filtros.put("sexo", sexo);
                    break;
                case 3:
                    String idade = PetService.perguntarAteValido(
                            "Digite a Idade:",
                            sc, Pet::validarIdade);
                    if (!idade.equals("NÃO INFORMADO")) {
                        filtros.put("idade", idade);
                    }
                    break;
                case 4:
                    String peso = PetService.perguntarAteValido(
                            "Digite o Peso:",
                            sc, Pet::validarPesoPet);
                    if (!peso.equals("NÃO INFORMADO")) {
                        filtros.put("peso", peso);
                    }
                    break;
                case 5:
                    String raça = PetService.perguntarAteValido(
                            "Digite a Raça:",
                            sc, MenuBusca::validarTextoBusca);
                    filtros.put("raça", raça);
                    break;
                case 6:
                    String endereço = PetService.perguntarAteValido(
                            "Digite um trecho do Endereço:",
                            sc, MenuBusca::validarTextoBusca);
                    filtros.put("Endereço", endereço);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        System.out.println("\nBuscando....");
        List<Pet> todosPets = petRepository.listarPets();
        List<Pet> encontrados = buscarPetService.buscarPets(todosPets, tipoBuscado, filtros);

        System.out.println("\n --- RESULTADDOS DA BUSCA---");
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum pet encontrado com estes critérios");
        } else {
            for (int i = 0; i < encontrados.size(); i++) {
                Pet p = encontrados.get(i);
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

        return encontrados;
    }

    public static String validarTextoBusca(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Valor de busca não pode ser vazio");
        }

        return texto.trim();
    }

    public static String[] menuAlterarDados(Scanner sc, Pet petAntigo) {
        String[] camposEditados = new String[7];
        boolean algumCampoEditando = false;


        System.out.println("\n==================================");
        System.out.println("      ALTERAR DADOS DOS PETS         ");
        System.out.println("==================================");
        System.out.println("\nDados atuais do pet:");
        System.out.println(" - Nome: " + petAntigo.getNomeCompleto());
        System.out.println(" - Tipo: " + petAntigo.getTipoPet().getTipoPet() + " [NAO PODE SER ALTERADO]");
        System.out.println(" - Sexo: " + petAntigo.getSexoPet().getSexoPet() + " [NAO PODE SER ALTERADO]");
        System.out.println(" - Idade: " + petAntigo.getIdade() + " anos");
        System.out.println(" - Peso: " + petAntigo.getPeso() + " kg");
        System.out.println(" - Raça: " + petAntigo.getRaça());
        System.out.println(" - Endereço: " + "Rua " + petAntigo.getEndereço().toString());

        while (true) {
            System.out.println("\n Qual dado do Pet você deseja alterar? [O Tipo e Sexo do Pet não são possível de alterar]");
            System.out.println("\n1 - Nome");
            System.out.println("2 - Idade");
            System.out.println("3 - Peso");
            System.out.println("4 - Raça");
            System.out.println("5 - Endereço");
            System.out.println("0 - " + (algumCampoEditando ? "Finalizar Edição" : "Cancelar"));
            System.out.print("Escolha uma opção >> ");

            int opcao = -1;


            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Formato inválido. Digite um número >> ");
                continue;
            }


            if (opcao == 0) {
                if (!algumCampoEditando) {
                    System.out.println("\nEdicão cancelada. Nenhum dado foi alterado.");
                }
                break;
            }

            switch (opcao) {
                case 1:
                    String nome = PetService.perguntarAteValido("Digite o novo Nome:",
                            sc, Pet::validarNome);
                    camposEditados[0] = nome;
                    algumCampoEditando = true;
                    System.out.println("Nome atualizado!");
                    break;
                case 2:
                    String idade = PetService.perguntarAteValido("Digite a nova Idade:",
                            sc, Pet::validarIdade);
                    camposEditados[4] = idade;
                    algumCampoEditando = true;
                    System.out.println("Idade atualizada!");
                    break;
                case 3:
                    String peso = PetService.perguntarAteValido("Digite o novo Peso:",
                            sc, Pet::validarPesoPet);
                    camposEditados[5] = peso;
                    algumCampoEditando = true;
                    System.out.println("Peso atualizado!");
                    break;
                case 4:
                    String raca = PetService.perguntarAteValido("Digite a nova Raça:",
                            sc, Pet::validarRaca);
                    camposEditados[6] = raca;
                    algumCampoEditando = true;
                    System.out.println("Raça atualizada!");
                    break;
                case 5:
                    String enderecoEditando = alterarEndereco(sc, petAntigo);
                    camposEditados[3] = enderecoEditando;
                    algumCampoEditando = true;
                    System.out.println("Endereço atualizado!");
                    break;
                default:
                    System.out.println("Opção inválida. Digite um número entre 0 e 5.");
                    break;
            }
        }
        return camposEditados;
    }

    public static String alterarEndereco(Scanner sc, Pet petAntigo) {

        System.out.print("\nVocê deseja alterar que parte do endereço?\n");
        System.out.println("1 - Cidade");
        System.out.println("2 - Rua");
        System.out.println("3 - Numero da Casa");
        System.out.println("4 - Endereço Completo");
        System.out.print("Escolha uma opção >> ");

        int numeroOpcaoEndereco = -1;

        while (true) {
            try {
                numeroOpcaoEndereco = Integer.parseInt(sc.nextLine());
                if (numeroOpcaoEndereco >= 1 && numeroOpcaoEndereco <= 4) {
                    break;
                }
                System.out.print("Opção inválida. Digite um número entre 1 e 4 >> ");
            } catch (NumberFormatException e) {
                System.out.print("Formato inválido >> ");
            }
        }

        String[] partesAtual = petAntigo.getEndereço().toString().split(", ");
        String ruaAtual = partesAtual.length > 0 ? partesAtual[0] : "";
        String numeroAtual = partesAtual.length > 1 ? partesAtual[1] : "";
        String cidadeAtual = partesAtual.length > 2 ? partesAtual[2] : "";


        switch (numeroOpcaoEndereco) {
            case 1:
                String cidade = PetService.perguntarAteValido("Digite a nova Cidade:",
                        sc, Endereço::validarCidade);
                return ruaAtual + ", " + numeroAtual + ", " + cidade;

            case 2:
                String rua = PetService.perguntarAteValido("Digite a nova Rua:",
                        sc, Endereço::validarRua);
                return rua + ", " + numeroAtual + ", " + cidadeAtual;

            case 3:
                String numeroDaCasa = PetService.perguntarAteValido("Digite o novo número da casa:",
                        sc, Endereço::validarNumeroDaCasa);
                return ruaAtual + ", " + numeroDaCasa + ", " + cidadeAtual;

            case 4:
                System.out.println("\nDigite o novo endereço completo:");
                String numeroDaCasaEndereco = PetService.perguntarAteValido("   " + "  i. Numero da casa?",
                        sc, Endereço::validarNumeroDaCasa);
                String nomeDaCidadeEndereco = PetService.perguntarAteValido("   " + "ii. Nome da Rua?",
                        sc, Endereço::validarRua);
                String nomeDaRuaEndereco = PetService.perguntarAteValido("  " + "  iii. Nome da Cidade?",
                        sc, Endereço::validarCidade);
                return nomeDaRuaEndereco + ", " + numeroDaCasaEndereco + ", " + nomeDaCidadeEndereco;
        }

        return petAntigo.getEndereço().toString();
    }

}

