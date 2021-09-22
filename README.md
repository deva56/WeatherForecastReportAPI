# WeatherForecastReportAPI

Application can be run locally by starting main SpringBootApplication annotated class using IDE of choice ( Spring Tool Suite, IntelliJ IDEA etc.)

Database used for development and testing purposes is H2 in-memory database which is configured to save files permanently inside ./data/weather_forecast.mv.db file located in project main folder.

Database can be accesed through web browser of choice at url http://localhost:8080/h2-console.
Database access data is: JDBC URL: jdbc:h2:file:./data/weather_forecast
			 User Name: root
		 	 Password: root

Application can be consumed with Postman or other similar JSON compatible clients.

---Supported routes with example usage---

	__Non authenticated users__

http://localhost:8080/api/auth/registration ->

Registers new user.
Calling method is POST.
Necessary data for registration is entered in request body in JSON form with example :

{
  "username": "slavko",
  "password": "slavko_pw"
}

http://localhost:8080/api/auth/login ->

Login existing user.
Calling method is POST.
Necessary data for login is entered in request body in JSON form with example :

{
  "username": "slavko",
  "password": "slavko_pw"
}

http://localhost:8080/api/auth/logout ->

Logout existing user.
Calling method is POST.
No data entered is needed for this route.


http://localhost:8080/api/home -> 

Calling method is GET.
No data entered is needed for this route.
If no query parameters are entered method will return all the locations temperatures from database.

http://localhost:8080/api/home?countryName=Croatia 

If query parameter is entered it will filter all the database locations based on country and return them, like in example above.


	__Authenticated users__

http://localhost:8080/api/location/forecast -> 

Calling method is GET.
No data entered is needed for this route.
It will return all the locations temperatures in JSON format of the currently authenticated user.


http://localhost:8080/api/location -> 

Saves new location of currently authenticated user in database.
Calling method is POST.
Necessary data for registration is entered in request body in JSON form with example :

{
  "country": "Croatia",
  "city": "Zagreb"
}


http://localhost:8080/api/location/{city} -> 

Calling method is DELETE.
Depending on value of path variable {city}, location where city name is equal to {city} will be deleted from currently 
authenticated user if he currently has a location with that city name saved in database.



