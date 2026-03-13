package services;

import model.Pet;
import model.enums.TipoPet;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BuscarPetService {

    private String verificarNome(Scanner sc) {
        String nomeFormatado;

        while (true) {
            nomeFormatado = sc.nextLine().trim();

            if (nomeFormatado.isBlank()) {
                System.out.print("Por favor, digite um nome >> ");
                continue;
            }

            if (!nomeFormatado.matches("^[\\p{L} ]+$")) {
                System.out.print("Nome invalido, digite novamente >> ");
                continue;
            }

            return nomeFormatado;
        }
    }

    public List<Pet> buscarPets (List<Pet> todosPets, TipoPet tipo, Map<String, String> filtrosAdicionais){
        List<Pet> resultado = new ArrayList<>();
        for (Pet pet : todosPets) {
            if (pet.getTipoPet() != tipo){
                continue;
            }

            boolean atendeFiltros = true;

            if (filtrosAdicionais != null && !filtrosAdicionais.isEmpty()){
                for(Map.Entry<String, String> filtro : filtrosAdicionais.entrySet()){
                    String criterio = removerAcentos(filtro.getKey().toLowerCase());
                    String valorBuscado = filtro.getValue();

                    boolean match = false;

                    switch (criterio){
                        case "nome":
                            match = matchParcial(pet.getNomeCompleto(), valorBuscado);
                            break;
                        case "sexo":
                            match = matchParcial(pet.getSexoPet().getSexoPet(), valorBuscado);
                            break;
                        case "idade":
                            match = matchParcial(pet.getIdade(), valorBuscado);
                            break;
                        case "peso":
                            match = matchParcial(pet.getPeso(), valorBuscado);
                            break;
                        case "raca":
                            match = matchParcial(pet.getRaça(), valorBuscado);
                            break;
                        case "endereco":
                            match = matchParcial(pet.getEndereço().toString(), valorBuscado);
                    }

                    if (!match){
                        atendeFiltros = false;
                        break;
                    }
                }
            }

            if (atendeFiltros){
                resultado.add(pet);
            }
        }

        return resultado;
    }

    private boolean matchParcial (String valorReal, String valorBuscado) {
        if (valorReal == null || valorBuscado == null)
            return false;

        String limpoReal = removerAcentos(valorReal.toLowerCase());
        String limpoBuscado = removerAcentos(valorBuscado.toLowerCase());
        return limpoReal.contains(limpoBuscado);
    }

    private String removerAcentos(String str){
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
