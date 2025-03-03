### Explicação resumida do projeto e resposta das perguntas relativas ao ex.3.1

### a) Identify a couple of examples that use AssertJ expressive methods chaining

O AssertJ permite um encadeamento fluente de métodos para tornar os testes mais legíveis. No código fornecido, há vários exemplos de uso dessa abordagem. Alguns deles incluem:

1. **Teste no repositório (`A_EmployeeRepositoryTest`)**:
   ```java
   assertThat(found).isNotNull()
       .extracting(Employee::getName).isEqualTo(persistedAlex.getName());
   ```
   - Verifica que o objeto `found` não é nulo.
   - Extrai o nome do objeto `Employee` e verifica se é igual ao nome do empregado persistido.

2. **Teste de busca por domínio de email**:
   ```java
   assertThat(results)
       .hasSize(2)
       .extracting(Employee::getEmail)
       .containsExactlyInAnyOrder(
           "bob@ua.pt",
           "ron@ua.pt"
       );
   ```
   - Garante que há exatamente dois resultados.
   - Extrai os e-mails e verifica se correspondem aos esperados, independentemente da ordem.

3. **Teste no serviço (`B_EmployeeService_UnitTest`)**:
   ```java
   assertThat(found.getName()).isEqualTo(name);
   ```
   - Verifica se o nome do funcionário encontrado corresponde ao nome esperado.

4. **Teste no controlador (`C_EmployeeController_WithMockServiceTest`)**:
   ```java
   assertThat(allEmployees)
       .hasSize(3)
       .extracting(Employee::getName)
       .contains(alex.getName(), john.getName(), bob.getName());
   ```
   - Garante que a lista de funcionários contém três elementos.
   - Extrai os nomes e verifica se os funcionários corretos estão presentes na lista.

---

### b) Take note of transitive annotations included in @DataJpaTest

A anotação `@DataJpaTest` é usada para testar a camada de persistência da aplicação, configurando um contexto de teste específico para JPA. Ela inclui algumas anotações transitivas que facilitam os testes, como:

1. **`@BootstrapWith(SpringBootTestContextBootstrapper.class)`**  
   - Inicializa um contexto de teste para o Spring Boot.

2. **`@ExtendWith(SpringExtension.class)`**  
   - Integra o Spring com o JUnit 5.

3. **`@Transactional`**  
   - Faz com que cada teste seja executado dentro de uma transação, que é revertida ao final do teste para garantir uma base de dados limpa.

4. **`@AutoConfigureTestDatabase`**  
   - Configura uma base de dados em memória para os testes, como o H2, a menos que seja substituído por configurações específicas.

5. **`@ImportAutoConfiguration`**  
   - Carrega automaticamente configurações relevantes para a JPA.

Essas anotações garantem que os testes de repositório sejam executados de maneira isolada, sem afetar uma base de dados real.


Aqui estão as respostas para as perguntas (c) e (d):  

---

### c) Identify an example in which you mock the behavior of the repository (and avoid involving a database). 

No código de testes, um exemplo claro de mock do repositório ocorre no teste da camada de serviço (`B_EmployeeService_UnitTest`). Aqui, o repositório é substituído por um mock para evitar interações reais com a base de dados:  

```java
@ExtendWith(MockitoExtension.class)
class B_EmployeeService_UnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void whenValidName_thenEmployeeShouldBeFound() {
        String name = "Alex";
        Employee alex = new Employee(name, "alex@ua.pt");

        // Simula o comportamento do repositório sem acessar a base de dados
        when(employeeRepository.findByName(name)).thenReturn(Optional.of(alex));

        // Chama o serviço
        Employee found = employeeService.getEmployeeByName(name);

        // Verifica se o serviço retorna o objeto esperado
        assertThat(found.getName()).isEqualTo(name);
    }
}
```

#### **Explicação**:  
- **`@Mock`**: Cria um mock do `EmployeeRepository`, impedindo que ele faça consultas reais na base de dados.  
- **`@InjectMocks`**: Injeta o mock dentro da instância de `EmployeeService`.  
- **`when(...).thenReturn(...)`**: Define o comportamento simulado do repositório: sempre que `findByName("Alex")` for chamado, ele retornará um objeto `Employee` pré-definido.  
- **O banco de dados nunca é acessado**, tornando o teste mais rápido e isolado.  

---

### **d) What is the difference between standard @Mock and @MockBean?

| Anotação  | Contexto de Uso | Objetivo | Como Funciona |
|-----------|----------------|----------|---------------|
| `@Mock`  | Usado em testes unitários | Cria um objeto simulado (mock) que não interage com o Spring | Utilizado com `MockitoExtension`, requer `@InjectMocks` para injeção manual |
| `@MockBean` | Usado em testes de integração (com Spring Boot) | Substitui um bean real no contexto do Spring por um mock | Permite que o mock seja injetado automaticamente em componentes do Spring |

#### **Exemplo de `@MockBean` em um teste com Spring Boot**:  
No teste do controlador `C_EmployeeController_WithMockServiceTest`, `@MockBean` é utilizado para substituir a instância real do `EmployeeService` dentro do contexto do Spring:  

