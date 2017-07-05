bInTime Test Assignment
===

## Technologies:

* Java 8
* Spring 
    * Core
    * MVC
    * Boot
* JPA
* Multithreading
* Maven
* MySQL

## SQL

DDL script: `sql/create_tables.sql`

Dump after program testing: `sql/value_count_dump.sql`

## Steps for Run: 

Build executable jar:

    mvnw spring-boot:repackage

Start service:

    mvnw spring-boot:run

Stop service:

    mvnw spring-boot:stop
   
## Endpoints:
   
* Parsing endpoint:

    * Request:       
        ```
        POST /parse
        Content-type: multipart/form-data
        Body:    
        files: <your-content>
        ```
    
    * Response:
    
        ```
        {
            "jobId": <generated-job-id> 
        }
        ```
        
* Result endpoint:

    * Request:
        ```
        GET /res/<job-id>
        ```
        
    * Response:
        ```
        [
            {
                "value": <string>,
                "count": <int>
            }
            ...
        ]    
        ```
    