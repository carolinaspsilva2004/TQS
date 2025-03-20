# Projeto TestCostumerContainers

Este projeto visa demonstrar como realizar testes de integração utilizando o Spring Boot e o Testcontainers, especificamente com um base de dados PostgreSQL. O código é estruturado para armazenar e manipular informações de clientes e realizar testes de inserção, atualização e recuperação de dados.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento de aplicações Java.
- **JPA (Jakarta Persistence API)**: Para a persistência de dados no base de dados.
- **PostgreSQL**: Sistema de gerenciamento de base de dados relacional utilizado.
- **Testcontainers**: Biblioteca que permite a execução de containers Docker durante os testes de integração.
- **JUnit**: Framework para a execução de testes unitários e de integração.
- **Flyway**: Ferramenta para controle de versões de base de dados.

## Estrutura do Projeto

1. **Classe `Customer`**: Define a entidade cliente, que será armazenada no base de dados. Esta classe tem os atributos `id`, `name` e `email`, além dos respectivos getters e setters.

2. **Interface `CustomerRepository`**: Interface que estende `JpaRepository` e permite a manipulação de dados da entidade `Customer` no base de dados.

3. **Classe de Teste `CustomerRepositoryTest`**: Contém testes para verificar a inserção e recuperação de dados da tabela `Customer`. A principal funcionalidade dos testes é garantir que os dados inseridos sejam corretamente recuperados e que as atualizações funcionem como esperado.

4. **Classe de Teste `CustomerRepositoryOrderedTest`**: Um conjunto de testes semelhantes, mas com a adição de ordenação para garantir que as operações de teste ocorram numa sequência específica.

5. **Classe de Configuração `TestcontainersConfiguration`**: Configura a criação e gerenciamento do container PostgreSQL durante a execução dos testes. O container é criado a partir da imagem oficial do PostgreSQL no Docker.

6. **Classe `TestCostumerContainersApplication`**: A classe principal que configura a aplicação e executa os testes de integração.

## Como Utilizar o Projeto

Para utilizar o projeto, siga os passos abaixo:

1. **Compilar o projeto**:
   Caso esteja utilizando o Maven:
   ```bash
   mvn clean install
   ```

2. **Executar os testes**:
   Para executar os testes, basta rodar o seguinte comando:
   ```bash
   mvn test
   ```
   Isso iniciará a execução dos testes, que irão interagir com a base de dados PostgreSQL criado no Docker.

## O que Aprendi

Com este projeto, aprendi a integrar o Spring Boot com o Testcontainers para criar testes de integração eficazes, utilizando uma base de dados real (PostgreSQL) em vez de uma base de dados em memória. Além disso, aprendi como configurar o Spring Data JPA e como utilizar o Flyway para gerenciar migrações de base de dados, além de criar testes ordenados e garantir que o base de dados seja configurado corretamente para os testes.

Outro ponto importante foi a utilização do Testcontainers para executar o PostgreSQL de forma isolada para os testes, sem necessidade de configuração manual de um base de dados local ou remoto.

## Funcionalidade de Cada Componente

- **Classe `Customer`**: Representa a entidade de cliente que será salva na base de dados.
- **`CustomerRepository`**: Interface que fornece métodos para interagir com a base de dados, como salvar e recuperar dados.
- **`TestcontainersConfiguration`**: Configura o container do PostgreSQL para ser usado durante os testes.
- **`CustomerRepositoryTest` e `CustomerRepositoryOrderedTest`**: Contêm testes para garantir que a persistência e a recuperação de dados funcionam como esperado.

## Como os Testes Funcionam

1. **Inserção de um Cliente**: Num dos testes, um novo cliente é salvo na base de dados. Isso garante que o processo de inserção está funcionando.
2. **Recuperação do Cliente**: Noutro teste, é verificado se o cliente inserido está disponível para leitura do banco de dados.
3. **Atualização de um Cliente**: Testa-se também a capacidade de atualizar os dados de um cliente na base de dados, garantindo que as alterações são persistidas.
4. **Execução Sequencial de Testes**: O uso de `@Order` nos testes permite que os testes sejam executados numa sequência específica, garantindo que as dependências entre as operações sejam respeitadas.

## Dependências

As dependências mais importantes utilizadas no projeto são:

- `spring-boot-starter-data-jpa`: Para a integração com o JPA e persistência de dados.
- `spring-boot-starter-test`: Para a execução de testes no Spring Boot.
- `testcontainers`: Para rodar containers de PostgreSQL durante os testes.
- `postgresql`: Driver JDBC para conectar à base de dados PostgreSQL.

## Conclusão

Este projeto é uma demonstração de como realizar testes de integração eficazes utilizando o Spring Boot e o Testcontainers. Este nos permite garantir que a aplicação funcione corretamente ao interagir com um banco de dados real em um ambiente controlado e isolado durante os testes.