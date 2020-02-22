# XYZ-API
## Teksystems assessment
by Ethel Salazar | cel: (312) 522-1590 | email: salazarethel3010@gmail.com

## Settings

Under src > main > resources folder edit **application.properties** file, variable:
```
api.key.zipcode = _p5dIK65I5qEQtN28zb8AjckxwzIQYlq4RDL2rrZi94gqBZPUmOpX6mUbJDUkzDIp_
```
and provide a fresh API key value before launch and run this project (https://www.zipcodeapi.com/API#zipToLoc)

## Endpoints
### Endpoint Create new Contact
```
http://localhost:8080/contact/
```
### Endpoint Update Contact
```
http://localhost:8080/contact/
```
### Endpoint Search Contact By Id
```
http://localhost:8080/contact/{id}
```
### Endpoint Search Contact By First Name, Last Name, ZipCode, Params are not required
```
http://localhost:8080/contact/?lastName=?&zipCode=?&firstName=?
```
### Endpoint Delete Contact By Id
```
http://localhost:8080/contact/{id}
```
## Test coverage
You can find the screenshots under **Test Print** folder (Integration Test Dao, Unit test Controllers and Services, and System Test from Postman)

Current line code coverage: **93%**

