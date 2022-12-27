<div align="center">
  <img src="assets/logo-api.png" alt="Sonatel API" width="320">
  <h1>Welcome to the Sonatel APIs Spring boot SDK repository</h1>
  <h6>Made with ❤️ &nbsp;by developers for developers</h6>
</div>
<br>

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/sn.sonatel.api/sonatel-api-springboot-parent/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/sn.sonatel.api/sonatel-api-springboot-parent)

### 📃 Description

The official Sonatel API SDK Java/Spring boot client.
The SDK makes it quick and easy to build Sonatel APIs integration and adoption by :

- managing under the hood token management
- pincode encryption
- request building
- error handling
- configuration

### 🚀 Getting started

1 - Add SDK dependency

```xml
        <dependency>
            <groupId>sn.sonatel.api</groupId>
            <artifactId>sonatel-api-springboot-starter</artifactId>
            <version>${sdk.version}</version>
        </dependency>
```

2 - 🔑 Configure my credentials

```yaml

sonatel:
    security:
        client-id: <put_your_client_id>
        client-secret: <put_your_client_secret>
    my-msisdn: 77xxxxxxx
    my-pin-code: 1234

```

3 - Usage (ex: cashin request)

```java
    var request = TransactionRequest.forAmountAndCustomer(2f, "786258731");
    var response = transactionService.cashIn(request);
```

### Requirements

Java 11+

### License

-------
This project is licensed under [the Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).