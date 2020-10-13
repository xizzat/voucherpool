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
2. git clone https://github.com/xizzat/voucherpool.git
3. cd voucherpool/
4. ./gradlew clean bootRun

## Run testing
1. ./gradlew test

## Documentation (Swagger)
```http://localhost:8080/swagger-ui/```

## Steps
1. The api can be used directly from swagger ui
2. Create recipient by POST /api/recipient
3. Create specialOffer by POST /api/special-offer
- if applyAll is enable, all existing recipient will be assigned a voucher for this offer
- if applyAll is disable, specialOffer is created but not assigned to recipient
4. Call /api/recipient/{email}/voucher to get list of valid voucher for specific recipient
5. Call /api/voucher/{code}/recipient/{email} to redeemed voucher
- if expiry and unused is valid, the percentage will return as a response
- same voucher only can be redeemed once
6. Voucher duration can be configured inside application.properties
```
#VoucherPool Config
boost.voucher.duration=60
```
