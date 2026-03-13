package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FormularioRepository {

    public List<String> lerPerguntas() {
        Path path = Paths.get("main", "resources", "formulario.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo no caminho: " + path.toAbsolutePath());
            return new ArrayList<>();
        }
    }

    public List<String> lerMenu() {
        Path path = Paths.get("main", "resources", "menu.txt");

        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo no caminho: " + path.toAbsolutePath());
            return new ArrayList<>();
        }
    }

    public List<String> lerPerguntasEndereco() {
        Path path = Paths.get("main", "resources", "formularioEndereco.txt");
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo no caminho: " + path.toAbsolutePath());
            return new ArrayList<>();
        }
    }

}
