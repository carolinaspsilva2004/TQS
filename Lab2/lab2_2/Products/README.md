# ProductFinderService - README

## Descrição do Projeto

Este projeto simula o comportamento de um serviço (`ProductFinderService`) que consome uma API de produtos (https://fakestoreapi.com/products/) e retorna informações detalhadas sobre produtos específicos, com base no ID do produto. A implementação adota uma abordagem **Test-Driven Development (TDD)**, com o foco na implementação de testes que garantem o funcionamento correto do método `findProductDetails(id)`.

## Funcionalidade

O serviço `ProductFinderService` usa um cliente HTTP para buscar dados da API e processar as informações recebidas, transformando os dados JSON em objetos Java. O comportamento da API é simulado em testes com o uso de **mocking** para isolar o código da dependência externa da API, garantindo que os testes sejam rápidos e independentes de chamadas reais à rede.

### Método principal:
- `findProductDetails(int id)`: Este método recebe um ID de produto e retorna um objeto `Product` representando os detalhes do produto, ou um `Optional.empty()` se o produto não for encontrado ou se ocorrer algum erro.

## Estrutura do Projeto

O projeto é dividido nas seguintes classes e interfaces:

### Classes:
- **Product**: Representa um produto com atributos como `id`, `title`, `price`, `description`, `category` e `image`. A classe utiliza a anotação `@Data` do Lombok para gerar automaticamente os métodos `getters`, `setters`, `toString()`, `equals()` e `hashCode()`.
- **ProductFinderService**: Responsável por fazer a requisição HTTP à API de produtos e converter a resposta JSON em um objeto `Product`. Caso a resposta seja inválida ou uma exceção seja lançada, retorna um `Optional.empty()`.
- **ISimpleHttpClient**: Interface para o cliente HTTP. Neste projeto, o cliente HTTP é simulado com o uso de mocks durante os testes.

**Como o Mocking seria Usado neste Cenário**

O uso de *mocking* é essencial neste caso porque:

1. **A implementação do cliente HTTP não foi decidida**: Em vez de fazer chamadas reais à API (o que exigiria escolher uma biblioteca como OkHttp ou HttpClient), nós fazemos *mock* do cliente HTTP.
2. **As chamadas à API têm um custo**: Fazer requisições reais à API toda vez que executamos os testes seria ineficiente e lento. O *mocking* nos permite simular as respostas da API sem realizar requisições reais.
3. **Testando a criação de objetos**: O objetivo do método `findProductDetails(id)` é pegar um ID de produto, fazer uma chamada HTTP e retornar um objeto `Product`. Precisamos verificar se a resposta JSON é corretamente convertida em um objeto `Product`.

### Dependências:
- **Lombok**: Para facilitar a geração de métodos automaticamente na classe `Product`.
- **JUnit** e **Mockito**: Usados para implementar testes unitários com mocks.
- **Jackson Databind**: Para a conversão de JSON em objetos Java (POJOs).
- **Jacoco**: Para medição de cobertura de código.

### Dependências Maven:
- JUnit 5 para testes unitários
- Mockito para criação de mocks
- Lombok para simplificação de código
- Jackson para conversão JSON para objetos
- Jacoco para análise de cobertura de código

### Configuração Maven:

```xml
<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>${logback.version}</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>${mockito-junit-jupiter.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
</dependencies>
```

## Testes Implementados

### 1. Teste de Produto Válido
- **Objetivo**: Verificar se, ao passar um ID de produto válido (ID = 3), o método `findProductDetails(id)` retorna um objeto `Product` correto, com os valores de `id` e `title` esperados.
- **Cenário Simulado**: A resposta da API é um JSON contendo os dados do produto com o ID 3.

### 2. Teste de Produto Não Encontrado
- **Objetivo**: Verificar se, ao passar um ID de produto inexistente (ID = 300), o método retorna `Optional.empty()`.
- **Cenário Simulado**: A resposta da API é `null`, o que simula a não existência do produto.

### 3. Teste de Exceção
- **Objetivo**: Testar o comportamento do método `findProductDetails(id)` quando ocorre uma exceção ao tentar fazer a requisição HTTP.
- **Cenário Simulado**: Uma exceção é lançada pelo método `doHttpGet`.

### 4. Teste de Resposta Vazia
- **Objetivo**: Testar o caso em que a API retorna uma resposta vazia.
- **Cenário Simulado**: A resposta da API é uma string vazia.

## Cobertura de Código

A cobertura de código é monitorada pelo **JaCoCo** e cobre todos os ramos de decisão no método `findProductDetails`, incluindo os casos de sucesso, falha e exceção. A partir dos testes implementados, o código está completamente coberto.

## Conclusão

Neste projeto, a abordagem TDD foi seguida para garantir a robustez da funcionalidade de busca de produtos. Os testes garantem que o código se comporta corretamente em diferentes cenários (produto válido, não encontrado, erro de rede e resposta vazia). A utilização de mocks (Mockito) permitiu isolar o código de dependências externas, mantendo a agilidade no processo de testes.

