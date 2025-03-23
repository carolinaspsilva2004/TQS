*
## ✅ Projeto: Testes de Integração com REST Assured

### 📚 **Sobre o Projeto**
Este projeto foi criado com o objetivo de explorar e praticar o uso da biblioteca **REST Assured** para testes de integração de APIs REST em Java, sem utilização do Spring Boot.  
A API que utilizei para este exercício foi a **JSONPlaceholder**, que é uma API pública "fake" usada frequentemente para simulação e testes.  

---

### 🎯 **Objetivos**
- Verificar se determinados endpoints REST estão acessíveis e a devolver as respostas corretas.
- Validar o conteúdo dos objetos JSON retornados pela API.
- Garantir que as respostas da API são rápidas (abaixo de 2 segundos).
- Aprender a estruturar testes de integração utilizando JUnit 5, Hamcrest e REST Assured.

---

### 🛠️ **Tecnologias e Ferramentas Utilizadas**
- **Java 21**
- **Maven**
- **JUnit 5 (versão 5.12.0)**
- **REST Assured (versão 5.4.0)**
- **Hamcrest (versão 2.2)**
- **IDE: IntelliJ IDEA / VS Code (opcional)**
- **Terminal e linha de comandos**


### 📥 **Como criar o projeto (passos que eu segui):**
1. Criei o projeto Maven através do terminal:
```bash
mvn archetype:generate -DgroupId=tqs.restassured -DartifactId=RestAssured -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
2. Editei o ficheiro `pom.xml` para incluir as dependências do REST Assured, JUnit 5 e Hamcrest.  
3. Adaptei o plugin do Maven para garantir compatibilidade com Java 21.  
4. Criei a estrutura de pastas para os testes em `src/test/java/tqs/restassured/`.  
5. Implementei os testes na classe `TodosApiTest.java`.

---

### ✅ **Testes Implementados**
Na minha classe de testes utilizei a API JSONPlaceholder (https://jsonplaceholder.typicode.com) e escrevi os seguintes testes:
- Verificar que o endpoint `/todos` responde com o código 200 (disponível).  
- Verificar que ao pedir o ToDo com o ID `4` a resposta contém o título `"et porro tempora"`.  
- Verificar que na listagem de todos os ToDos aparecem os IDs `198` e `199`.  
- Verificar que a resposta para a listagem de ToDos chega em menos de 2 segundos.  

---

### ▶️ **Como correr os testes**
Basta executar o comando na raiz do projeto:
```bash
mvn test
```

---

### 📝 **Conclusão**
Com este projeto consegui ganhar prática na criação de testes de integração utilizando REST Assured e JUnit 5.  
Este tipo de testes é muito útil para validar que APIs externas ou internas estão a responder corretamente e dentro de tempos aceitáveis.  
Além disso, foi uma boa oportunidade para reforçar o conhecimento em Maven e gestão de dependências.  
