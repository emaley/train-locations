# train-locations

## What is it?
Provides a web page to see a real time updated list of train locations.

It uses websockets to communicate the real time information and has an Oauth2 secured
trains endpoint that is used to integrate the sending of the train location information.

## How to view train locations
Run the application using the spring boot plugin

Goto `http://localhost:8080` and you will directed to the login page.

NOTE: The service is using an in memory database so it will need to be initialised with a
user registration before the system can be used

Use the `Create an account` link to register a user

Once registered you will be shown the train locations page. Here you will need to select to
have the train locations shown in real time

## How to add train locations for testing
A swagger UI is provided to be able to send train locations to the service for testing.
`http://localhost:8080/swagger-ui.html`

When you add a train location, you will see it shown in the train locations page
`localhost:8080/train`
