# Person Connection


Run the maven project using ide like IntelliJ
Create a maven run config: spring-boot:run

Update path in PersonData.java to grab your file.
static final String PERSON_FILE="/Users/kdsouza1/Downloads/Person[1].txt";
static final String RELATIONSHIP_FILE="/Users/kdsouza1/Downloads/Relationship[1].txt";


Comments/questions:
1) Who can introduce user id=X to user id=Y?
Ive picked the common 1st degree person between X and Y

2) Which connections are common between user id=X and user id=Y?
Ive picked all common persons between X and Y based on degree

3) Pending TODOs
Grab file path from application.properties
Add unit tests

Please let me know if if (1) and (2) are correct and you'd like to see unit tests?


Api Calls:

* Get a user by id
* eg http://localhost:8080/rest/persons/3

* Get the connections from user id=X
* eg http://localhost:8080/rest/personRelations?id=1&degree=4

* How many total connections  does user id=X has?
* eg:  http://localhost:8080/rest/personRelations/total?id=1&degree=2

* Who can introduce user id=X to user id=Y?
* eg: http://localhost:8080/rest/commonRelations?firstPerson=1&secondPerson=2

* Which connections are common between user id=X and user id=Y?
* eg http://localhost:8080/rest/commonConnections?firstPerson=1&secondPerson=8&degree=2

* Which user has the most connections?
* http://localhost:8080/rest/personRelations/max/?degree=4


* Which user has the least connections?
* http://localhost:8080/rest/personRelations/min/?degree=4

