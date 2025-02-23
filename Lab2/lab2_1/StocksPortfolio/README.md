## 📌 Stocks Portfolio - Testes com JUnit e Mockito  

Este projeto implementa e testa uma classe **StocksPortfolio**, que gerencia ações de um portfólio e calcula seu valor total utilizando um serviço de mercado de ações.  

O projeto também inclui testes unitários com **JUnit 5** e **Mockito**, além do uso de **Hamcrest** para assertions mais legíveis.  

---

## 📜 O que foi implementado?  

1. **Criação das classes principais**  
   - `Stock`: Representa uma ação com rótulo (`label`) e quantidade (`quantity`).  
   - `IStockmarketService`: Interface para consultar o preço das ações.  
   - `StocksPortfolio`: Classe principal que gerencia um conjunto de `Stock` e calcula seu valor total.  

2. **Testes unitários para `totalValue()`**  
   - Uso de **Mockito** para criar um mock do serviço de mercado (`IStockmarketService`).  
   - Configuração do mock para retornar valores predefinidos (usando `when().thenReturn()`).  
   - Verificação dos resultados com **JUnit Assertions** e **Mockito Verify**.  

3. **Cobertura de edge cases no método `mostValuableStocks(int topN)`**  
   - Implementação do método `mostValuableStocks(int topN)`, que retorna as ações mais valiosas.  
   - Testes para diferentes cenários (topN maior que disponível, portfólio vazio, valores iguais, etc.).  

4. **Melhoria dos assertions com Hamcrest**  
   - Uso de `assertThat()` para deixar os testes mais legíveis.  

---

## 🛠️ Tecnologias e Bibliotecas Usadas  

- **Java 21**  
- **JUnit 5** (Testes unitários)  
- **Mockito** (Mocking de dependências externas)  
- **Hamcrest** (Assertions mais legíveis)  
- **Maven** (Gerenciamento de dependências)  

### 📌 Dependências no `pom.xml`  
Adicionei estas dependências ao `pom.xml` para rodar os testes:  
```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.7.1</version>
        <scope>test</scope>
    </dependency>

    <!-- Mockito -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>3.11.2</version>
        <scope>test</scope>
    </dependency>

    <!-- Hamcrest -->
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest</artifactId>
        <version>2.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```  

---

## 🚀 Como Executar os Testes?  

1️⃣ **Baixar dependências do Maven**  
```sh
mvn clean install
```  

2️⃣ **Rodar os testes**  
```sh
mvn test
```  

---

## 🧪 Resumo dos Testes Implementados  

### ✅ Teste para `totalValue()`  
- Mocka o `IStockmarketService` para retornar preços fixos.  
- Adiciona ações ao portfólio.  
- Verifica se o cálculo do valor total está correto.  

### ✅ Testes para `mostValuableStocks(int topN)`  
Casos testados:
- `topN > número de ações disponíveis` → Retorna todas as ações.  
- `topN == número de ações` → Retorna todas ordenadas.  
- `topN == 0` → Retorna uma lista vazia.  
- `Portfólio vazio` → Retorna uma lista vazia.  
- `Ações com mesmo valor` → Mantém ordem estável.  
- `Ação com quantidade 0` → Ignorada corretamente.  
- `topN negativo` → Retorna lista vazia.  

---

## 📖 Conclusão  

Este projeto demonstrou como testar uma classe que depende de um serviço externo utilizando **Mockito** para criar um mock controlado.  
Além disso, abordamos **edge cases** no método `mostValuableStocks()` e melhoramos a legibilidade dos testes com **Hamcrest**.  

Agora o código está **testável, confiável e bem estruturado** para futuras melhorias! 🚀🎯  

