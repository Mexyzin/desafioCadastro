package repository;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PetRepository {

    private static final String DIRECTORY = "petsCadastrados";

    public PetRepository(){
        inicializarDiretorio();
    }

    private void inicializarDiretorio() {
        Path pastaPath = Paths.get(DIRECTORY);
        if (Files.notExists(pastaPath)) {
            try {
                Files.createDirectory(pastaPath);
            } catch (IOException e) {
                System.err.println("Erro crítico: Não foi possível criar a pasta " + DIRECTORY);
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

            if (i + 1 == 4) {
                formatarLinhas.add((i + 1) + " - " + "Rua " + respostas.get(i));
            } else if (i + 1 == 5) {
                formatarLinhas.add((i + 1) + " - " + (respostas.get(i).equals("NAO_INFORMADO") ? respostas.get(i) : respostas.get(i) + " anos"));
            } else if (i + 1 == 6) {
                formatarLinhas.add((i + 1) + " - " + (respostas.get(i).equals("NAO_INFORMADO") ? respostas.get(i) : respostas.get(i) + "kg"));
            } else {
                formatarLinhas.add((i + 1) + " - " + respostas.get(i));
            }

        }
        return formatarLinhas;
    }

    public List<Pet> listarPets() {
        List<Pet> pets = new ArrayList<>();
        Path pastaPath = Paths.get(DIRECTORY);

        if (Files.notExists(pastaPath)){
            return pets;
        }

        try (Stream<Path> paths = Files.list(pastaPath)){
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> {
                        Pet pet = carregarPetDeArquivo(p);
                        if(pet != null){
                            pets.add(pet);
                        }
                    });
        } catch (IOException e){
            System.out.println("Erro ao listar pets: " + e.getMessage());
        }

        return pets;
    }

    private Pet carregarPetDeArquivo(Path arquivo){
        try {
            List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
            if (linhas.size() < 7)
                return null;

            String nome = extrairValor(linhas.get(0));
            String tipoStr = extrairValor(linhas.get(1));
            String sexoStr = extrairValor(linhas.get(2));
            String enderecoCompleto = extrairValor(linhas.get(3));
            String idade = extrairValor(linhas.get(4).replace(" anos", ""));
            String peso = extrairValor(linhas.get(5).replace("kg", ""));
            String raca = extrairValor(linhas.get(6));

            TipoPet tipoPet = TipoPet.validarTipoPet(tipoStr);
            SexoPet sexoPet = SexoPet.validarSexoPet(sexoStr);

            if (enderecoCompleto.startsWith("Rua ")){
                enderecoCompleto = enderecoCompleto.substring(4);
            }

            String[] partesEnd = enderecoCompleto.split(", ");
            String rua = partesEnd.length > 0 ? partesEnd[0] : "";
            String numero = partesEnd.length > 1 ? partesEnd[1] : "";
            String cidade =partesEnd.length > 2 ? partesEnd[2] : "";

            Endereço endereço = new Endereço(numero, cidade, rua);
            Pet pet = new Pet(nome, tipoPet, sexoPet, endereço, idade, peso, raca);
            pet.setNomeArquivo(arquivo.getFileName().toString());
            return pet;
        } catch (Exception e){
            System.out.println("Aviso: arquivo possivelmente corrompoido ou formato inválido ignorado (" + arquivo.getFileName() + "): " + e.getMessage());
            return null;
        }
    }

    private String extrairValor (String linha){
        int tracoIdx = linha.indexOf(" - ");
        if (tracoIdx != -1 && linha.length() > tracoIdx + 3){
            return linha.substring(tracoIdx + 3).trim();
        }

        return linha;
    }

    public void alterarDadosPet(Pet petAntigo, Pet petNovo, List<String> novasRespostas){
        String novoNomeArquivo = gerarNomeArquivo(petNovo);
        Path novoPath = Paths.get(DIRECTORY, novoNomeArquivo);

        List<String> conteudoFinal = formatarConteudo(novasRespostas);

        try (BufferedWriter bw = Files.newBufferedWriter(novoPath, StandardCharsets.UTF_8)){
            for (String line : conteudoFinal){
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e){
            System.err.println("ERRO CRITICO: Falha ao gravar novo ficheiro. Dados antigos preservados.");
            throw new RuntimeException("Atualização cancelada: " + e.getMessage(), e);
        }

        if (Files.exists(novoPath)){
            try {
                Path pathAntigo = Paths.get(DIRECTORY, petAntigo.getNomeArquivo());
                Files.deleteIfExists(pathAntigo);
            }catch (IOException e){
                System.err.println("AVISO: Novo ficheiro criado, mas falha ao remover antigo: " + e.getMessage());
            }
        }
    }

    public List<String> realizarMergeDeDados(Pet petAntigo, String[] novosCampos){
        List<String> respostaMescladas = new ArrayList<>();

        respostaMescladas.add(novosCampos[0] != null ? novosCampos[0] : petAntigo.getNomeCompleto());
        respostaMescladas.add(novosCampos[1] != null ? novosCampos[1] : petAntigo.getTipoPet().getTipoPet());
        respostaMescladas.add(novosCampos[2] != null ? novosCampos[2] : petAntigo.getSexoPet().getSexoPet());
        respostaMescladas.add(novosCampos[3] != null ? novosCampos[3] : petAntigo.getEndereco().toString());
        respostaMescladas.add(novosCampos[4] != null ? novosCampos[4] : petAntigo.getIdade());
        respostaMescladas.add(novosCampos[5] != null ? novosCampos[5] : petAntigo.getPeso());
        respostaMescladas.add(novosCampos[6] != null ? novosCampos[6] : petAntigo.getRaca());

        return respostaMescladas;
    }

    public void deletarArquivoPet(String nomeArquivo){
        try {
            Path path = Paths.get(DIRECTORY, nomeArquivo);
            Files.deleteIfExists(path);
        } catch (IOException e){
            System.out.println("Erro ao remover arquivo antigo: " + e.getMessage());
        }
    }

}
