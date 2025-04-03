## **C)**
A análise do relatório do Lighthouse mostra alguns pontos interessantes sobre a performance, 
acessibilidade e boas práticas do seu site. Vamos responder às perguntas com base nas informações do relatório:

### **1. O que contribui mais para a percepção de desempenho do frontend? O que esses métricos significam?**

As principais métricas que contribuem para o desempenho percebido (Performance Score) são:

- **First Contentful Paint (FCP) – 2.9s**:
  - **O que significa**: O tempo até que o primeiro conteúdo (como texto ou imagem) seja exibido na tela. O FCP é uma das primeiras interações visíveis que o usuário tem com a página.
  - **Impacto**: Um FCP baixo melhora a percepção de que a página está carregando rapidamente. O objetivo é ter FCP abaixo de 2 segundos para uma boa experiência de usuário.
  
- **Largest Contentful Paint (LCP) – 3.4s**:
  - **O que significa**: Mede o tempo até que o maior conteúdo visível (imagem ou bloco de texto) seja renderizado completamente na tela. Essa métrica reflete a carga de conteúdo principal da página.
  - **Impacto**: O LCP é crucial para a percepção de quão rápido o conteúdo principal da página é carregado. A meta é ter o LCP abaixo de 2.5 segundos.

- **Total Blocking Time (TBT) – 120ms**:
  - **O que significa**: Mede o tempo total durante o carregamento da página onde o thread principal está bloqueado e não pode responder à interação do usuário. Um valor baixo de TBT indica que a página permite interatividade de forma rápida.
  - **Impacto**: Menor TBT significa menos tempo onde o usuário não pode interagir com a página, o que resulta em uma melhor experiência.

- **Cumulative Layout Shift (CLS) – 0**:
  - **O que significa**: Indica quanto a página "salta" ou muda de posição durante o carregamento. Um valor de 0 significa que a página não sofreu mudanças inesperadas no layout.
  - **Impacto**: O CLS ideal é 0, pois melhora a estabilidade visual da página e evita surpresas desagradáveis para o usuário, como botões ou links que mudam de lugar.

- **Speed Index – 3.0s**:
  - **O que significa**: Mede a rapidez com que o conteúdo visível da página é renderizado. Quanto menor o Speed Index, mais rápido o conteúdo se torna visível para o usuário.
  - **Impacto**: A meta para Speed Index é abaixo de 3 segundos, e quanto mais rápido, melhor a percepção do carregamento da página.

### **2. Como tornar o site mais acessível?**

O relatório de acessibilidade identificou várias áreas em que o site pode ser melhorado. Um dos pontos principais para melhorar a acessibilidade é:

- **Contraste insuficiente entre as cores de fundo e o texto**:
  - **Solução**: Aumentar o contraste entre o texto e o fundo para garantir que ele seja legível para todos os usuários, especialmente aqueles com deficiências visuais. Certifique-se de que o contraste de cores esteja em conformidade com as diretrizes WCAG (Web Content Accessibility Guidelines), onde o valor mínimo recomendado é de 4.5:1 para texto normal e 3:1 para texto grande.
  
Além disso, o Lighthouse fornece uma lista de **itens adicionais** para verificar manualmente, como a navegação por teclado e a adequação de tags ARIA, que são cruciais para garantir a acessibilidade.

Outras sugestões incluem:
- **Uso de alternativas textuais para imagens**: Certifique-se de que todas as imagens tenham atributos `alt` adequados para usuários de leitores de tela.
- **Facilidade de navegação por teclado**: Garantir que o site possa ser navegado completamente com o teclado, sem a necessidade de um mouse.

### **3. O que mais precisa ser feito para melhorar as boas práticas e SEO?**

- **Boas práticas**: 
  - A auditoria de boas práticas mostrou que o site segue muitas recomendações de segurança, como mitigação contra ataques XSS e o uso de uma política forte de HSTS. No entanto, há sempre mais áreas a serem verificadas, como a verificação de cabeçalhos HTTP corretos, como `X-Frame-Options` para prevenção de ataques de clickjacking.

- **SEO**: 
  - O relatório de SEO sugere que **links não têm texto descritivo**. Isso é importante para que o Google e outros motores de busca possam entender o conteúdo do link, além de melhorar a acessibilidade.
  - **Solução**: Verifique se todos os links possuem textos alternativos claros e descritivos, como `click here` ou `read more`. Evite links genéricos e use descrições mais informativas.

### **Conclusão**

A partir do relatório do Lighthouse, é possível identificar várias áreas de melhoria no seu site:
- **Desempenho**: Melhorar o FCP e o LCP reduzindo o tempo de carregamento e melhorando a interatividade.
- **Acessibilidade**: Melhorar o contraste entre o texto e o fundo e garantir uma navegação eficiente por teclado.
- **Boas práticas**: Continuar implementando as recomendações de segurança e boas práticas de desenvolvimento.
- **SEO**: Melhorar o uso de textos alternativos para links e otimizar o HTML para motores de busca.

