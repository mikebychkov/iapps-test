XML validate & parse service
-
- Java 17, Spring-Boot, MongoDB
- Check the pdf task for full description

Attention:
-
- In order to run this service you must have Java 17 installed on your computer.
- Have Docker & Docker-compose on your computer.

Launch:
-
- ./test.sh - to run tests (Ports: 8080, 27217 - should not be in use)
- ./start-local.sh - to launch service app, this script also run tests before launching app (Ports: 8080, 27017 - should not be in use)

Request Examples:
-
- POST: http://localhost:8080/xml (File attached as form-multipart-data)
- GET: http://localhost:8080/xml - get all parsed xml data
- GET: http://localhost:8080/xml?newspaperName=ABC - get all parsed xml data where newspaperName equals "ABC"

Filter request params:
-
- newspaperName
- screenWidth
- screenHeight
- screenDpi

Response Example:
-
```json
{
"content": [
{
"id": "64ac435f1e6e0b42cbc6831d",
"uploadTime": "2023-07-10T17:43:59.3",
"fileName": "request.xml",
"newspaperName": "abb",
"screenWidth": 1280,
"screenHeight": 752,
"screenDpi": 160
}
],
"pageable": {
"sort": {
"empty": true,
"sorted": false,
"unsorted": true
},
"offset": 0,
"pageSize": 20,
"pageNumber": 0,
"paged": true,
"unpaged": false
},
"totalElements": 1,
"totalPages": 1,
"last": true,
"size": 20,
"number": 0,
"sort": {
"empty": true,
"sorted": false,
"unsorted": true
},
"numberOfElements": 1,
"first": true,
"empty": false
}
```

For the xsd schema used online xsd generator based on xml example listed in pdf.