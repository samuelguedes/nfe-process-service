# nfe-process-service - Aplicação responsável por processar os arquivos de notas fiscais.

### Pré-Requisitos *obrigatórios*:
 - Git
 - JDK 11+
 - Maven 3.8.1
 - PostgreSQL

## Criar diretório input, output e erro nos caminhos indicados:

```shell script
Ex: "/home/user/arquivos/input" * "Lembrando que este diretório é o mesmo input do projeto nfe-upload-service"
Ex: "/home/user/arquivos/output"
Ex: "/home/user/arquivos/erro"
```

## Configurar no application.properties as variáveis abaixo:

```shell script
nfe-process-service.diretorio.input=/home/user/arquivos/input
nfe-process-service.diretorio.output=/home/user/arquivos/output/
nfe-process-service.diretorio.erro=/home/user/arquivos/erro/
```

## Rodar a aplicação em modo de desenvolvimento

## Maven
**Lembrando pré-requisito é o PostgreSQL já esta rodando e com BD nfe criado e schema public com permissões para o usuário postgres garantidos!**<br/>

Use o seguinte comando para iniciar o servidor:<br/>
<small>Obs: Esse comando deve ser executado na _raiz_ do projeto!</small>
```shell script
./mvnw clean compile quarkus:dev
```

## API disponível em:

```shell script
http://localhost:2000/api/
```

#### Alguns guias relacionados:
- Maven ([guia](https://maven.apache.org/what-is-maven.html))
- Hibernate ORM (Implementação do JPA) ([guia](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html))
- RESTEasy JAX-RS ([guia](https://docs.jboss.org/resteasy/docs/3.0.9.Final/userguide/html_single/index.html))
- PostgreSQL ([guia](https://www.postgresql.org/docs/))
