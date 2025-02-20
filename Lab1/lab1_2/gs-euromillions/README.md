# **EuroMillions - Resumo do Projeto**  

## **1. Objetivo**  
Criar e testar um sistema de apostas para o **EuroMillions**, garantindo geração válida de apostas, sorteios e comparação de resultados.  

## **2. Funcionalidades Principais**  
- **`DemoMain`**: Demonstra a geração de apostas aleatórias, sorteios e exibição dos resultados.  
- **`Dip`**: Representa uma aposta, validando números e estrelas, gerando apostas aleatórias e comparando resultados com `equals()`.  

## **3. Testes e Melhorias**  
✅ **Validação de dados**: Garante que números e estrelas estejam dentro dos limites corretos.  
✅ **Geração aleatória**: Testa se as apostas criadas são sempre válidas e sem repetições.  
✅ **Cobertura do método `equals()`**: Verifica casos com `null`, objetos diferentes e apostas distintas.  

## **4. Aprendizados Importantes**  
🔹 **Configuração e uso do Jacoco**: Foi utilizada a ferramenta Jacoco para medir a cobertura dos testes, configurando o Maven para gerar relatórios detalhados.  
🔹 **Correção de falhas nos testes existentes**: Ajustes na implementação da classe `Dip` para garantir que exceções sejam lançadas corretamente quando números inválidos são fornecidos.  
🔹 **Análise de cobertura de código**: Identificação de classes e métodos com menor cobertura, garantindo testes para cobrir mais casos e melhorar a qualidade do código.  
🔹 **Aplicação de regras de cobertura**: Definição de um limite mínimo de 90% de cobertura para garantir um alto nível de testes automatizados no projeto.  

## **5. Como Usar o Projeto**  

### **Executar Testes e Gerar Relatório de Cobertura com Jacoco**  
1. **Executar os testes unitários**  
   ```sh
   mvn clean test
   ```  
2. **Gerar relatório de cobertura de código**  
   ```sh
   mvn jacoco:report
   ```  
   - O relatório será gerado no diretório:  
     ```
    xdg-open target/site/jacoco/index.html     
    ```  

3. **Configurar uma regra para cobertura mínima de 90%**  
   - Adicionar a seguinte configuração no `pom.xml`:  
     ```xml
     <configuration>
         <rules>
             <rule>
                 <element>BUNDLE</element>
                 <limits>
                     <limit>
                         <counter>LINE</counter>
                         <value>COVEREDRATIO</value>
                         <minimum>0.90</minimum>
                     </limit>
                 </limits>
             </rule>
         </rules>
     </configuration>
     ```
4. **Verificar se a cobertura mínima foi atingida**  
   ```sh
   mvn jacoco:check
   ```  
   - Se a cobertura for inferior a 90%, o build falhará.  

## **6. Conclusão**  
Este projeto melhorou a qualidade do código e dos testes, garantindo maior confiabilidade no sistema de apostas. O uso do Jacoco foi essencial para medir e aumentar a cobertura de testes. 🚀