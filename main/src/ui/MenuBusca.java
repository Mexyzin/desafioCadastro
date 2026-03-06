package ui;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;
import repository.PetRepository;
import services.BuscarPetService;
import services.PetService;

import java.util.*;

public class MenuBusca {

//    public void exibirMenuBusca() {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("Escolha o TIPO DE ANIMAL:\n" + "1 - Cachorro\n" + "2 - Gato\n>> ");
//
//        int input = 0;
//
//        while (true){
//            try {
//                input = sc.nextInt();
//                if (input == 1 || input == 2){
//                    break;
//                }
//                System.out.print("Por favor, digite umas das opções disponível (1 - Cachorro/2 - Gato) >> ");
//            }catch (InputMismatchException e){
//                System.out.print("Formato incorreto! Digite apenas números (1 - Cachorro/2 - Gato) >> ");
//                sc.next();
//            }
//        }
//
//        System.out.println("\nVocê deseja filtrar como:");
//        System.out.println("1 - Nome ou sobrenome\n" +
//                "2 - Sexo\n" +
//                "3 - Idade\n" +
//                "4 - Peso\n" +
//                "5 - Raça\n" +
//                "6 - Endereço");
//
//    }

    public static void iniciarBusca(){
        Scanner sc = new Scanner(System.in);
        PetRepository petRepository = new PetRepository();
        BuscarPetService buscarPetService = new BuscarPetService();


        System.out.println("\n==================================");
        System.out.println("          BUSCA DE PETS         ");
        System.out.println("==================================");

        TipoPet tipoBuscado = PetService.perguntarAteValido(
                "Digite o tipo de animal (Gato/Cachorro) [Obrigatório]:",
                sc, TipoPet::validarTipoPet);
        Map<String, String> filtros = new HashMap<>();

        while (filtros.size() < 2){
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
            }catch (NumberFormatException e){
                System.out.println("Opcao inválida.");
                continue;
            }

            if (opcao == 0){
                break;
            }

            switch (opcao){
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
                    if(!idade.equals("NÃO INFORMADO")){
                        filtros.put("idade", idade);
                    }
                    break;
                case 4:
                    String peso = PetService.perguntarAteValido(
                            "Digite o Peso:",
                            sc, Pet::validarPesoPet);
                    if (!peso.equals("NÃO INFORMADO")){
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
        if (encontrados.isEmpty()){
            System.out.println("Nenhum pet encontrado com estes critérios");
        }else {
            for (int i = 0; i < encontrados.size(); i++){
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
            System.out.println("\n(Futuro: Selecione o ID do Pet para Alterar ou Deletar)\n");
        }
    }

    public static String validarTextoBusca(String texto){
        if (texto == null || texto.isBlank()){
            throw new IllegalArgumentException("Valor de busca não pode ser vazio");
        }

        return texto.trim();
    }

    public static void main(String[] args) {
        MenuBusca menuBusca = new MenuBusca();

        MenuBusca.iniciarBusca();
    }
}
