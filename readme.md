# Weather Forecasting Game - backend

The user gives his/her prediction about the temperature at a location, date and time of his choice (date has to be in
the future). When this day comes the system assigns an appropriate number of scored points, by checking the accuracy of
the given prediction. This is the main principle of the game, but it also has many other functionalities, like:

- Adding, deleting, checking weather prediction
- Checking user score or whole scoreboard
- Checking historical or actual weather of given location
- Checking user results: historical weather prediction with scored points

## Table of Contents

- [Specification](#specification)
- [Tech stack](#tech-stack)
    - [Code](#code)
    - [Tests](#tests)
- [C3 Diagram](#c3-Diagram)
- [Endpoints](#endpoints)
- [Setup](#setup)

## Specification

- Java with Spring boot
- Facade design pattern
- Modular monolith hexagonal architecture
- MySQL database for Weather Prediction, Score and Historical Weather Prediction
- Scheduling for results of a comparison between the weather prediction and the current real weather
- Connection to the external weather API - www.weatherapi.com
- Unit tests

## Tech stack

#### Code: Java, Spring, Hibernate, MySQL

#### Tests: Junit, Mockito

## C3 Diagram

The diagram blow presents main application components and module dependencies.

![Architecture_v6.png](architecture%2FArchitecture_v6.png)

## Endpoints

|               ENDPOINT               | METHOD |                REQUEST                |          RESPONSE          |                 FUNCTION                 |
|:------------------------------------:|:------:|:-------------------------------------:|:--------------------------:|:----------------------------------------:|
|       /weather-prediction/add        |  POST  | JSON REQUEST BODY (weatherPrediction) |  JSON (operation result)   |        creates weather prediction        |
|  /weather-prediction/delete?{UUID}   | DELETE |         REQUEST PARAM (UUID)          |   JSON operation result)   |        deletes weather prediction        |
| /weather-prediction/show?{username}  |  GET   |        REQUEST PARAM (String)         | JSON (weather predictions) |       returns weather predictions        |
|         /current?{location}          |  GET   |        REQUEST PARAM (String)         |    JSON (real weather)     |   returns current weather of location    |
| /historical?{location}?{date}?{hour} |  GET   |  REQUEST PARAM (String, String, int)  |    JSON (real weather)     |  returns historical weather of location  |
|             /scoreboard              |  GET   |                   -                   |       JSON (scores)        |            returns scoreboard            |
|        /user-score?{username}        |  GET   |        REQUEST PARAM (String)         |        JSON (score)        |            returns user score            |
|         /history?{username}          |  GET   |        REQUEST PARAM (String)         | JSON (prediction history)  | returns historical prediction with score |

## Setup

Requirements: IDE (IntelliJ), MySQL, Postman

1. Clone repository.
2. Open project in IDE.
3. Create MySQL schema.
    ```
    mysql -u root -p
    create database weatherdb;
    ```
4. Run application.
5. To test app, you can use the endpoints in the postman
