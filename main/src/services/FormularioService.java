package services;

import repository.FormularioRepository;

import java.util.List;

public class FormularioService {

    private final FormularioRepository repository = new FormularioRepository();

    public List<String> obterPerguntasDoFormulario() {
        return repository.lerPerguntas();
    }

    public List<String> obterPerguntasDoFormularioEndereco() {
        return repository.lerPerguntasEndereco();
    }
}
