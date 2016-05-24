Crossover - Medical Journals Publication And Subscription System
=======

A web portal where publishers can manage a list of medical journals and public users can subscribe to journals of their interest.

 _____ ______ _____ _____ _____  _____  _   _ ___________                   
/  __ \| ___ \  _  /  ___/  ___||  _  || | | |  ___| ___ \                  
| /  \/| |_/ / | | \ `--.\ `--. | | | || | | | |__ | |_/ /                  
| |    |    /| | | |`--. \`--. \| | | || | | |  __||    /                   
| \__/\| |\ \\ \_/ /\__/ /\__/ /\ \_/ /\ \_/ / |___| |\ \                   
 \____/\_| \_|\___/\____/\____/  \___/  \___/\____/\_| \_|                  
                                                                            
                                                                            
___  ___         _ _           _     ___                              _     
|  \/  |        | (_)         | |   |_  |                            | |    
| .  . | ___  __| |_  ___ __ _| |     | | ___  _   _ _ __ _ __   __ _| |___ 
| |\/| |/ _ \/ _` | |/ __/ _` | |     | |/ _ \| | | | '__| '_ \ / _` | / __|
| |  | |  __/ (_| | | (_| (_| | | /\__/ / (_) | |_| | |  | | | | (_| | \__ \
\_|  |_/\___|\__,_|_|\___\__,_|_| \____/ \___/ \__,_|_|  |_| |_|\__,_|_|___/


## Frontend

The project uses the following frameworks:

- [AngularJS] (https://angularjs.org/) - AngularJS is a structural framework for dynamic web apps.
- [Bootstrap] (http://getbootstrap.com/) - Bootstrap is the most popular HTML, CSS, and JS framework for developing responsive, mobile first projects on the web.
- [Font awesome] (http://fortawesome.github.io/Font-Awesome/) - The iconic font and CSS toolkit.

## Backend

The project uses the following frameworks:

- [Dropwizard] (http://www.dropwizard.io/) - Dropwizard is a Java framework for developing ops-friendly, high-performance, RESTful web services.
- [Hibernate ORM] (http://hibernate.org/) - Hibernate ORM is an object-relational mapping framework.
- [Liquibase] (http://www.liquibase.org/) - Liquibase is an open source database-independent library for tracking, managing and applying database schema changes.
- [Maven] (https://maven.apache.org/) - Apache Maven is a software project management and comprehension tool.
- [PostgreSQL] (http://www.postgresql.org/) - PostgreSQL is a powerful, open source object-relational database system.

## Setup

* The configuration file is the "conf/server.yml".

### Upload directory

    $ mkdir /tmp/medicaljournals-bucket/
    $ chmod 775 /tmp/medicaljournals-bucket/

### Database Configuration

The database is PostgreSQL. Once you're in PSQL, just run the following command to create the Database:

    CREATE DATABASE medicaljournals;

You will need to define the user and password:

    CREATE USER medicaljournals PASSWORD '123456';

## Build and Run

Compile the Medical Journals Application:

    $ mvn package

Create the database schema:

    $ java -jar target/crossover-medical-journals-0.0.1-SNAPSHOT.jar db migrate conf/server.yml

Start the server:

    $ java -jar target/crossover-medical-journals-0.0.1-SNAPSHOT.jar server conf/server.yml

To see the application, go to your web browser and access:

    http://localhost:8080
