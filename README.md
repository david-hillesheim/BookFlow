# 📚 BookFlow

## 📖 Sobre o Projeto
O **BookFlow** é uma aplicação web Full-Stack desenvolvida como laboratório prático para consolidar conhecimentos em desenvolvimento back-end com **Java e Spring Boot**, evoluindo progressivamente até a integração com um front-end moderno.

Mais do que entregar um software funcional, o foco do projeto é aplicar boas práticas de mercado, incluindo:

- Clean Code  
- Princípios SOLID  
- Arquitetura em camadas  
- Segurança de APIs  

O projeto simula um ambiente real de desenvolvimento, com evolução incremental da arquitetura a cada fase.

---

## 🚀 Fases de Desenvolvimento

O projeto está estruturado em **5 fases evolutivas**:

- ✅ **Fase 1: Setup e Integração Inicial**  
  Criação da API RESTful, mapeamento de entidades com JPA/Hibernate e integração com banco de dados.

- 🔄 **Fase 2: Lógica e Regras de Negócio**  
  Implementação de validações robustas e regras de negócio na camada de Service.

- ⏳ **Fase 3: Clean Code e Arquitetura (Atual)**  
  Refatoração, uso de DTOs e tratamento global de exceções.

- ⏳ **Fase 4: Segurança da API**  
  Implementação de CORS, autenticação e autorização com JWT.

- ⏳ **Fase 5: Front-end**  
  Desenvolvimento da interface com React/Next.js e integração com a API.

---

## 🛠️ Tecnologias Utilizadas

### 🔙 Back-end
- Java 21  
- Spring Boot 3.4.1  
- Spring Data JPA  
- PostgreSQL (Supabase)  
- Maven  
- Lombok  

### 🎨 Front-end (Futuro)
- Next.js / React  
- Tailwind CSS  
- Axios  

---

## ⚙️ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- Java JDK 21  
- Maven  
- Node.js e NPM *(necessário apenas na Fase 5)*  
- IDE (IntelliJ, VS Code, etc.)  

---

## 🏃 Como Rodar a Aplicação

### 1. Clonar o repositório
```bash
git clone https://github.com/david-hillesheim/BookFlow
```

### 2. Configurar variáveis de ambiente

O banco de dados está hospedado no Supabase. Configure as seguintes variáveis de ambiente:
```bash
DB_URL=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0
DB_USERNAME=postgres.torvyxktjroqkpzgibpg
DB_PASSWORD=5xethvu2zaFshArg
```
No application.properties, utilize:
```bash
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

### 3. Rodar o Back-end
```bash
mvn spring-boot:run
```
A API estará disponível em:
```bash
http://localhost:8080
```
### 4. Rodar o Front-end

(Disponível a partir da Fase 5)

👨‍💻 Autor

David Hillesheim
Estudante de Desenvolvimento de Software

LinkedIn: https://www.linkedin.com/in/david-hillesheim-3b6b8a320/

Email: davidhillesheim6@gmail.com

⭐ Se este projeto te ajudou ou inspirou, considere deixar uma estrela no repositório!

