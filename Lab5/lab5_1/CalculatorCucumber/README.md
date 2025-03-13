# 📌 Calculadora RPN com Cucumber

Este projeto implementa uma **Calculadora de Notação Polaca Inversa (RPN)** com **testes automatizados utilizando Cucumber**. O objetivo é demonstrar como escrever testes BDD (Behavior Driven Development) utilizando **Cucumber, JUnit e Maven**.

## 📜 Funcionalidades Implementadas
- **Calculadora RPN** com suporte às operações básicas:
  - ✅ **Adição**
  - ✅ **Subtração**
  - ✅ **Multiplicação**
  - ✅ **Divisão**
  - ✅ **Operações inválidas**
- **Testes automatizados com Cucumber**
  - Definição de cenários no formato **Gherkin** (`.feature`)
  - Implementação dos passos de teste em **Java**
  - Uso de **Cucumber Expressions** para melhor legibilidade
- **Integração com Maven** para gestão de dependências e execução de testes

---

## 📂 Estrutura do Projeto
```bash
CalculatorCucumber/
│── src/
│   ├── main/java/tqs/calculatorcucumber/
│   │   ├── Calculator.java  # Implementação da calculadora RPN
│   ├── test/java/tqs/calculatorcucumber/
│   │   ├── CalculatorSteps.java  # Implementação dos passos de teste
│   │   ├── RunCucumberTest.java  # Ativação do motor do Cucumber
│   ├── test/resources/tqs/calculatorcucumber/
│   │   ├── calculator_ops.feature  # Definição dos cenários de teste
│── pom.xml  # Configuração do Maven
│── README.md  # Documentação do projeto
```

---

## 🔧 Configuração e Dependências
O projeto utiliza Maven para gestão de dependências. O ficheiro `pom.xml` inclui:

- **Cucumber** (`cucumber-java`, `cucumber-junit-platform-engine`)
- **JUnit** (`junit-jupiter`, `junit-platform-suite`)
- **SLF4J** para logs
- **Spring Boot Starter** (para uma base sólida de projeto)

Para instalar as dependências, basta correr:
```sh
mvn clean install
```

---

## 📝 Implementação
### **1️⃣ Calculadora (`Calculator.java`)
A classe `Calculator.java` implementa a lógica da calculadora RPN, suportando operações matemáticas básicas.

```java
public class Calculator {
    private final Deque<Number> stack = new LinkedList<>();
    private static final List<String> OPS = List.of("-", "+", "*", "/");

    public void push(Object arg) {
        if (arg instanceof String && OPS.contains(arg)) {
            Number y = stack.removeLast();
            Number x = stack.isEmpty() ? 0 : stack.removeLast();
            Double val = null;
            switch ((String) arg) {
                case "-":
                    val = x.doubleValue() - y.doubleValue();
                    break;
                case "+":
                    val = x.doubleValue() + y.doubleValue();
                    break;
                case "*":
                    val = x.doubleValue() * y.doubleValue();
                    break;
                case "/":
                    if (y.doubleValue() == 0) {
                        throw new ArithmeticException("Divisão por zero não permitida.");
                    }
                    val = x.doubleValue() / y.doubleValue();
                    break;
                default:
                    throw new IllegalArgumentException("Operação inválida: " + arg);
            }
            push(val);
        } else if (arg instanceof Number) {
            stack.add((Number) arg);
        } else {
            throw new IllegalArgumentException("Entrada inválida: " + arg);
        }
    }

    public Number value() {
        return stack.isEmpty() ? 0 : stack.getLast();
    }
}
```

### **2️⃣ Testes Cucumber (`CalculatorStepsTest.java`)
A classe `CalculatorStepsTest.java` implementa os **passos dos testes Cucumber**, utilizando **Cucumber Expressions**:

```java
@When("I add {int} and {int}")
public void add(int a, int b) {
    calc.push(a);
    calc.push(b);
    calc.push("+");
}
```

### **3️⃣ Ficheiro `.feature` (cenários de teste)**
O ficheiro `calculator_ops.feature` contém os **cenários de teste** escritos em **Gherkin**:

```gherkin
Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Subtraction
    When I subtract 2 from 7
    Then the result is 5

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

    Examples: Single digits
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |

  Scenario: Multiplication
    When I multiply 3 by 4
    Then the result is 12

  Scenario: Division
    When I divide 10 by 2
    Then the result is 5

  Scenario: Invalid operation
    Given a calculator I just turned on
    When I perform an invalid operation
    Then an error is thrown
```

---

## 🚀 Como Executar os Testes
Para correr os testes automatizados:
```sh
mvn clean test
```


## 🎯 Conclusão
Este projeto demonstra como **desenvolver e testar uma aplicação Java** utilizando **Cucumber e JUnit**, promovendo boas práticas de **BDD**. 🚀

