package ui;

import services.FormularioService;

import java.util.List;

public class MenuPrincipal {

    public static void viewForm() {
        List<String> listForm = FormularioService.readFile();
        for (String s : listForm) {
            System.out.println(s);
        }
    }

}
