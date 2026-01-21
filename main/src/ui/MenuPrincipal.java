package ui;

import services.FormularioService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    public static void viewForm() {
        List<String> listForm = FormularioService.readFile();
        for (String s : listForm) {
            System.out.println(s);
        }
    }

    public static List<String> menuList() {
        File file = new File("/home/mexy/Documentos/IntelliJ/desafioCadastro/main/resources/Menu.txt");
        List<String> menuList = new ArrayList<>();
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                menuList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public static void showMenu() {

        List<String> menuOptions = menuList();
        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================================\n" +
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
                if (option <= 0 || option > 6){
                    System.out.print("Opção inválida! Digite um opção válida >> ");
                }else {
                    System.out.println("Deu foi certo.");
                    sc.close();
                    break;
                }
            }catch (NumberFormatException e) {
                System.out.print("Formato inválido! Por favor, digite um valor válido >> ");
            }
        }

    }


//
//    public static void limparTerminal() {
//        try {
//            String os = System.getProperty("os.name").toLowerCase();
//
//            if (os.contains("win")) {
//                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//            } else {
//                new ProcessBuilder("clear").inheritIO().start().waitFor();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void main(String[] args) {
        showMenu();
    }
}
