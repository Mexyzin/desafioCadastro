
# 🐾 Desafio Cadastro de Pets

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![CLI](https://img.shields.io/badge/Application-CLI-blue?style=for-the-badge)

---

# 📌 Sobre o Projeto

Este projeto é uma implementação do desafio de programação proposto por **Lucas Carrilho**, cujo objetivo é desenvolver um sistema de **cadastro e gerenciamento de pets para adoção** utilizando **Java puro**.

A aplicação funciona em **linha de comando (CLI)** e permite cadastrar, listar, buscar, atualizar e remover animais registrados em um abrigo.

Nesta versão do projeto foi realizada uma **refatoração da estrutura**, reorganizando os pacotes e separando responsabilidades para melhorar **manutenibilidade, legibilidade e organização do código**.

---

# Funcionalidades

O sistema permite:

- 🐶 **Cadastrar pets**
- 📋 **Listar todos os pets cadastrados**
- 🔎 **Buscar pets utilizando filtros**
- ✏️ **Atualizar dados de um pet**
- ❌ **Remover pets do sistema**
- 💾 **Persistir os dados em arquivos locais**

Os registros são armazenados em arquivos `.txt`, garantindo persistência mesmo após o encerramento da aplicação.

---

# Conceitos Aplicados

Este projeto foi desenvolvido com foco em boas práticas de programação e arquitetura:

- **Programação Orientada a Objetos (POO)**
- **Separação de responsabilidades**
- **Arquitetura em camadas**
- **Refatoração e organização de estrutura de projeto**
- **Manipulação de arquivos com Java (I/O / NIO)**
- **Validação de dados de entrada**
- **Tratamento de exceções**

---

# Estrutura do Projeto

Após a refatoração, o projeto foi organizado da seguinte forma:

```

src
├── exception        # Exceções customizadas
├── model            # Entidades do domínio (Pet, Endereço, etc)
├── repository       # Acesso e manipulação de arquivos
├── service          # Regras de negócio
├── ui               # Interação com o usuário (menus e CLI)
└── Main.java        # Ponto de entrada da aplicação
````

---

# Tecnologias Utilizadas

- **Java 21**
- **Java IO / NIO**
- **CLI (Command Line Interface)**
- **Git**
- **GitHub**

---

# Como Executar o Projeto

## Pré-requisitos

- **Java JDK 21 ou superior**
- Uma IDE Java:
  - IntelliJ
  - Eclipse
  - VS Code

---

# Clonar o Repositório

```bash
git clone https://github.com/Mexyzin/desafioCadastro.git
````

Entre na pasta do projeto:

```bash
cd desafioCadastro
```

---

# 💻 Configuração nas IDEs

- **Como este projeto foi desenvolvido utilizando apenas Java puro, sem gerenciadores de dependência como Maven ou Gradle, é necessário configurar manualmente na IDE qual é a pasta `resources`. Para facilitar esse processo, incluí abaixo instruções para configurar o projeto nas principais IDEs e editores de código utilizados para desenvolvimento em Java.**

---

## 🔵 IntelliJ IDEA



1. No painel lateral esquerdo (**Project Explorer**), abra a pasta **main**.
2. Clique com o botão direito sobre a pasta **resources**.
3. Vá em:

```
Mark Directory as → Resources Root
```

4. Agora execute a classe:

```
Main.java
```

---

## 🟣 Eclipse

1. No **Package Explorer**, abra a pasta do projeto.
2. Clique com o botão direito sobre a pasta **resources**.
3. Vá em:

```
Build Path → Use as Source Folder
```

4. Execute a classe:

```
Main.java
```

---

## 🟦 Visual Studio Code

Certifique-se de ter instalado:

**Extension Pack for Java**


Crie ou abra o arquivo:

```
.vscode/settings.json
```

Adicione:

```json
{
    "java.project.sourcePaths": [
        "main/src",
        "main/resources"
    ]
}
```

Salve o arquivo e execute a classe:

```
Main.java
```

---

# Desafio Original

Este projeto foi desenvolvido como solução para o desafio criado por **Lucas Carrilho**.

O objetivo do desafio é praticar:

* lógica de programação
* orientação a objetos
* organização de código
* manipulação de arquivos

---

# 👤 Autor

**Mexysx**

GitHub:
[https://github.com/Mexyzin](https://github.com/Mexyzin)

---

# ⭐ Contribuição

Este projeto foi desenvolvido para **fins de estudo**.

Sugestões de melhoria são sempre bem-vindas.

