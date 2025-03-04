# Cars Service - Spring Boot Application

## Descrição

Este projeto consiste no desenvolvimento de uma API para um sistema de informação de carros utilizando **Spring Boot**. 
O objetivo principal foi seguir a abordagem **TDD (Test-Driven Development)**, garantindo que a implementação fosse validada 
por testes antes da escrita do código de produção.

A aplicação utiliza **Spring Boot**, **Spring Data JPA**, **H2 Database** e segue uma estrutura baseada no
 **diagrama UML** fornecido.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Maven**
- **Spring Web** (para a API REST)
- **Spring Data JPA** (para persistência)
- **H2 Database** (banco de dados em memória para testes)
- **JUnit 5** e **Mockito** (para testes)

## Dependências (POM.xml)

```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- H2 Database (para testes) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Testes com JUnit e Mockito -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/tqs/carsservice/
│   │   ├── controller/    # Camada Controller (API REST)
│   │   ├── service/       # Camada Service (Regras de Negócio)
│   │   ├── repository/    # Camada Repository (Persistência)
│   │   ├── model/         # Modelos (Entidades JPA)
│   ├── resources/
│       ├── application.properties  # Configuração do Banco de Dados H2
└── test/
    ├── java/tqs/carsservice/
        ├── CarControllerTest.java    # Testes da API REST
        ├── CarManagerServiceTest.java       # Testes da camada de Serviço
        ├── CarRepositoryTest.java    # Testes da camada de persistência
```

---

## Funcionalidades Implementadas

1. **CRUD de Carros**:

   - Criar um carro
   - Buscar todos os carros
   - Buscar um carro pelo ID
   - Guardar carros na base de dados

2. **Testes Implementados**:

   - **(a)** Teste do `CarController` (mockando `CarManagerService`)
   - **(b)** Teste do `CarManagerService` (mockando `CarRepository`)
   - **(c)** Teste da persistência com `CarRepository` (utilizando H2 Database)

3. **Lógica de Negócio: Encontrar um Carro Substituto**:

   - Dada uma necessidade de um carro substituto, encontrar um veículo com o **mesmo segmento** e **tipo de motor**.
   - Implementado na ``.
   - Testado com **JUnit e Mockito**.

---

## Configuração do Banco de Dados (H2)

A base de dados **H2** é utilizada para persistência em memória, facilitando os testes sem necessidade de configuração externa.

Arquivo: `src/test/resources/application.properties`

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
```

---

## Testes

### Como Rodar os Testes

Para executar os testes, utilize o seguinte comando:

```sh
mvn test
```

### Exemplo de Teste Unitário (CarManagerService)

```java
@Test
void shouldFindSuitableReplacementCar() {
    Car originalCar = new Car(1L, "Toyota", "Corolla", "Sedan", "Gasoline");
    Car replacementCar = new Car(2L, "Honda", "Civic", "Sedan", "Gasoline");

    when(carRepository.findById(1L)).thenReturn(Optional.of(originalCar));
    when(carRepository.findBySegmentAndEngineType("Sedan", "Gasoline"))
            .thenReturn(Arrays.asList(originalCar, replacementCar));

    Optional<Car> foundCar = carManagerService.findReplacementCar(1L);

    assertTrue(foundCar.isPresent());
    assertEquals("Honda", foundCar.get().getMaker());
    verify(carRepository, times(1)).findById(1L);
}
```

---


## Conclusão

Este projeto demonstra **boas práticas de desenvolvimento**, como: 
✔ Uso de **Spring Boot** para construir APIs REST; 
✔ Aplicação da **arquitetura em camadas** (Controller, Service, Repository); 
✔ Implementação de **Test-Driven Development (TDD)**; 
✔ Uso de **Mockito** para **mockar dependências** em testes; 
✔ Utilização do **H2 Database** para testes rápidos e eficientes; 
✔ Implementação de uma lógica de negócio realista (**carro substituto**).


