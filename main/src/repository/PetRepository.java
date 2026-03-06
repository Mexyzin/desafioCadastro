package repository;

import model.Endereço;
import model.Pet;
import model.enums.SexoPet;
import model.enums.TipoPet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
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

            if (i + 1 == 4) {
                formatarLinhas.add((i + 1) + " - " + "Rua " + respostas.get(i));
            } else if (i + 1 == 5) {
                formatarLinhas.add((i + 1) + " - " + respostas.get(i) + " anos");
            } else if (i + 1 == 6) {
                formatarLinhas.add((i + 1) + " - " + respostas.get(i) + "kg");
            } else {
                formatarLinhas.add((i + 1) + " - " + respostas.get(i));
            }

        }
        return formatarLinhas;
    }


//    public List<Pet> carregarPets() {
//        List<String> linhas;
//        try {
//            linhas = Files.readAllLines(Path.of(DIRECTORY));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (String linha : linhas) {
//            String[] partes = linha.split(" - ");
//            int codigo = Integer.parseInt(partes[0].trim());
//            String valor = partes[1].trim();
//
//            switch (codigo) {
//                case 1:
//
//            }
//        }
//    }

    public List<Pet> buscarTodosOsPets(){
        List<Pet> listaDePets = new ArrayList<>();
        File diretorio = new File("petsCadastrados");

        File[] arquivos =  diretorio.listFiles();

        if (arquivos != null){
            for (File arquivo : arquivos) {
                try{
                    List<String> linhas = Files.readAllLines(arquivo.toPath());
                    String nome = Pet.validarNome(linhas.get(0).split(" - ")[1]);
                    TipoPet tipoPet = TipoPet.validarTipoPet(linhas.get(1).split(" - ")[1]);
                    SexoPet sexoPet = SexoPet.validarSexoPet(linhas.get(2).split(" - ")[1]);

                    String endereçoCompleto = linhas.get(3).split(" - ")[1];
                    String[] partes = endereçoCompleto.split(",");

                    String rua = Endereço.validarRua(partes[0].trim());
                    String numeroDaCasa = Endereço.validarNumeroDaCasa(partes[1].trim());
                    String cidade = Endereço.validarCidade(partes[2].trim());

                    String idade = Pet.validarIdade(linhas.get(4).split(" - ")[1].replace("anos", ""));
                    String peso = Pet.validarPesoPet(linhas.get(5).split(" - ")[1].replace("kg", ""));
                    String raca = Pet.validarRaca(linhas.get(6).split(" - ")[1]);


                    Endereço endereço = new Endereço(numeroDaCasa, cidade, rua);
                    Pet pet = new Pet(nome, tipoPet, sexoPet, endereço, idade, peso, raca);

                    listaDePets.add(pet);
                }catch (IOException e){
                    System.out.println("Erro ao ler arquivo: " + arquivo.getName());
                }
            }
        }

        return listaDePets;
    }

//    public void listarArquivosTEST() {
//        Path caminho = Paths.get(DIRECTORY);
//
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(caminho)) {
//            for (Path entry : stream) {
//                System.out.println(entry.getFileName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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

            return new Pet(nome, tipoPet, sexoPet, endereço, idade, peso, raca);
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

    public static void main(String[] args) {
        PetRepository petRep = new PetRepository();
//        petRep.listarArquivosTEST();
    }

}
