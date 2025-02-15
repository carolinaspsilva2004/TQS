# Projeto: Implementação de uma Estrutura de Dados Stack (TqsStack)

## 📌 Descrição
Este projeto implementa uma **pilha (Stack)** genérica em Java, chamada **TqsStack**, seguindo a abordagem **Test-Driven Development (TDD)**. Além disso, há uma variação com tamanho limitado (**TqsBoundedStack**) e testes unitários escritos com **JUnit 5**.

A abordagem TDD exigiu que os testes fossem escritos **antes da implementação real** dos métodos. O projeto foi desenvolvido usando **Maven** para gerenciamento de dependências e execução de testes.

---

## 🏗️ Estrutura do Projeto

```
project-root/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── stack/
│   │   │       ├── TqsStack.java
│   │   │       ├── TqsBoundedStack.java
│   ├── test/
│   │   ├── java/
│   │   │   └── stack/
│   │   │       ├── _TqsStackTest.java (desativado)
│   │   │       ├── TqsStackNewTest.java (novo conjunto de testes)
│── pom.xml (configuração do Maven)
```

- **`TqsStack.java`** → Implementação principal da pilha.
- **`TqsBoundedStack.java`** → Variante da pilha com capacidade limitada.
- **`_TqsStackTest.java`** → Testes antigos renomeados para não serem executados.
- **`pom.xml`** → Configuração do Maven com dependências do JUnit 5.

---

## 🚀 Passos Seguidos no Desenvolvimento

### 1️⃣ **Configuração Inicial**
✅ Criado um projeto **Maven** e adicionado **JUnit 5** e `maven-surefire-plugin` no `pom.xml`.

### 2️⃣ **Criação da Classe `TqsStack` (Skeleton)**
✅ Criada a classe `TqsStack<T>` sem implementação dos métodos, apenas com assinaturas.

### 3️⃣ **Escrita dos Testes (TDD)**
✅ Criada a classe `TqsStackTest.java` dentro de `src/test/java/stack/`.
✅ Escritos testes para os seguintes cenários:
- A pilha é criada vazia.
- `size()` retorna `0` ao iniciar.
- `push(x)` adiciona elementos e aumenta o tamanho corretamente.
- `pop()` remove e retorna o elemento correto.
- `peek()` retorna o elemento sem removê-lo.
- `pop()` e `peek()` em pilha vazia lançam `NoSuchElementException`.

### 4️⃣ **Implementação da Classe `TqsStack`**
✅ Implementação correta dos métodos após falha dos testes.
✅ Todos os testes passaram com sucesso após implementação.

### 5️⃣ **Criação de `TqsBoundedStack` (Opcional - Pilha com Capacidade Fixa)**
✅ Criada a classe `TqsBoundedStack<T>` com um limite máximo de elementos.
✅ Implementado `push(x)`, que lança `IllegalStateException` se a pilha estiver cheia.
✅ Criado teste `testPushBeyondCapacityThrowsException()` para validar esse comportamento.

### 6️⃣ **Renomeação da Classe de Testes e Geração de Novos Testes**
✅ Renomeado `TqsStackTest.java` para `_TqsStackTest.java` (agora ignorado pelo Maven).
✅ Criado `TqsStackNewTest.java`, gerado com IA (exemplo: GitHub Copilot).
✅ Comparada a cobertura de testes entre versões antigas e novas.

### 7️⃣ **Adição do Método `popTopN(int n)`**
✅ Método que remove os `n-1` primeiros elementos e retorna o `n`-ésimo.
✅ Criado teste para garantir cobertura total (100% statement e method coverage).

---

## 📊 Reflexão sobre Cobertura de Testes (alínea j)

Mesmo com **alta cobertura de testes (100%)**, há cenários em que a pilha pode falhar:

1. **Erros de concorrência**: Se a `TqsStack` for usada em um ambiente multithreading, chamadas simultâneas podem causar comportamentos inesperados, pois `LinkedList` não é thread-safe.
2. **Erros de desempenho**: O código pode ter problemas de eficiência que não são detectados pelos testes.
3. **Falhas devido a entradas inválidas**: Os testes podem não cobrir inputs incomuns, como `null` (se aplicável).
4. **Exceções inesperadas**: Se a estrutura interna da pilha mudar no futuro (por exemplo, de `LinkedList` para `ArrayList`), algumas exceções podem surgir inesperadamente.

🔹 **Conclusão**: Cobertura de código **não é garantia de qualidade**. Testes devem ser combinados com **análise de código**, testes de carga e revisões manuais.

---

## 🔧 Como Rodar o Projeto


1 - Compile e execute os testes:
```sh
 mvn test
```

2- Para verificar a cobertura de testes, use:
```sh
 mvn clean verify
```