Essas melhorias contribuirão para uma melhor experiência de usuário e uma classificação superior em mecanismos de pesquisa, além de garantir maior acessibilidade para todos os usuários.

## **D)**
No Google Chrome, a guia "Lighthouse" permite realizar auditorias de performance, acessibilidade, boas práticas e SEO diretamente no navegador. Comparar os resultados da auditoria do Lighthouse via CLI com a auditoria realizada na guia "Lighthouse" do Chrome é uma excelente maneira de validar os resultados e observar possíveis discrepâncias entre os ambientes.
Por quê usar uma guia de navegação anônima (Incognito)?

    -Evitar cache: Quando você usa uma janela de navegação regular, o navegador pode armazenar em cache recursos da página, o que pode resultar em tempos de carregamento mais rápidos e influenciar negativamente os resultados do teste. Uma janela de navegação anônima não usa o cache ou cookies da sessão anterior, proporcionando resultados mais consistentes e realistas.

    - Impedir interferência de extensões: Extensões de navegador, como bloqueadores de anúncios ou outras ferramentas, podem alterar o comportamento de uma página e influenciar os resultados da auditoria. O modo anônimo desativa as extensões, garantindo que os testes sejam realizados sem qualquer interferência.

Resumoe o relatório do Chrome: 
Performance: A pontuação de 87 indica que o site precisa de algumas melhorias, principalmente em relação ao tempo de carregamento e bloqueio do thread principal. Focar na otimização do LCP e na redução do TBT pode trazer melhorias significativas.

Acessibilidade: A pontuação de 95 é excelente, mas a questão do contraste precisa ser corrigida para garantir que o site seja totalmente acessível.

Boas Práticas: A pontuação perfeita de 100 é um excelente resultado, indicando que as boas práticas de segurança estão bem implementadas.

SEO: A pontuação de 91 é boa, mas melhorar os links descritivos ajudará a otimizar ainda mais o site para os motores de busca.


### E) **Usar a Ferramenta Lighthouse no Chrome para Análise em Dispositivo Desktop**

Quando utilizo a ferramenta **Lighthouse** nas Ferramentas de Desenvolvedor do Chrome para um **dispositivo desktop**, os resultados podem ser diferentes dos anteriores (especialmente se realizaste o teste num dispositivo móvel). As principais diferenças e razões para isso podem ser:

1. **Condições de Rede**:
   - **Dispositivos móveis** geralmente têm conexões de rede mais lentas ou variáveis em comparação com dispositivos desktop. O Lighthouse no desktop normalmente assume conexões mais rápidas e estáveis.
   - **Impacto**: Isso pode resultar em melhores resultados para **Performance** e **First Contentful Paint (FCP)** no desktop em comparação com o móvel, já que os desktops têm velocidades de rede melhores e menos limitações de CPU e memória.

2. **Ambiente de Renderização**:
   - **Dispositivos móveis** enfrentam limitações como ecrãs mais pequenos, resoluções mais baixas e menos poder de processamento. Já no desktop, há mais recursos de processamento, o que pode permitir um carregamento mais rápido do site.
   - **Impacto**: Isso pode resultar em um **Largest Contentful Paint (LCP)** e **Speed Index** melhores no desktop, pois as imagens podem carregar mais rápido e o JavaScript ser processado com mais eficiência.

3. **Experiência do Utilizador**:
   - **Dispositivos móveis** frequentemente têm fatores adicionais, como eventos de toque, orientação da tela e responsividade, que podem afetar o desempenho e a acessibilidade de forma diferente em comparação com dispositivos desktop.

**Por que os resultados são diferentes**: O ambiente de desktop geralmente possui recursos mais poderosos (e.g., CPU, largura de banda de rede) e menos limitações, permitindo um desempenho melhor que no dispositivo móvel, que está sujeito a conexões mais lentas, telas menores e menor poder de processamento.

---

### F) **Por Quê Testar os Diferentes Aspectos (Performance, Acessibilidade, Boas Práticas e SEO)?**

Cada um desses aspectos desempenha um papel importante para garantir que um site esteja otimizado, não apenas para desempenho, mas também para a experiência do utilizador, classificação em motores de busca e segurança:

1. **Performance**:
   - **Por que testar**: Os utilizadores são sensíveis ao tempo de carregamento e à capacidade de resposta. Sites que carregam rapidamente proporcionam uma melhor experiência, reduzem taxas de rejeição e aumentam as conversões. A performance é um fator chave para a satisfação do utilizador e o sucesso nos negócios.
   - **Aspectos importantes**: First Contentful Paint (FCP), Largest Contentful Paint (LCP), Total Blocking Time (TBT), etc.
   
