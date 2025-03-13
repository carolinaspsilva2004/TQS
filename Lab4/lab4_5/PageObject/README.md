# 📖 CoverBookStore - Variações de Browser (Docker Selenium Grid)

## 🚀 As Minhas Implementações

### 1️⃣ **Utilização do Docker para Testes em Diferentes Navegadores**
Implementei a execução dos testes automatizados num **ambiente Dockerizado**, permitindo a execução em navegadores que não estão instalados localmente. Para isso, utilizei o **Selenium Grid** com containers Docker.

📌 **Tecnologias Utilizadas:**
- **Docker** para executar os navegadores em containers.
- **Selenium Grid** para gerir a execução remota dos testes.
- **WebDriver remoto** para ligar o Selenium ao navegador Dockerizado.

### 2️⃣ **Configuração do Selenium Grid com Docker**
Criei um ambiente de teste utilizando **Selenium Grid** com um nó do Google Chrome em Docker. O `docker-compose.yml` foi configurado para inicializar os serviços necessários:

```yaml
docker compose up -d
```

Isto inicia os seguintes containers:
- **Selenium Hub** - Gere os nós de teste.
- **Chrome Node** - Container que executa o Google Chrome para correr os testes.

### 3️⃣ **Utilização do WebDriver Remoto**
Em vez de executar o WebDriver localmente, agora ligo-me a um navegador que corre no Docker:

```java
WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new ChromeOptions());
```

Isto permite que os testes sejam executados **em qualquer ambiente, independentemente de o navegador estar instalado localmente**.

### 4️⃣ **Melhoria na Gestão do WebDriver**
Para evitar que o WebDriver continue a correr indefinidamente, adicionei uma etapa para **encerrar o navegador após cada teste**:

```java
@AfterEach
void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

Isto garante que **não fiquem processos do Chrome abertos após a execução dos testes**.

### 5️⃣ **Gestão de Esperas para Maior Confiabilidade**
Aumentei os tempos de espera para garantir que os testes não falhem devido a carregamentos lentos do site.

```java
this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
```

Além disso, adicionei **gestão de exceções** para evitar falhas caso o elemento não seja encontrado:

```java
try {
    return wait.until(ExpectedConditions.presenceOfElementLocated(
        By.cssSelector("[data-testid=book-search-item]"))).getText();
} catch (TimeoutException e) {
    return "Elemento não encontrado";
}
```

---

## 📚 Aprendizagens

1️⃣ **Execução de testes em navegadores remotos usando Docker**
2️⃣ **Configuração do Selenium Grid para execução distribuída de testes**
3️⃣ **Utilização do `RemoteWebDriver` para ligação ao Selenium Grid**
4️⃣ **Melhoria na gestão do WebDriver para evitar processos em aberto**
5️⃣ **Aprimoramento das esperas explícitas para tornar os testes mais estáveis**

Esta abordagem permite que os meus testes sejam executados **em qualquer ambiente**, garantindo maior flexibilidade e escalabilidade para os testes automatizados! 🚀

