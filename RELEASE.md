# Release notes

## version 2.0.0

major features :

- Web payment API (pay by otp code)
- Get Retailer balance API
- Get merchant balance API

## version 1.2.0

major features :

- Cashin API
- Get Balance API
- Get Public Key

## Upgrading from version 1.2.0 

```java
   Float getBalance(); // method is replaced by more meaningful one : getRetailerBalance() or getMerchantBalance()
```


```yaml

# configuration changed

# from 

sonatel:
  my-msisdn: 77xxxxxxx
  my-pin-code: 1234

# to
sonatel:
    retailer:
        msisdn: 7xxxxxxxx
        pin-code: XXXX
```