```java
@WebMvcTest(EmployeeController.class)
class C_EmployeeController_WithMockServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
        Employee alex = new Employee("Alex", "alex@ua.pt");

        List<Employee> allEmployees = List.of(alex);

        // Define o comportamento do mock dentro do contexto Spring
        when(employeeService.getAllEmployees()).thenReturn(allEmployees);

        mockMvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value(alex.getName()));
    }
}
```

#### **Resumo da diferença**:
- **`@Mock`**: Para testes **unitários**, sem contexto do Spring. Usado junto com `@InjectMocks`.
- **`@MockBean`**: Para testes **de integração**, substituindo beans reais dentro do contexto do Spring.



Aqui estão as respostas para as questões (e) e (f):  

---

### e) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

O arquivo `application-integrationtest.properties` define configurações específicas para testes de integração. Este será usado sempre que os testes forem executados em um **ambiente de teste**, geralmente com a anotação `@TestPropertySource(locations = "classpath:application-integrationtest.properties")` nos testes de integração.  

#### **Função das configurações definidas no arquivo**  
```properties
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.username=demo
spring.datasource.password=demo
```
- **`spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo`**  
  - Define a URL do banco de dados MySQL a ser usada nos testes.  
  - A base de dados roda na porta `33060` em vez da padrão `3306`.  
- **`spring.jpa.hibernate.ddl-auto=create-drop`**  
  - Faz com que o Hibernate crie o esquema da base de dados no início dos testes e a remova ao final.  
  - Garante que os testes sempre rodem em numa base de dados limpa.  
- **`spring.datasource.username=demo` e `spring.datasource.password=demo`**  
  - Define as credenciais de acesso à base de dados.  

#### **Quando ele será usado?**  
- Este arquivo será usado **somente nos testes de integração** que requerem acesso à base de dados real.  
- No código, é provável que testes que utilizam `@SpringBootTest` carreguem automaticamente essas configurações.  
- A base de dados é inicializada usando um container Docker:  
  ```bash
  docker run --name mysql5tqs -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tqsdemo -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -p 33060:3306 -d mysql/mysql-server:5.7
  ```
  - Esse comando cria uma base de dados MySQL em um container Docker, que é usado pelos testes de integração.  

---

### **f) the sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. 
### Which are the main/key differences?

O projeto usa três estratégias de teste para avaliar a API Spring Boot:  

| Teste | Estratégia | Tecnologias principais | O que testa? | Mock do Serviço? | Base de Dados? |
|-------|-----------|-----------------------|--------------|----------------|----------------|
| **C - Controller com Mock Service** | Teste unitário do controlador | `@WebMvcTest` + `MockMvc` + `@MockBean` | Apenas o controlador | Sim (`@MockBean`) | Não |
| **D - Controller com Embedded DB** | Teste de integração com Spring Boot | `@SpringBootTest` + Base de dados em memória (`H2`) | Fluxo real da API | Não | Sim (H2) |
| **E - API End-to-End com DB real** | Teste de integração completo | `@SpringBootTest` + Base de dados MySQL Docker | API completa com a base de dados real | Não | Sim (MySQL Docker) |

---

#### **C - Teste de Controlador com Mock do Serviço (`C_EmployeeController_WithMockServiceTest`)**  
- **Foca apenas no controlador (`EmployeeController`)**.  
- Utiliza `@WebMvcTest(EmployeeController.class)`, que carrega apenas o contexto da web.  
- Simula a camada de serviço usando `@MockBean EmployeeService`.  
- **Vantagem**: Rápido, pois não carrega o contexto completo do Spring.  
- **Limitação**: Não testa a integração real entre o controlador e o serviço.  

```java
@WebMvcTest(EmployeeController.class)
class C_EmployeeController_WithMockServiceTest {
    @MockBean
    private EmployeeService employeeService;
}
```

---

#### **D - Teste de Integração com Banco de Dados em Memória (`D_EmployeeController_EmbeddedDbTest`)**  
- **Testa a API com um banco de dados real, mas em memória (`H2`)**.  
- Utiliza `@SpringBootTest`, que carrega todo o contexto do Spring.  
- Permite testar **requisições HTTP reais** e a **persistência dos dados**.  
- **Vantagem**: Testa a integração real da API sem exigir um banco externo.  
- **Limitação**: Como usa H2 (um banco diferente do de produção), pode não capturar todos os problemas do MySQL real.  

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class D_EmployeeController_EmbeddedDbTest {
    @Autowired
    private TestRestTemplate restTemplate;
}
```

---

#### **E - Teste End-to-End com Banco de Dados Real (`E_EmployeeController_RealDbTest`)**  
- **Executa a API completa com um banco de dados MySQL real rodando no Docker**.  
- Utiliza `@SpringBootTest` + configurações de banco de integração (`application-integrationtest.properties`).  
- Permite testar a API de ponta a ponta, simulando um ambiente mais próximo da produção.  
- **Vantagem**: Garante que a aplicação funcione corretamente com o banco real.  
- **Limitação**: Mais lento e exige um banco externo no Docker.  

```java
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class E_EmployeeController_RealDbTest {
    @Autowired
    private TestRestTemplate restTemplate;
}
```

---

### **Resumo das diferenças**
1. **Teste C (Controller + Mock)** → Rápido, foca apenas no controlador, sem banco.  
2. **Teste D (Controller + H2)** → Teste real da API, mas com banco em memória.  
3. **Teste E (End-to-End com MySQL)** → Teste mais realista, usando um banco externo via Docker.  

