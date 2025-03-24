# CarsService - Explicação das Alterações e Melhorias

## Introdução
Neste projeto, decidi melhorar a estrutura de testes do meu controlador REST (`CarController`) utilizando o **REST Assured** em conjunto com o ambiente **MockMvc**. Estas alterações visaram não só reforçar a qualidade dos testes, como também garantir que estou a testar de forma isolada e eficiente o comportamento do controlador, sem depender do ambiente completo.

## O que alterei

### 1. Implementação de testes com RestAssuredMockMvc
Anteriormente, estava a utilizar apenas o MockMvc com chamadas diretas e verificações simples.
Agora, passei a utilizar a biblioteca **REST Assured** na variante **RestAssuredMockMvc**, que me permite escrever testes muito mais legíveis e expressivos, simulando requisições REST num ambiente de MockMvc.

### 2. Anotação @WebMvcTest
Utilizei `@WebMvcTest(CarController.class)` para garantir que apenas o contexto web referente ao `CarController` é carregado, sem carregar o contexto completo da aplicação. Isto torna os testes mais rápidos e específicos.

### 3. Mock do serviço CarManagerService
Incluí um mock do serviço `CarManagerService` através de `@MockBean`. Desta forma, consigo isolar completamente o controlador e garantir que o comportamento que estou a testar não depende da lógica do serviço, mas sim da forma como o controller reage às respostas do serviço.

### 4. Estrutura de testes
Implementei três testes principais:
- **Teste de obtenção de todos os carros**: Valido se a resposta contém a lista correta e no formato esperado.
- **Teste de obtenção de um carro por ID válido**: Garante que um pedido com um ID válido retorna o carro correto.
- **Teste de obtenção de um carro por ID inválido**: Testa o comportamento do sistema quando o carro não existe (devolvendo 404).

## Diferenças em relação à versão anterior
- Antes, o foco estava em testes mais tradicionais com MockMvc, sem uma sintaxe fluída e legível.
- Agora, os testes utilizam o poder do REST Assured para criar um fluxo mais natural de leitura: `given()...when()...then()`.
- Os testes passaram a estar totalmente desacoplados do serviço e da base de dados, garantindo maior rapidez e foco no comportamento do controller.
- A verificação do JSON de resposta tornou-se mais robusta, utilizando o `hasSize()` e `equalTo()` do REST Assured para validar campos específicos.

## Melhorias
- **Legibilidade:** A escrita dos testes tornou-se muito mais limpa e fácil de compreender.
- **Velocidade:** Como utilizo apenas o contexto web parcial com mocks, os testes correm de forma significativamente mais rápida.
- **Manutenção:** Com esta abordagem, fica mais simples adicionar novos casos de teste, alterá-los ou diagnosticar falhas.
- **Cobertura:** A utilização do mock permite testar comportamentos de erro (404), que seriam difíceis de simular num ambiente sem mocks.

## Propósito das novas implementações
- Garantir a validação do comportamento do controlador sem dependências externas.
- Obter feedback rápido e fiável sobre a integridade das rotas expostas pelo controlador.
- Facilitar a manutenção do código e permitir alterações futuras no serviço ou na base de dados sem impacto nos testes do controller.

## Sobre o `pom.xml`
Incluí no `pom.xml` a dependência `io.rest-assured:spring-mock-mvc` para poder usar o REST Assured em conjunto com MockMvc. Esta dependência é fundamental para suportar a sintaxe fluída e expressiva que utilizei nos testes.

Além disso, mantive as dependências já existentes de `spring-boot-starter-test`, `mockito-core`, `h2` e `spring-boot-starter-data-jpa`, que garantem a base de testes, mocking e persistência em memória.

## Conclusão
Com estas alterações, o meu projeto passou a ter uma camada de testes muito mais robusta, clara e eficiente. Os controladores estão agora bem validados, os testes são rápidos e fáceis de interpretar, e a abordagem permite-me confiar na qualidade do código antes de o integrar ou disponibilizar. Esta foi uma melhoria que trouxe profissionalismo e segurança ao meu processo de desenvolvimento.

