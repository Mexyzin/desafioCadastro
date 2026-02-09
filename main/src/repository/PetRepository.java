package repository;

import model.Pet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PetRepository {

    private static final String DIRECTORY = "petsCadastrados";

    {
        Path pastaPath = Paths.get(DIRECTORY);
        if (Files.notExists(pastaPath)) {
            try {
                Files.createDirectory(pastaPath);
            } catch (IOException e) {
                System.out.println("Não foi possivel criar a pasta: " + e.getMessage());
            }
        }
    }

    public void salvarPet(Pet pet, List<String> respostas) {

        String nomeArquivo = gerarNomeArquivo(pet);
        Path paths = Paths.get(DIRECTORY, nomeArquivo);
        List<String> lines = formatarConteudo(respostas);


        try (BufferedWriter bw = Files.newBufferedWriter(paths, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public String gerarNomeArquivo(Pet pet) {
        LocalDateTime agora = LocalDateTime.now();
        String dataFormatada = agora.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));
        String nomeArquivo = dataFormatada + "-" + pet.getNomeCompleto().toUpperCase().replace(" ", "");

        return nomeArquivo + ".txt";
    }

    public List<String> formatarConteudo(List<String> respostas) {
        List<String> formatarLinhas = new ArrayList<>();

        for (int i = 0; i < respostas.size(); i++) {

                if (i + 1 == 4){
                    formatarLinhas.add((i + 1) + " - " + "Rua " + respostas.get(i));
                }else if (i + 1 == 5){
                    formatarLinhas.add((i + 1) + " - " + respostas.get(i) + " anos");
                } else if (i + 1 == 6) {
                    formatarLinhas.add((i + 1) + " - " + respostas.get(i) + "kg");
                }else {
                    formatarLinhas.add((i + 1) + " - " + respostas.get(i));
                }

        }

        return formatarLinhas;
    }


}
