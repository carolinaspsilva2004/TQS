# Biblioteca - Testes BDD com Cucumber

## Introdução
Este projeto implementa uma Biblioteca com capacidade de armazenar e pesquisar livros, utilizando testes BDD (Behavior-Driven Development) com Cucumber para validar funcionalidades.

## O que é BDD com Cucumber?
BDD (Behavior-Driven Development) é uma metodologia de desenvolvimento que foca na descrição do comportamento do sistema usando uma linguagem simples e compreensível para todas as partes interessadas. O Cucumber é uma ferramenta que permite escrever esses testes de forma estruturada, utilizando a linguagem Gherkin para definir cenários de teste em um formato legível.

Os testes BDD são especialmente úteis em projetos onde a comunicação entre desenvolvedores, testadores e clientes é essencial, garantindo que todos tenham uma compreensão clara dos requisitos e do comportamento esperado do sistema.

## Funcionalidades Implementadas
O projeto suporta as seguintes funcionalidades de busca:
- **Busca por autor:** Permite encontrar livros de um autor específico.
- **Busca por intervalo de datas:** Permite encontrar livros publicados dentro de um período definido pelo usuário.
- **Uso de DataTable:** Para simular uma base de dados de livros e facilitar os testes.

## O que são Expressões Cucumber?
Expressões Cucumber são uma forma moderna e simplificada de definir padrões de correspondência nos passos do Cucumber. Elas permitem capturar valores dinâmicos sem precisar usar expressões regulares complexas. Exemplo:

- **Forma antiga (expressões regulares):**
  ```java
  @When("^o usuário busca por livros do autor (.+)$")
  ```
- **Forma nova (Expressão Cucumber):**
  ```java
  @When("o usuário busca por livros do autor {string}")
  ```

## O que é um DataTable no Cucumber?
O DataTable é um recurso do Cucumber que permite definir tabelas de dados diretamente nos cenários Gherkin. Isto facilita a entrada de informações estruturadas para os testes. No meu projeto, utilizaei um DataTable para armazenar uma lista de livros fictícios.

Exemplo de DataTable em um cenário Gherkin:
```gherkin
Given a seguinte coleção de livros:
  | Título         | Autor        | Publicado   |
  | Livro A        | Autor X      | 2020-01-01  |
  | Livro B        | Autor Y      | 2021-05-10  |
  | Livro C        | Autor X      | 2019-07-15  |
```

## Implementação das funcionalidades

### Definição dos Cenários (Arquivo .feature)
Os cenários de teste são definidos no arquivo Gherkin. Exemplo de procura por autor:
```gherkin
Scenario: Procurar livros por autor
  Given a seguinte coleção de livros:
    | Título         | Autor        | Publicado   |
    | Livro A        | Autor X      | 2020-01-01  |
    | Livro B        | Autor Y      | 2021-05-10  |
  When o utilizador procura por livros do autor "Autor X"
  Then os seguintes livros são retornados:
    | Título  |
    | Livro A |
```

### Uso de ParameterType para manipulação de datas
Para lidar com datas no formato ISO 8601 ("aaaa-MM-dd"), configuramos um `ParameterType` personalizado:
```java
@ParameterType("\\d{4}-\\d{2}-\\d{2}")
public LocalDate iso8601Date(String date) {
    return LocalDate.parse(date);
}
```
Isto permite utilizar datas nos cenários de forma natural, por exemplo:
```gherkin
When o utilizador busca por livros publicados entre "2020-01-01" e "2021-12-31"
```

## Conclusão
Este projeto demonstra como utilizar BDD com Cucumber para validar a funcionalidade de uma biblioteca. Explorei a procura de livros por autor e por intervalo de datas, através do uso expressões Cucumber para simplificar os testes, e utilizei DataTable para manipulação de dados estruturados. Estes conceitos tornam os testes mais legíveis, reutilizáveis e fáceis de manter.

