
## Testes de Automação com Selenium WebDriver - CoverBookStore

Este projeto demonstra como escrever testes usando o Selenium WebDriver para automatizar a interação com um site de livros online. Abaixo estão os objetivos dos testes e os conceitos principais abordados durante a implementação.

### Objetivos:
1. **Implementação Inicial do Teste**:
   - Escrever um teste Selenium que pesquisa por um livro ("Harry Potter and the Sorcerer's Stone") na livraria online e verifica se o título está presente nos resultados da busca.
   
2. **Refatoração do Teste para Maior Robustez**:
   - Utilizar **seletores bem definidos** para melhorar a manutenção.
   - Introduzir **esperas explícitas** para lidar com a latência de resposta da busca.

### Tecnologias Utilizadas:
- **Selenium WebDriver**: Automação da interação com o navegador.
- **JUnit 5**: Framework para execução dos testes.
- **Maven**: Gerenciamento de dependências e build.
- **AssertJ**: Biblioteca de assertivas fluentes para facilitar a leitura dos testes.

### Descrição dos Testes:

#### Parte a) - Teste Inicial

O primeiro caso de teste acessa a livraria, insere "Harry Potter and the Sorcerer's Stone" na caixa de busca, envia a pesquisa e verifica se o livro **"Harry Potter and the Sorcerer's Stone"** está presente nos resultados. Para isso, os seguintes localizadores foram usados:
- **Localizador ID**: `By.id("search-bar")` para o campo de pesquisa.
- **Localizador XPath**: `By.xpath("//h5[contains(text(),'Harry Potter and the Sorcerer\'s Stone')]`) para verificar o livro nos resultados.

#### Parte b) - Revisão dos Localizadores

- **Localizadores ID**: São uma boa prática para encontrar elementos rapidamente, já que são únicos na página.
- **Localizadores XPath**: São flexíveis, mas podem ser frágeis se a estrutura do DOM mudar. O XPath permite buscar por texto, o que é útil quando IDs ou classes não estão disponíveis.
- **Seletores CSS**: São ideais para selecionar elementos baseados em atributos como classes ou `data-testid`. Geralmente, são mais rápidos e robustos que o XPath.

#### Parte c) - Refatoração para Seletores Bem Definidos e Esperas Explícitas

- **Seletores CSS (`data-testid`)**: Substituí o XPath por um seletor CSS bem definido para melhorar a robustez e a manutenção dos testes.
- **Espera Explícita**: Introduzi `WebDriverWait` para lidar com a latência de resposta da busca, garantindo que o teste não falhe devido ao tempo de carregamento.

#### Parte d) - Alterações Finais no Título da Pesquisa

A pesquisa foi atualizada para ser feita especificamente pelo título completo **"Harry Potter and the Sorcerer's Stone"**. Assim, garantimos que o teste busque exatamente o livro correto nos resultados da busca.

### Lições Importantes:

1. **Seletores Robustos**: 
   - Usar seletores bem definidos, como `data-testid`, para tornar os testes mais robustos e menos frágeis.
   - Evitar o uso excessivo de XPath quando há alternativas mais simples e estáveis, como seletores CSS.

2. **Espera Explícita**:
   - Aguardar explicitamente até que os elementos se tornem visíveis ou interativos é crucial para evitar problemas de tempo de execução.
   - Sempre garantir que o conteúdo dinâmico ou processos assíncronos tenham tempo suficiente para carregar antes de interagir com os elementos.

3. **Resiliência dos Testes**:
   - Combinar bons localizadores e espera explícita, consigo garantir que os testes sejam mais resilientes e menos propensos a falhas devido a mudanças no site ou no tempo de carregamento.


### Conclusão:
Este exercício demonstrou como usar **Selenium WebDriver** para automatizar testes e destacou a importância de usar **seletores bem definidos** e **esperas explícitas** para melhorar a robustez e a manutenção dos testes. Com essas práticas, consigo garantir que os testes sejam mais confiáveis e menos propensos a falhas devido a mudanças na estrutura ou no tempo de resposta do site.