2. **Acessibilidade**:
   - **Por que testar**: Garantir que um site seja acessível para utilizadores com deficiências não é apenas uma exigência legal em muitos países (conformidade com a ADA, diretrizes WCAG), mas também amplia o público-alvo. Um site acessível melhora a inclusão e a satisfação do utilizador.
   - **Aspectos importantes**: Suporte para leitores de ecrã, navegação por teclado, contraste de cores, etc.

3. **Boas Práticas**:
   - **Por que testar**: Seguir as melhores práticas, especialmente em relação à segurança e aos padrões modernos da web, garante que o site seja seguro e fácil de manter. Isso também reduz vulnerabilidades a ataques (e.g., XSS, clickjacking) e garante que o site funcione bem em diferentes dispositivos e navegadores.
   - **Aspectos importantes**: Política de segurança de conteúdo (CSP), cabeçalhos de segurança HTTP (HSTS), uso de HTTPS, etc.

4. **SEO (Otimização para Motores de Busca)**:
   - **Por que testar**: Otimizar um site para motores de busca garante melhor visibilidade e classificação, levando a mais tráfego orgânico. Um SEO fraco pode resultar em visibilidade baixa nos resultados de pesquisa, limitando a aquisição e o engajamento de utilizadores.
   - **Aspectos importantes**: Metadados, links descritivos, compatibilidade com dispositivos móveis, dados estruturados, etc.

Testar todos esses aspectos garante uma abordagem **holística** e **focada no utilizador** para a otimização do site. Se um desses aspetos for negligenciado, pode prejudicar a experiência geral do utilizador, segurança ou classificação.

---

### g) **Analisando um Site Conhecido Usando o Lighthouse**

Vamos considerar uma análise do **www.ua.pt** (site da Universidade de Aveiro) e observar alguns problemas típicos.

1. **Problemas de Performance**:
   - **Exemplo de problema**: **Primeiro Pintor Conteúdo (FCP)** ou **Maior Pintura de Conteúdo (LCP)** lentos. Isso pode ser causado por imagens grandes ou JavaScript/CSS bloqueando a renderização.
   - **Impacto**: Utilizadores podem perceber o site como lento, levando a frustração e aumento da taxa de rejeição. Motores de busca também utilizam essas métricas para classificar sites.

2. **Problemas de Acessibilidade**:
   - **Exemplo de problema**: Falta de contraste suficiente entre texto e fundo ou falta de rótulos ARIA.
   - **Impacto**: Utilizadores com deficiência visual ou que dependem de leitores de ecrã podem ter dificuldades para navegar no site, tornando-o inacessível para um público maior.

3. **Problemas de Boas Práticas**:
   - **Exemplo de problema**: Falta de cabeçalhos de segurança, como a política de segurança de conteúdo (CSP) ou cabeçalhos HTTP adequados para segurança.
   - **Impacto**: Vulnerabilidades de segurança como ataques XSS podem ser possíveis, afetando a integridade do site e dos dados do utilizador.

4. **Problemas de SEO**:
   - **Exemplo de problema**: Falta de meta descrições, estrutura HTML inadequada ou falta de atributos alt para imagens.
   - **Impacto**: O site pode não ser otimizado para motores de busca, levando a uma visibilidade baixa e menor tráfego.

---

### **Desafios de Corrigir Problemas em Sites Live**:

1. **Código Legado**: Sites que estão no ar há muito tempo podem ter um código complexo, desatualizado ou mal documentado, o que dificulta as atualizações sem causar quebras de funcionalidade.
   
2. **Falta de Recursos**: Se o site não foi desenvolvido com performance, segurança ou acessibilidade em mente desde o início, pode ser necessário um grande esforço para resolver os problemas.

3. **Impacto no Utilizador**: Corrigir problemas em sites ao vivo pode afetar a experiência do utilizador, sendo necessário planeamento e testes cuidadosos para garantir que as mudanças não introduzam novos problemas.

4. **Dificuldade em Implementar Correções**: Pode ser difícil aplicar otimizações, como compressão de imagens ou melhoria do desempenho do JavaScript, em sites com grande volume de visitantes ou funcionalidades complexas já em produção.

---

### **Prevenindo Esses Problemas no Futuro**:

1. **Planeamento para Performance e Acessibilidade desde o Início**: Integrar as melhores práticas e otimizações de performance nas primeiras fases de desenvolvimento pode ajudar a evitar esses problemas mais tarde.
   
2. **Auditorias Contínuas**: Realizar auditorias regulares de performance e acessibilidade (usando ferramentas como o Lighthouse) ao longo do ciclo de vida do desenvolvimento ajuda a identificar problemas cedo, antes de se tornarem difíceis de corrigir.

3. **Testes Automatizados**: Usar pipelines CI/CD para rodar auditorias automatizadas do Lighthouse e outros testes (e.g., testes de acessibilidade) durante as fases de desenvolvimento e implementação para detectar problemas antes que cheguem à produção.

Ao abordar performance, acessibilidade, boas práticas e SEO de maneira proativa, os sites podem oferecer uma melhor experiência aos utilizadores e alcançar maior sucesso a longo prazo.