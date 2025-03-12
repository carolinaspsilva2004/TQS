# README - CoverBookStore Test Project

## Novas Funcionalidades Implementadas

Este documento descreve as melhorias e refatorações realizadas no projeto de testes automatizados da **CoverBookStore**.

### **1️⃣ Implementação do Padrão Page Object**

Foi introduzido o **Page Object Model (POM)**, que melhora a organização do código ao separar a lógica dos testes da lógica de interação com a página.
Agora, cada página do site é representada como uma classe Java, com os elementos definidos como atributos e as ações como métodos. Isso torna o código:

- **Mais reutilizável** – podemos reaproveitar a classe da página em vários testes.
- **Mais fácil de manter** – se a estrutura da página mudar, apenas a classe correspondente precisa ser atualizada.
- **Mais legível** – os testes se tornam mais simples, focando na validação e não na navegação.

### **2️⃣ Uso do Page Factory para Gerir Elementos**

A ferramenta **Page Factory** do Selenium foi utilizada para inicializar e gerir elementos da página de forma automática, reduzindo a complexidade da inicialização manual dos WebElements.

Benefícios:
- Reduz a necessidade de chamadas explícitas a `findElement()`.
- Melhora a performance e organização do código.

### **3️⃣ Ajuste nos Tempos de Espera para Melhor Estabilidade**

- A espera implícita foi aumentada para **15 segundos** para garantir que os elementos da página tenham tempo suficiente para carregar antes da execução do teste.
- O WebDriver agora aguarda mais tempo antes de interagir com a página, evitando falhas causadas por carregamento lento do site.
