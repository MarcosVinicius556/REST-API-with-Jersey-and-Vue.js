# Sobre o projeto
Sistema de cadastro de funcionários e setores, desenvolvido com Java e Jersey para a construção da API RESTful que ficará responsável por atender as requisições. Nele foram utilizados alguns patterns como o Builder para facilitar a construção de objetos grandes, Factory para poder utilizar da inversão de dependência e facilitar a criação de objetos com compotamentos similares, como é o caso dos 'DAO' desenvolvidos e DTO para ter uma maior flexibilidade e segurança na hora de trafegar os dados. Já o front-end foi desenvolvido com html, css e js, porém utilizando de algumas diretivas de Vue.js e bootstrap para facilitar a construção da interface, para as requisições foi utilizado a API axios.

# Sobre as requisições
O sistema é capaz de realizar a busca, inserção, alteração e exclusão tanto de funcionários quanto setores, abaixo segue as rotas disponíveis:

* **Funcionários**
    *Salvar - http://localhost:8080/funcionarios/rest/funcionarios/salvar
    *Buscar pelo ID - http://localhost:8080/funcionarios/rest/funcionarios/buscar/{id}
    *Listar Todos - http://localhost:8080/funcionarios/rest/funcionarios/atualizar
    *Deletar - http://localhost:8080/funcionarios/rest/funcionarios/deletar/{id}
    *Teste - http://localhost:8080/funcionarios/rest/funcionarios/teste

* **Setores**
    *Salvar - http://localhost:8080/funcionarios/rest/setores/salvar
    *Buscar pelo ID - http://localhost:8080/funcionarios/rest/setores/buscar/{id}
    *Listar Todos - http://localhost:8080/funcionarios/rest/setores/atualizar
    *Deletar - http://localhost:8080/funcionarios/rest/setores/deletar/{id}
    *Teste - http://localhost:8080/funcionarios/rest/setores/teste


* Para exemplos de requisições verificar o arquivo *Testes Processo - Seletivo (HEPTA).postman_collection*.

# Como subir o projeto e os testes?

* Para rodar o projeto será necessário as seguintes ferramentas.
    * 1. Tomcat 9
    * 2. Jdk 8
    * 3. MySql*

* Para a instalação do banco basta apenas executar o script *SQL_CRIACAO_DATABASE.sql

* Para testar aplicação acessar o serviço: http://localhost:8080/funcionarios/rest/funcionarios/teste

* Para os testes unitários basta apenas rodar o projeto como um TestCase de JUnit

# Tecnologias utilizadas

* Java
* Vue.js
* Hibernate
* Jersey
* HTML, CSS, JS
* BootStrap
* JUnit
* TOMCAT 9
* Fetch API
* Axios

# Links relacionados

* Para organização do desenvolvimento do projeto foi utilizado o Trello, podendo ser acessado pelo link a seguir:
    * [Trello - Processo - Seletivo](https://trello.com/invite/b/J5JRa1jN/ATTI0b059748db92e22faf4a20f64eaea2c6FB4731AB/processo-seletivo-hepta)

