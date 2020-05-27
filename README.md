# BookStore

1.Built a spring boot based rest endpoints for performing following operations :

*Adding a book to the store 
Rest Request:
curl --location --request POST 'http://localhost:10222/books' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=552940D011A5EE33009338278C51E04C' \
--data-raw '{
	"title":"Hello",
	"isbn":"123456789",
	"author":"ABC",
	"price":1000.00,
	"edition":1
	
}'


*Searching book based on Title
Rest Request:
curl --location --request GET 'http://localhost:10222/books/search?title=Hello' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Cookie: JSESSIONID=C3B49E045F32987EF8DE6DE6874DDF5B'



*Searching book based on ISBN
Rest Request:
curl --location --request GET 'http://localhost:10222/books/search?isbn=123456789' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Cookie: JSESSIONID=C3B49E045F32987EF8DE6DE6874DDF5B'



*Searching book based on Author
Rest Request:
curl --location --request GET 'http://localhost:10222/books/search?author=William' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Cookie: JSESSIONID=C3B49E045F32987EF8DE6DE6874DDF5B'



*Searching media coverage of book given its ISBN
Rest Request:
curl --location --request GET 'http://localhost:10222/books/getMediaCoverage?isbn=123456789' \
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
--header 'Cookie: JSESSIONID=C3B49E045F32987EF8DE6DE6874DDF5B' \
--data-raw ''



*Buying a Book by user
Rest Request:
curl --location --request POST 'http://localhost:10222/books/buy' \
--header 'Authorization: Basic dXNlcjpwYXNzd29yZA==' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=C3B49E045F32987EF8DE6DE6874DDF5B' \
--data-raw '{
"title":"Hello",
"author":"ABC"
}'


2 Authentication over EndPoint:
All the endpoints have Basic Authentication with two types of users Admin and Users 
API Acessed By only Admin : Adding a book to the store (http://localhost:10222/books)
API Acessed By only USER : Buying a Book by user (http://localhost:10222/books/buy)
Rest all Apis are acessed by both Admin and User
Default Password is "password"
Username of admin "admin"
Username of user "user"


3. Tried to make Api as Non Blocking IO Calls using CompletableFuture
4.Handled Case like no two users can buy book parallely if the capacity of book is 1.
5 Used MySql as Database

6.Below are the steps to build your docker container:
