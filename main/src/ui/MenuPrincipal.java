package ui;

import repository.FormularioRepository;
import services.PetService;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    public static void viewForm() {
        FormularioRepository formularioRepository = new FormularioRepository();
        List<String> listForm = formularioRepository.lerPerguntas();
        for (String s : listForm) {
            System.out.println(s);
        }
    }

    public static void showMenu(Scanner sc) {
        FormularioRepository formularioRepository = new FormularioRepository();
        List<String> menuOptions = formularioRepository.lerMenu();
        PetService petService = new PetService();


        while (true) {


            System.out.println("\n=====================================================\n" +
                    "          \uD83D\uDC3E SISTEMA DE ADOÇÃO PET AMIGO \uD83D\uDC3E          \n" +
                    "=====================================================");
            System.out.println();
            menuOptions.forEach(System.out::println);
            System.out.println("=====================================================");


            System.out.print("\nEscolha uma opção >> ");
            int option;

            while (true) {
                try {
                    option = Integer.parseInt(sc.nextLine());
                    if (option <= 0 || option > 6) {
                        System.out.print("Opção inválida! Digite um opção válida >> ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Formato inválido! Por favor, digite um valor válido >> ");
                }
            }

            switch (option) {
                case 1:
                    petService.cadastrarPet(sc);
                    break;
                case 2:
                    petService.alterarPet(sc);
                    break;
                case 3:
                    petService.deletarPet(sc);
                    break;
                case 4:
                    petService.listarTodosPets();
                    break;
                case 5:
                    MenuBusca.buscarPet(sc);
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
            }
        }

    }

}
