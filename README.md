Medical Journals Publication And Subscription System
=======

A web portal where publishers can manage a list of medical journals and public users can subscribe to journals of their interest.

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

## Screenshots

- [ScreenShot-1.png](docs/ScreenShot-1.png)
- [ScreenShot-2.png](docs/ScreenShot-2.png)
- [ScreenShot-3.png](docs/ScreenShot-3.png)
- [ScreenShot-4.png](docs/ScreenShot-4.png)
- [ScreenShot-5.png](docs/ScreenShot-5.png)

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


## Missing Requirements

Some features were not completely implemented.

- Sign In
	Backend: 100%
	Frontend: 0%

- Sign Up
	Backend: 100%
	Frontend: 0%

- Upload
	Backend: 90%
	Frontend: 50%

- Subscription
	Backend: 0%
	Frontend: 50%


## API

### Authenticate (PUBLISHER)

- **POST** [http://localhost:8080/api/users/authenticate](http://localhost:8080/api/users/authenticate)
- **Accept:** application/json
- **Content-Type:** application/json

```json
	{
		"email": "publisher@crossover.com",
		"password": "123"
	}
```
**Response:**

- **200** OK

```json
	{
	    "id": 1,
	    "name": "Publisher",
	    "email": "publisher@crossover.com",
	    "password": "$2a$10$iwO9vH6m4h6I.9pnuaUo4u19mUKXuG6mm3oKE9iT525fQ/Vf5jA7K",
	    "role": "PUBLISHER",
	    "token": "94ad34b1de8bd56172a7bde237bf4470db305cdd"
	}
```

### Authenticate (SUBSCRIBER)

- **POST** [http://localhost:8080/api/users/authenticate](http://localhost:8080/api/users/authenticate)
- **Accept:** application/json
- **Content-Type:** application/json

```json
	{
		"email": "subscriber@crossover.com",
		"password": "123"
	}
```

**Response:**

- **200** OK

```json
	{
	    "id": 2,
	    "name": "Subscriber",
	    "email": "subscriber@crossover.com",
	    "password": "$2a$10$fVwdoMyHsXqIQT7OYeXzKuc4b7JONpfnAJBstV/pIRQ08eP.J1d8i",
	    "role": "PUBLISHER",
	    "token": "689251e80cdd91301b069923a314631bef431063"
	}
```

### List all users (Empty Token)

- **GET** [http://localhost:8080/api/users](http://localhost:8080/api/users)
- **Accept:** application/json
- **Content-Type:** application/json

**Response:**

- **401** Unauthorized

```json
	{
	    "code": "401",
	    "message": "Unauthorized"
	}
```

### List all users (SUBSCRIBER Token)

- **GET** [http://localhost:8080/api/users](http://localhost:8080/api/users)
- **Accept:** application/json
- **Content-Type:** application/json
- **Authorization:** 689251e80cdd91301b069923a314631bef431063

**Response:**

- **403** Forbidden

```json
	{
	    "code": "403",
	    "message": "Forbidden"
	}
```

### List all users (PUBLISHER Token)

- **GET** [http://localhost:8080/api/users](http://localhost:8080/api/users)
- **Accept:** application/json
- **Content-Type:** application/json
- **Authorization:** 94ad34b1de8bd56172a7bde237bf4470db305cdd

**Response:**

- **200** OK

```json
	[
	    {
	        "id": 2,
	        "name": "Subscriber",
	        "email": "subscriber@crossover.com",
	        "password": "$2a$10$fVwdoMyHsXqIQT7OYeXzKuc4b7JONpfnAJBstV/pIRQ08eP.J1d8i",
	        "role": "SUBSCRIBER",
	        "token": "689251e80cdd91301b069923a314631bef431063"
	    },
	    {
	        "id": 1,
	        "name": "Publisher",
	        "email": "publisher@crossover.com",
	        "password": "$2a$10$iwO9vH6m4h6I.9pnuaUo4u19mUKXuG6mm3oKE9iT525fQ/Vf5jA7K",
	        "role": "PUBLISHER",
	        "token": "94ad34b1de8bd56172a7bde237bf4470db305cdd"
	    }
	]
```
