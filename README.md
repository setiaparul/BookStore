# BookStore

->.Built a spring boot based rest endpoints for performing following operations :

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


-> Authentication over EndPoint:
All the endpoints have Basic Authentication with two types of users Admin and Users 
API Acessed By only Admin : Adding a book to the store (http://localhost:10222/books)
API Acessed By only USER : Buying a Book by user (http://localhost:10222/books/buy)
Rest all Apis are acessed by both Admin and User
Default Password is "password"
Username of admin "admin"
Username of user "user"


->. Tried to make Api as Non Blocking IO Calls using CompletableFuture

->.Handled Case like no two users can buy book parallely if the capacity of book is 1.

-> Used MySql as Database

->.Below are the steps to build your docker container:

1. Setup MySql 
CMD: docker run -d -p 6033:3306 --name=docker-mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=book_manager" mysql

2 docker image ls 
You will see mysql image 

3.check by logging in to MySQL.
CMD: docker exec -it docker-mysql bash;

4.login to mysql using 
CMD: mysql -uroot -p

5.Then CMD: show databases;
to verify your book_manager database.

6.We can externally use this database from our host machine by using port 6033.

7.Now we have to import the database script to the Docker MySQL database.

8.The SQL script is available (https://github.com/setiaparul/BookStore/blob/master/sql/bookstore.sql)

9.Run the following command to import this script to docker-mysql. 
CMD: docker exec -i docker-mysql mysql -uroot -proot book_manager <book_manager.sql

10.You can confirm by logging into container and checking if table is created or not.

11. Now open the Dockerfile.This file contains sequential commands to execute in docker. 
Now we will build a docker image by using this Dockerfile. 
CMD: docker build -f Dockerfile -t book_store_app .

12. This command will create a Docker image named book_store_app to the Docker machine. 

13.Now we will run this image as a container.
CMD: docker run -t --link docker-mysql:mysql -p 10222:10222 book_store_app

The --link command will allow the book_store_app container to use the port of MySQL container

After running this command, we will hit a api to add a book using admin as user 
curl --location --request POST 'http://localhost:10222/books'
--header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ='
--header 'Content-Type: application/json'
--header 'Cookie: JSESSIONID=552940D011A5EE33009338278C51E04C'
--data-raw '{ "title":"Hello", "isbn":"123456789", "author":"ABC", "price":1000.00, "edition":1

}'

This will successfully add book in db.
