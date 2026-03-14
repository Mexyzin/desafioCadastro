package services;

import repository.FormularioRepository;

import java.util.List;

public class FormularioService {

    private final FormularioRepository formularioRepository;

    public FormularioService(FormularioRepository formularioRepository){
        this.formularioRepository = formularioRepository;
    }

    public List<String> obterPerguntasDoFormulario() {
        return this.formularioRepository.lerPerguntas();
    }

    public List<String> obterPerguntasDoFormularioEndereco() {
        return this.formularioRepository.lerPerguntasEndereco();
    }
}
