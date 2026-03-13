import ui.MenuPrincipal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MenuPrincipal.showMenu(sc);

        sc.close();
    }
}