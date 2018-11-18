# bankslips-api

A simple REST API to generate bank slips.

### Requirements

- JDK 8+
- Apache Maven 3+ (not mandatory)

### How to run?

#### Development

First, you need to clone this git repository:

```git clone https://github.com/WellingtonCosta/bankslips-api.git```

You can open the project in any IDE, but it must support Maven projects. 

You also can run this project just with Maven:

- For UNIX-based systems

```./mvnw spring-boot:run```

- For Windows

```mvnw spring-boot:run```

#### Test

To execute the automated tests, you just need to run the following command:

- For UNIX-based systems

```./mvnw test```

- For Windows

```mvnw test```

#### Production

This project is also availabe on [Heroku](https://bankslips-api.herokuapp.com).

The application on Heroku may take a while to respond due to [Heroku Free Dynos](https://devcenter.heroku.com/articles/free-dyno-hours) limitations.

### Technologies

This project was built with the following stack of technologies:

- Apache Maven
- H2 Database
- Jackson
- Lombok
- Querydsl
- Spring Boot (Actuator, Data JPA, Devtools, Tests, Web)
- Swagger

### License

    Copyright 2018 Wellington Costa

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.