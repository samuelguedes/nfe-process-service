# nfe-process-service - Aplicação responsável por processar os arquivos de notas fiscais.

### Pré-Requisitos *obrigatórios*:
 - Git
 - JDK 11+
 - Maven 3.8.1
 - PostgreSQL

## Rodar a aplicação em modo de desenvolvimento

## Maven
**Lembrando pré-requisito é o PostgreSQL já esta rodando e com BD nfe criado e schema public com permissões para o usuário postgres garantidos!**<br/>

Use o seguinte comando para iniciar o servidor:<br/>
<small>Obs: Esse comando deve ser executado na _raiz_ do projeto!</small>
```shell script
./mvnw clean compile quarkus:dev
```


#### Alguns guias relacionados:
- Maven ([guia](https://maven.apache.org/what-is-maven.html))
- Hibernate ORM (Implementação do JPA) ([guia](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html))
- RESTEasy JAX-RS ([guia](https://docs.jboss.org/resteasy/docs/3.0.9.Final/userguide/html_single/index.html))
- PostgreSQL ([guia](https://www.postgresql.org/docs/))
