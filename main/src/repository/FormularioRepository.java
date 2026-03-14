package repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FormularioRepository {

    public List<String> lerPerguntas() {
        return lerArquivoDoClasshPath("/formulario.txt");
    }


    public List<String> lerMenu() {
        return lerArquivoDoClasshPath("/menu.txt");
    }

    public List<String> lerPerguntasEndereco() {
       return lerArquivoDoClasshPath("/formularioEndereco.txt");
    }

    private List<String> lerArquivoDoClasshPath(String nomeArquivo) {
        try (InputStream is = getClass().getResourceAsStream(nomeArquivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            if (is == null) {
                System.out.println("Aviso: Arquivo: " + nomeArquivo + " não encontrado no classpath.");
            }

            return br.lines().toList();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo no caminho: " + nomeArquivo);
            return new ArrayList<>();
        }
    }

}
