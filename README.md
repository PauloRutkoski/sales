# Sales
<p>
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" /> 
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
<img src="https://img.shields.io/badge/Kakfa-c3c3c3?style=for-the-badge&logo=Apache&logoColor=black" />
</p>
### This project is an example of event-driven microservices with separate databases to each other which use Apache Kafka for synchronization.

---

The Sales project is constituted by 4 microservices, which are:

* Gateway: Receives all requests and direct them to the correct microservice.
* Orders: Responsible for creating orders with 1 Person and some Products.
* Person: Responsible for managing people's register.
* Products: Responsible for managing products's register.

> Orders, Person and Products have their own databases. Products and Person send data through Apache Kafka, and then Orders database replicate this data on its own database.

---

## Getting Started

First of all, you need docker to start the databases and Apache Kafka. For this, run the following command on the root directory:

```` 
docker-compose up -d
````

> Some data will be generated automatically to you start testing.

It will start the needed dependencies.
After that, run the 4 microservices through your prefered IDE, just let the Gateway lastly.

The Gateway microservice will be avaiable at
```
http://localhost:9090/
```

----

## Endpoints 

Through the gateway microservice you can access all the other 3:

* **Orders:** /orders/
* **Person:** /person/
* **Products:** /products/

All microservices have the same endpoints

* **GET** - / - Return all your records.
* **GET** - /{*ID*} - Return a record with the specified Id.
* **POST** - / - Insert a new record.
* **PUT** - /{*ID*} - Update a record with the specified Id.

---

## Structure Examples

### Person
```
{
    "name":"Jason",
    "document":"454568417854",
    "id": null
}
```

### Product
```
{
  "id": null,
  "reference": "0045",
  "name": "Test2",
  "brand": "Brand 3",
  "price": 3220
}
```

### Order

```
{
  "id": null,
  "person": {
    "id": 1,
    "name": "Pedro dos Santos",
    "document": "68821017010"
  },
  "products": [
    {
      "id": null,
      "product": {
        "id": 1,
        "reference": "0001",
        "name": "TV 42",
        "brand": "LG",
        "price": 2000
      },
      "quantity": 2,
      "order": null
    },
    {
      "id": null,
      "product": {
        "id": 2,
        "reference": "0002",
        "name": "Notebook",
        "brand": "Dell",
        "price": 3000
      },
      "quantity": 4,
      "order": null
    },
    {
      "id": null,
      "product": {
        "id": 3,
        "reference": "0003",
        "name": "Smartphone",
        "brand": "Samsung",
        "price": 1900
      },
      "quantity": 1,
      "order": null
    }
  ]
}
```


