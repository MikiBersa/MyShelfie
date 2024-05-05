# Corso Ingegneria del Software 2023 - Cremona

![alt text](https://github.com/DylanRossi/proj-ingsw-hackapo/blob/main/Documentation/images/name.png?raw=true)

## Documentation about the project

### Introduction

The university project is about develop a virtual edition of the board game MyShelfie.
The students had to create a server where the game runs and a client connecting to the server
that can be instantiated multiple times in order to play the game.
The game can be played with 2, 3 or 4 players, and it is possible to run the client in a CLI or using the
GUI (developed with JavaFX).
The following parts are about what decisions were made in order to create the game.

### MVC pattern

The MVC is a software design pattern used for developing user interfaces that
divides the related program logic into three interconnected elements.
This is done to separate internal representations of information from the ways information is presented to and accepted
from the user.

There are two MVC patterns in the project: one for the server and one for the client.

#### Server MVC

In the server the MVC is used together with the Observer-Observable pattern in order to send a message to the client
every time that there is an update in the Model.
More specifically the server receives a message that updates the Model, so the controller updates the
Model and the VirtualView receives a notification about the change with the Observer-Observable pattern,
creates a message using a factory method and gives it to the InterfaceNetwork that has to send it to the client.

#### Client MVC

Also in the client the MVC is used together with the Observer-Observable pattern, but they are used in order to update
the
View in the correct way.
In detail the client receives a message that updates the VirtualModel and the View, so the controller
updates the VirtualModel and then with Observer-Observable pattern the View receives a notification that the
VirtualModel has
changed, so the View will be refreshed.

### Starter screen in GUI
![alt text](https://github.com/DylanRossi/proj-ingsw-hackapo/blob/main/Documentation/images/start.png?raw=true)

### Network of the application

The application is based on the Java Serialization and two Visitor pattern: one for the client and one for the server
in order to send/receive messages and understand their type by the client or the server.
The visitor pattern apply a double dispatch in which the first method is used to identify the type of the message and
then
a second method is called to handle the message in the right way.

#### Example:

The client send the username to the server using an AddUserMessage that contains the username, the server receives the
message
and handles it in the right way with the double dispatch.
The server has to answer the client about the username selected: if the username was already selected by another player
the server sends a ResponseUsernameMessage with the status set to false, otherwise the status is set to true.
The client receive the message and identify it with the visitor pattern then the client reads the status of the
ResponseUsernameMessage and decides if it has to ask for another username.

### Javadoc documentation
https://dylanrossi.github.io/proj-ingsw-hackapo/

### Main screen of the game
![alt text](https://github.com/DylanRossi/proj-ingsw-hackapo/blob/main/Documentation/images/board.png?raw=true)
