# Book.Catalog

Este documentação contém as diretrizes iniciais para uso da aplicação

## Guide Development

### Configure Data base
Você precisa de um servidor de dados MongoDB
Para configurar a base na aplicação, basta informar a URI da base na variável de ambiente : ```spring.data.mongodb.uri

### Start application

```bash
mvn  spring-boot:run  
```
 
### Run Component Test

```bash
mvn clean verify
``` 

## Guide Build

### Step to deploy
Para realizar o deploy da aplicação em kubernetes

```bash
mvn clean  package -DskipTests -X
docker build . -t book-catalog
kubectl apply -f k8sdeploy.yaml
``` 