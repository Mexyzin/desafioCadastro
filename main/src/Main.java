import repository.FormularioRepository;
import repository.PetRepository;
import services.BuscarPetService;
import services.FormularioService;
import services.PetService;
import ui.MenuBusca;
import ui.MenuPrincipal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FormularioRepository formularioRepository = new FormularioRepository();
        PetRepository petRepository = new PetRepository();

        FormularioService formularioService = new FormularioService(formularioRepository);
        BuscarPetService buscarPetService = new BuscarPetService();

        MenuBusca menuBusca = new MenuBusca(petRepository, buscarPetService);
        PetService petService = new PetService(formularioService, petRepository, menuBusca);

        MenuPrincipal menuPrincipal = new MenuPrincipal(petService, formularioRepository, menuBusca);

        menuPrincipal.showMenu(sc);

        sc.close();
    }
}