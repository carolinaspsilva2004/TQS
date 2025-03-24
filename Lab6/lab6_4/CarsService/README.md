

# **Car Service - Testes da Camada de Persistência**  

 
## C) **Vantagens e Desvantagens de Usar uma Conexão Real com uma Base de Dados nos Testes**  

### ✅ **Vantagens**  
1. **Ambiente Realista** – Testar com uma base de dados real garante que consultas, relacionamentos e restrições funcionem como em produção.  
2. **Teste de Integridade dos Dados** – Restrições como `NOT NULL`, `FOREIGN KEY` e `UNIQUE` podem ser verificadas corretamente.  
3. **Análise de Desempenho** – Permite testar tempos de execução de consultas e estratégias de otimização.  
4. **Funcionalidades Específicas da Base de Dados** – Algumas bases de dados possuem comportamentos únicos que só podem ser testados em um ambiente real.  

### ❌ **Desvantagens**  
1. **Execução Mais Lenta** – Operações em bases de dados reais são mais lentas do que em bases de dados em memória.  
2. **Configuração e Manutenção Complexas** – Requer configuração, limpeza e, às vezes, redefinição entre testes.  
3. **Persistência de Dados de Teste** – Dados de testes podem permanecer na base de dados e afetar execuções futuras se não forem corretamente gerenciados.  
4. **Dependência de Fatores Externos** – Problemas de rede, indisponibilidade da base de dados ou estados inconsistentes podem afetar a confiabilidade dos testes.  

#
## **Como Executar os Testes**  
1. Corre o docker:  
   ```sh
        docker run --name mysql5tqs -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tqs -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -p 33060:3306 -d mysql/mysql-server:5.7
  
3. Executa os testes:  
   ```sh
   mvn test
   ```  

