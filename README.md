# User Service

### You can find all services in Swagger [http://localhost:8080/swagger-ui.html]

### How To Run

Requirements:
* Maven
* JDK 8
```
mvn package
java -jar user-service-0.0.1-SNAPSHOT.jar
```


### Database
I'm using H2(file mode) as a database. You can find tables in H2 console [http://localhost:8080/h2-console]

I'm using some configuration(like headers().frameOptions().disable(), .csrf().disable()) in spring security to support development of a Spring Boot web application and enable access to the H2 database console.
We must remove them on production.

### Test
You can find lots of tests for all services and APIs in the test package.

### Sample Requests

1- Create a user

```
curl -X POST \
  http://localhost:8080/users \
  -u 'admin:asdf1234'\
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"lastName": "khoshtarash",
	"firstName": "mohammadjavad",
	"phoneNumbers": ["143-7654321", "123-7654321"],
	"emails": ["mohammadjavadkho@gmail.com"]
}'
```


2- Get user by id

```
curl -X GET \
  http://localhost:8080/users/1 \
  -u 'admin:asdf1234'\
  -H 'cache-control: no-cache'
```

3- Get user by name

```
curl -X GET \
  http://localhost:8080/users?name=khoshtarash \
  -u 'admin:asdf1234'\
  -H 'cache-control: no-cache'
```

4- Add new mail

```
curl -X POST \
  http://localhost:8080/users/1/emails \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -u 'admin:asdf1234'\
  -d '{
	"email": "mohammadjavadkho1@gmail.com"
}'
```

5- Add new phone number

```
curl -X POST \
  http://localhost:8080/users/1/phones \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -u 'admin:asdf1234'\
  -d '{
	"phoneNumber": "123-4567895"
}'
```

6- Update email

```
curl -X POST \
  http://localhost:8080/users/1/emails/1448964796 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -u 'admin:asdf1234'\
  -d '{
	"email": "mohammadjavadkho123@gmail.com"
}'
```

7- Update phone number

```
curl -X POST \
  http://localhost:8080/users/1/phones/1307696836 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -u 'admin:asdf1234'\
  -d '{
	"phoneNumber": "123-5567895"
}'
```

8- Delete User

```
curl -X DELETE \
  http://localhost:8080/users/1 \
  -H 'cache-control: no-cache' \
  -u 'admin:asdf1234'
```
