package ui;

import repository.FormularioRepository;
import services.FormularioService;
import services.PetService;

import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private final PetService petService;
    private final FormularioRepository formularioRepository;
    private final MenuBusca menuBusca;

    public MenuPrincipal(PetService petService, FormularioRepository formularioRepository, MenuBusca menuBusca){
        this.petService = petService;
        this.formularioRepository = formularioRepository;
        this.menuBusca = menuBusca;
    }

    public void viewForm() {
        List<String> listForm = this.formularioRepository.lerPerguntas();
        for (String s : listForm) {
            System.out.println(s);
        }
    }

    public void showMenu(Scanner sc) {
        List<String> menuOptions = this.formularioRepository.lerMenu();

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
                    this.petService.cadastrarPet(sc);
                    break;
                case 2:
                    this.petService.alterarPet(sc);
                    break;
                case 3:
                    this.petService.deletarPet(sc);
                    break;
                case 4:
                    this.petService.listarTodosPets();
                    break;
                case 5:
                    menuBusca.buscarPet(sc);
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    System.exit(0);
            }
        }

    }

}
