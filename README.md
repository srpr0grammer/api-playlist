<br>
<h1 align="center">
PlaylistAPI + Docker.
</h1>
<br>

## 💬 Sobre o repositório.

Repositório onde se consiste em um CRUD de cliente.
## ⚠ Pré-requisitos para execução do projeto

* docker e docker-composer instalado

## 📌 Como usar ?
Primeiro, vamos construir a aplicação para que o .jar possa ser gerado.
```
mvn clean install -DskipTests
```
Após a construção estar completa, basta executar a aplicação.
```
docker-compose up
```
## 🧪 Como Testar a API ?
A documentação da API e a interface para testar os endpoints estão disponíveis através do Swagger UI
```
Acessar Swagger UI: Uma vez que a aplicação está rodando, você pode acessar o Swagger UI pelo navegador 
utilizando a seguinte URL:

http://localhost:8080/swagger-ui.html

Nota: Certifique-se que a aplicação está rodando na porta 8080, ou ajuste a URL conforme necessário.
```

```
Testar Endpoints: No Swagger UI, você verá uma lista de todos os endpoints disponíveis. Selecione um endpoint para ver
os detalhes e clique em "Try it out" para testá-lo. Preencha os parâmetros necessários (se aplicável) e clique em "Execute"
para realizar a requisição HTTP.
```
