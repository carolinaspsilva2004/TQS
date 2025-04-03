# **Load Testing com k6 - QuickPizza API**

Este projeto realiza testes de carga na API do QuickPizza usando a ferramenta **k6**. O objetivo é avaliar o desempenho da API, analisando métricas como tempo de resposta, número de requisições e falhas.

## **Configuração e Execução**

### **1. Clonar o Repositório**
```bash
git clone https://github.com/grafana/k6-oss-workshop.git
cd k6-oss-workshop
```

### **2. Iniciar a Aplicação com Docker**
```bash
docker compose up -d
```
Isso inicia três containers:
- **QuickPizza API** (aplicação-alvo)
- **Grafana** (visualização de métricas)
- **Prometheus** (coleta de métricas)

### **3. Instalar o k6**
#### **Linux (Ubuntu/Debian)**
```bash
sudo apt update
sudo apt install -y gpg
curl -fsSL https://dl.k6.io/key.gpg | sudo gpg --dearmor -o /usr/share/keyrings/k6-archive-keyring.gpg
echo "deb [signed-by=/usr/share/keyrings/k6-archive-keyring.gpg] https://dl.k6.io/deb stable main" | sudo tee /etc/apt/sources.list.d/k6.list
sudo apt update
sudo apt install -y k6
```
``

### **4. Executar o Teste**
```bash
k6 run test.js
```

---

## **Resultados e Métricas**

### **⏳ Tempo de resposta da API**
- **Média**: `88.12ms`
- **Mínimo**: `88.12ms`
- **Máximo**: `88.12ms`
- **Percentil 90**: `88.12ms`
- **Percentil 95**: `88.12ms`

### **📌 Número de requisições**
- **Total de requisições enviadas**: `1`

### **❌ Número de falhas**
- **Requisições com erro (status ≠ 200)**: `0`
- **Percentual de falha**: `0.00%`

### **📊 Outras métricas**
- **Iterações concluídas**: `1`
- **Usuários Virtuais (VUs)**: `1`
- **Duração total da iteração**: `1.09s`

---

## **Conclusão**
O teste foi bem-sucedido, com todas as requisições respondendo corretamente (**status 200**) e um tempo médio de resposta de **88.12ms**. Para testar cenários mais complexos, podemos aumentar o número de usuários virtuais (**vus**) e iterações no script.

