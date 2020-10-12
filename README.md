# Voucher Pool

A voucher pool is a collection of (voucher) codes that can be used by customers (recipients) to get discounts in a
web shop. Each code may only be used once and we would like to know when it was used by the recipient. Since
there can be many recipients in a voucher pool, we need a call that auto-generates voucher codes for each recipient.

- Kotlin Java
- Spring Boot 2.3
- MongoDB

## Prerequisite
1. mongo db running at localhost:27017
2. java 11

## To run
1. start mongodb
2. cd voucherpool/
3. ./gradlew clean bootRun

## Documentation (Swagger)
```http://localhost:8080/swagger-ui/```
