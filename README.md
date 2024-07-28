# Book.Catalog.Api

Este documentação contém as diretrizes iniciais para uso da aplicação

## Guide Development

### Configure Data base
Você precisa de um servidor de dados MongoDB
Para configurar a base na aplicação, basta informar a URI da base na variável de ambiente : ```spring.data.mongodb.uri

Script View:
db.createView( "ViewLivro", "Livro",
[
  {
    $lookup: {
      from: "Autor",
      localField: "Autores._id",
      foreignField: "_id",
      as: "AutoresLivro"
    }
  },
  {
        $unwind: {
            path: "$AutoresLivro"
        }
    },
  {
    $lookup: {
      from: "Assunto",
      localField: "Assuntos._id",
      foreignField: "_id",
      as: "AssuntoLivro"
    }
  },
    {
        $unwind: {
            path: "$AssuntoLivro"
        }
    },
  {
    $project: {
      _id: 0,
      titulo: 1,
      edicao: 2,
      anoPublicacao: 3,
      AutoresLivros: "$AutoresLivro.Nome",
      AssuntoLivros: "$AssuntoLivro.Descricao"
    }
  }
]);

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
