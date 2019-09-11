# Weather Forecasting Rest API version 1.2

## Getting started
This application is build on spring boot v 2.1.2.Release
To use Weather Forecasting Rest API, User need to register with
http://openweathermap.org service to get APIKEY, which will be added in application.properties file.

## Prerequisite
* Maven 3.5.2
* Eclipse Oxygen
* Java 8

## Build
This application uses MAVEN 3.5.2 for build.
Use below command at the root directory of this project

>mvn clean install

## Run
Once the build is successful, you can find the jar file of this application in ~/target folder
Run the jar file using below command

> cd target

>java -jar weatherforecast-0.0.1-SNAPSHOT.jar


## Access API
http://localhost:9090/data/{cityname}
e.g. http://localhost:9090/data/berlin

response Example: 

`[{"date":[2019,1,14],"avg_day_temp":34.705,"avg_night_temp":35.3625,"avg_pressure":1005.36375},{"date":[2019,1,15],"avg_day_temp":35.385,"avg_night_temp":36.5425,"avg_pressure":1016.5550000000001},{"date":[2019,1,16],"avg_day_temp":42.81999999999999,"avg_night_temp":43.102500000000006,"avg_pressure":1010.2175}]`

## Access Swagger UI
http://localhost:9090/swagger-ui.html
This can be used  for manual testing

## Unit Testing
To  Test Application : 

you can run com.weatherforecast.WeatherForcastApplicationTests

## Integration Testing
To Test Integration with openweathermap.org: 

you can run com.weatherforecast.integration.WeatherForecastIntegrationTest

## Reasoning and Motivations

At first, I created a high level design:

`API call-->/data/{city}-->dataservice-->openweathermap forecast API-->dataservice-->response for /data`

Note: In case study, I assume that, Average is referring to mean and not to mode or median

Following are my reasoning and motivations through out this case study:

* When ever I develop an API, I keep in mind the performance, maintainability, easy to test, and easy to read.
* code should be well commented for easy understanding.
* Bootstrap the application by creating project at https://start.spring.io/.
* Imported to eclipse for development, my preferable IDE.
* All the variables, which are subject to change were added in properties file, this helps in easy maintenance.
* Java 8 features like Lambda expressions with Stream API is used for better understanding and less code.
* Collections like ArrayList is used to collect data for different days.
* POJO classes are created to get the required forecast data from openweathermap.org.
* POJO class is created for /data response.
* Exceptions are created to throw the correct error code according to the REST API conventions.
* Input is getting validated at the starting of API call and appropriate error code is thrown.
* Caching is enabled in this application to cached the forecast data from openweatherapi.org, to improve the performance for multiple same API calls.
* server port is also changed to 9090 because mainly 8080 is already taken in several machines.
* I have used multiple functions to divide the logic and increase the code reuse.
* LocalDate and LocalTime is added in the response of openweathermap.org, which makes it easy to filter the entries according to the date and time.
* 
