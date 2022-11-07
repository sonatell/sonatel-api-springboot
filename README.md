# Sonatel APIs Spring Boot Library

### 🚀 Getting started

1 - Add SDK dependency

```xml
        <dependency>
            <groupId>sn.sonatel.api</groupId>
            <artifactId>sonatel-api-springboot</artifactId>
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

3 - Example (doing cashin request)

```java
    var request = TransactionRequest.builder()
            .amount(2000f)
            .customerMsisdn("771234567")
            .build();
    var response = transactionService.cashIn(request);
```

2 - Required

Java 11+
