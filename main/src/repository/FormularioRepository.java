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

    public static void main(String[] args) {
        FormularioRepository repository = new FormularioRepository();
        repository.lerPerguntas();
    }
}


/*
    public static List<String> displayMenus(String path){
        File file = new File(path);
        List<String> listForm = new ArrayList<>();

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                listForm.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listForm;
    }
 */