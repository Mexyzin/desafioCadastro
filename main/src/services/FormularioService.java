package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormularioService {

    public static List<String> readFile() {
        File file = new File("/home/mexy/Documentos/IntelliJ/desafioCadastro/main/resources/formulario.txt");
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

}
