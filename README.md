# BookFlow

## 📖 Sobre o Projeto
Este projeto é uma aplicação web Full-Stack desenvolvida como um laboratório prático para consolidar conhecimentos em desenvolvimento back-end com **Java e Spring Boot**, evoluindo até a integração com um front-end moderno.

O objetivo principal não é apenas entregar um software funcionando, mas sim aplicar gradativamente as melhores práticas de mercado, como **Clean Code, princípios SOLID, segurança de APIs e arquitetura em camadas**. O projeto simula um ambiente de trabalho real, onde a arquitetura do sistema evolui fase a fase.

---

## 🚀 Como o projeto está sendo conduzido (Fases de Desenvolvimento)
O desenvolvimento foi estruturado em **5 Fases evolutivas**. Atualmente o projeto encontra-se na **Fase 1**

* **Fase 1: Setup e Integração Inicial (Spring Boot & SQL)**
  Criação da API RESTful básica, mapeamento de entidades com JPA/Hibernate e configuração do banco de dados relacional.
* **Fase 2: Lógica e Regras de Negócio**
  Implementação de validações robustas e fluxos de decisão complexos isolados na camada de Service.
* **Fase 3: Clean Code e Arquitetura**
  Refatoração do código, separação estrita de responsabilidades, uso de DTOs (Data Transfer Objects) e tratamento global de exceções.
* **Fase 4: Segurança da API**
  Configuração de CORS e implementação de Spring Security com autenticação e autorização via JWT (JSON Web Tokens).
* **Fase 5: Front-end (Next.js / React)**
  Desenvolvimento da interface de usuário consumindo a API de forma segura e gerenciando estados e tokens de acesso.

---

## 🛠️ Tecnologias e Frameworks Utilizados

### Back-end
* **Linguagem:** Java (Versão 21)
* **Framework Principal:** Spring Boot (4.0.5)
* **Acesso a Dados:** Spring Data JPA
* **Banco de Dados:** PostgreSQL
* **Segurança:** Spring Security & JWT
* **Ferramentas:** Lombok, Maven

### Front-end (Fase 5)
* **Framework:** Next.js / React
* **Estilização:** Tailwind CSS
* **Integração de API:** Axios

---

## ⚙️ Pré-requisitos para rodar o projeto localmente

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
* [Java JDK](https://www.oracle.com/java/technologies/downloads/) (21)
* [Maven](https://maven.apache.org/)
* [PostgreSQL](https://www.postgresql.org/download/)
* [Node.js e NPM](https://nodejs.org/) (Para o Front-end)
* Uma IDE de sua preferência (IntelliJ IDEA, VS Code, etc.)

---

## 🏃 Como rodar a aplicação

### 1. Clonando o repositório
```bash
git clone [https://github.com/](https://github.com/)<seu-usuario>/<nome-do-repositorio>.git

2. Configurando o Banco de Dados
Crie um banco de dados no seu SGBD local chamado <nome-do-banco>.

Acesse o arquivo src/main/resources/application.properties (ou .yml) e atualize as credenciais de acesso:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<nome-do-banco>
spring.datasource.username=<seu-usuario-do-banco>
spring.datasource.password=<sua-senha>

3. Rodando o Back-end
Pelo terminal, acesse a pasta raiz do projeto back-end e execute:

Bash
mvn spring-boot:run
A API estará rodando em http://localhost:8080

4. Rodando o Front-end (Em breve)
(Instruções serão adicionadas na Fase 5)

👨‍💻 Autor
David Hillesheim

Estudante de Desenvolvimento de Software

LinkedIn: https://www.linkedin.com/in/david-hillesheim-3b6b8a320/

E-mail: davidhillesheim6@gmail.com

⭐ Se este projeto te ajudou ou inspirou de alguma forma, sinta-se à vontade para deixar uma estrela no repositório!
