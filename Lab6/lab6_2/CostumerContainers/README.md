## 📚 README - Projeto CostumerContainers  

### ✅ Descrição do projeto
O objetivo principal foi criar uma aplicação Spring Boot com persistência de dados utilizando uma base de dados PostgreSQL, e garantir o correto funcionamento das migrações de base de dados através do Flyway e o uso de Testcontainers para testes automatizados.  

### ✅ Implementações realizadas

#### 1️⃣ **Criação de uma aplicação Spring Boot**
Implementei uma aplicação básica em Spring Boot com uma entidade `Customer` e um repositório JPA correspondente. Esta estrutura serviu para poder armazenar e recuperar dados de clientes numa base de dados PostgreSQL.  

#### 2️⃣ **Configuração do Flyway**
Configurei o Flyway para gerir as migrações da base de dados.  
Criei o ficheiro `V001__INIT.sql` no caminho `src/test/resources/db/migration`, onde defini o esquema da base de dados e adicionei dados de teste, através de comandos `CREATE TABLE` e `INSERT`.  
Desta forma, cada vez que a aplicação ou os testes arrancam, a base de dados é criada e populada automaticamente de forma limpa e previsível.  

#### 3️⃣ **Utilização de Testcontainers**
Implementei testes de integração com recurso ao Testcontainers, o que me permitiu criar uma base de dados PostgreSQL temporária, totalmente isolada, para executar os testes sem necessidade de usar um servidor de base de dados fixo.  
Utilizei a anotação `@Testcontainers` e defini um container PostgreSQL que arranca e termina automaticamente nos testes.  
A configuração dinâmica (`@DynamicPropertySource`) permitiu injetar as propriedades da ligação à base de dados no contexto da aplicação.  

#### 4️⃣ **Criação de um teste de verificação do Flyway**
Implementei uma classe de teste chamada `FlywayMigrationTest`, onde verifiquei se, após a execução do Flyway, a tabela `Customer` estava criada e continha exatamente os dados de exemplo definidos na migração inicial.  
Este teste garantiu que o mecanismo de migração funciona corretamente e que os dados iniciais são carregados como esperado.  

#### 5️⃣ **Configuração do `application.properties`**
Adaptei o ficheiro `application.properties` para remover a criação dinâmica automática (`jdbc:tc`) e passei a injetar dinamicamente a ligação ao container definido nos testes.  
Além disso, desativei a criação automática de tabelas (`spring.jpa.hibernate.ddl-auto=none`), permitindo que o Flyway fosse o responsável exclusivo pela criação e gestão do esquema da base de dados.  

---

### ✅ Objetivos do projeto
- Garantir que consigo gerir o esquema da base de dados de forma automática e controlada usando Flyway.  
- Validar que a criação de tabelas e inserção de dados funcionam corretamente através de testes automatizados.  
- Aprender a utilizar Testcontainers para criar ambientes isolados e reproduzíveis durante a execução dos testes.  
- Assegurar que o projeto é escalável, prático e não depende de configurações manuais no ambiente local.  
