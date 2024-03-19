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

#### Code:

![Alt text](https://camo.githubusercontent.com/142c1ca57c4a85ddae844e196b62ffd9095552d94e559f68907d2f6031ece170/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6a6176615f31372d6f72616e67653f7374796c653d666f722d7468652d6261646765266c6f676f3d6f70656e6a646b266c6f676f436f6c6f723d7768697465)
![Alt text](https://camo.githubusercontent.com/cec4f3deeda1cde8d7e0729e689a9946a7286fc2a79be3e8b32fafa4b9f0396a/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e675f426f6f745f332d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d737072696e67266c6f676f436f6c6f723d7768697465)
![Alt text](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Alt text](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Alt text](https://camo.githubusercontent.com/c3b871d02afde0384d676dfb0872461bca6d18199375067e04e0d67ff0f9bfae/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6d6176656e2d4337314133363f7374796c653d666f722d7468652d6261646765266c6f676f3d6170616368656d6176656e266c6f676f436f6c6f723d7768697465)

#### Tests:

![Alt text](https://camo.githubusercontent.com/6cf47d9ca3b8d62efb942ad8e9c9335f5bd5196ec76150d42fcc1a65f8486ddf/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a756e6974352d3235413136323f7374796c653d666f722d7468652d6261646765266c6f676f3d6a756e697435266c6f676f436f6c6f723d7768697465)
![Alt text](https://camo.githubusercontent.com/d38819e2d4efdc0a84acb94de6e2c94a02997234c5a72e72b1c250bb5a980e6f/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4d6f636b69746f2d3738413634313f7374796c653d666f722d7468652d6261646765)

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