### f) Explicação das Diferenças entre os Comandos Maven

1. **`$ mvn test`**:
   - **Objetivo**: Executa os testes unitários.
   - **O que faz**: Este comando executa os testes unitários definidos na sua aplicação. Ele compilará o código, executará todos os testes que estão na pasta `src/test/java`, e irá verificar a integridade das funcionalidades em isolamento. Os testes são executados sem a necessidade de uma conexão externa, a não ser que o código de teste dependa disso.
   - **Quando usar**: Use quando quiser verificar se os testes unitários estão funcionando corretamente sem se preocupar com a integração com sistemas externos.

2. **`$ mvn package`**:
   - **Objetivo**: Compila o código e empacota o aplicativo.
   - **O que faz**: Este comando compila o código, executa os testes unitários e empacota o código em um arquivo JAR ou WAR (dependendo do tipo de projeto). O objetivo é gerar o artefato que será distribuído ou implantado.
   - **Quando usar**: Use quando quiser apenas empacotar a aplicação sem executar os testes de integração.

3. **`$ mvn package -DskipTests=true`**:
   - **Objetivo**: Empacota a aplicação sem executar os testes.
   - **O que faz**: Este comando é semelhante ao `mvn package`, mas a diferença é que ele *pula* a execução dos testes. Ele compilará o código, mas não executará os testes, tornando o processo mais rápido. O código é empacotado, mas os testes não são verificados.
   - **Quando usar**: Use quando não precisar executar os testes (por exemplo, em um ambiente de desenvolvimento ou quando os testes já foram executados anteriormente).

4. **`$ mvn failsafe:integration-test`**:
   - **Objetivo**: Executa os testes de integração.
   - **O que faz**: Este comando executa os testes de integração definidos no seu projeto. Ele procura por classes de teste que terminam com `IT` ou `ITCase` e executa os testes que fazem chamadas reais a serviços externos (como APIs), garantindo que a integração com sistemas remotos (como a Fake Store API) funcione corretamente.
   - **Quando usar**: Use quando quiser testar a integração do sistema com componentes externos ou recursos de rede, como APIs.

5. **`$ mvn install`**:
   - **Objetivo**: Compila o código, executa os testes e instala o artefato no repositório local.
   - **O que faz**: Este comando é mais abrangente. Ele compila o código, executa os testes unitários e de integração, empacota a aplicação e instala o artefato no repositório local Maven. O artefato ficará disponível para ser utilizado por outras dependências em projetos locais.
   - **Quando usar**: Use quando quiser garantir que a versão mais recente do artefato esteja disponível no repositório local para ser usada em outros projetos ou sistemas.

### Resumo do Comportamento:

- **Com `mvn test`**: Executa apenas os testes unitários.
- **Com `mvn package`**: Compila e empacota o código, mas sem rodar os testes de integração.
- **Com `mvn package -DskipTests=true`**: Compila e empacota o código sem rodar nenhum teste.
- **Com `mvn failsafe:integration-test`**: Executa os testes de integração, com foco em sistemas externos (como APIs).
- **Com `mvn install`**: Compila, testa e instala o artefato no repositório local para uso posterior.


# Projeto de Testes de Integração com API Remota

Este projeto tem como objetivo testar a integração com uma API remota (Fake Store API) usando testes de integração, validando a obtenção de detalhes dos produtos. A aplicação utiliza o cliente HTTP real (`TqsBasicHttpClient`) para realizar chamadas reais a um serviço remoto.

## Funcionalidades Implementadas

- **Classe `Product`**: Representa a estrutura de dados de um produto, incluindo propriedades como `id`, `title`, `price`, `description`, `category`, e `image`.
- **Classe `ProductFinderService`**: Serviço que interage com a API externa para recuperar os detalhes de um produto específico.
- **Classe `TqsBasicHttpClient`**: Implementação do cliente HTTP real utilizando a biblioteca Apache HttpClient, responsável por realizar as requisições GET para a API remota.
- **Testes de Integração**: Realiza testes para validar que a integração com a API está funcionando corretamente. Utiliza o cliente real para enviar requisições e verificar as respostas.

## Estrutura do Projeto

- `src/main/java/tqs/mockforhttpclient/`: Contém as classes de implementação.
- `src/test/java/tqs/mockforhttpclient/`: Contém os testes.
  - `ProductFinderServiceIT.java`: Testes de integração utilizando a API real. Testa funcionalidades como recuperação de produto válido, produto não encontrado e resposta vazia.

## Como Executar o Projeto

1. **Executar os Testes de Integração**:
   Para garantir que os testes de integração estão funcionando corretamente, execute o comando abaixo:
   ```bash
   mvn failsafe:integration-test
   ```
   Este comando irá rodar os testes de integração e validar a comunicação com a API remota.
2. **Executar os Testes Unitários**:
   Para executar apenas os testes unitários (sem interagir com a API remota), use:
   ```bash
   mvn test
   ```

3. **Compilar e Empacotar o Código**:
   Para compilar e empacotar o código em um arquivo JAR, use:
   ```bash
   mvn package
   ```

4. **Compilar e Instalar no Repositório Local**:
   Para compilar, testar e instalar o artefato no repositório local Maven:
   ```bash
   mvn install
   ```

## Considerações Importantes

- **Dependência da Internet**: Os testes de integração dependem de uma conexão de internet ativa, pois as requisições são feitas a uma API remota.
- **Execução de Testes**: Se a conexão com a internet for perdida, os testes falharão ao tentar acessar a API.

## Observações Finais

Este projeto demonstra como criar testes de integração utilizando um cliente HTTP real e como configurar o Maven para executar esses testes de maneira adequada. Ele valida a capacidade de interação com sistemas externos, como APIs remotas, utilizando o Maven para automatizar a execução dos testes e a construção do artefato.

