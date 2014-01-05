3DChess
=======

3D Chess application written in Java and using JOGL as an opengl wrapper

This is a 3D chess program written purely in java, and incorporates all chess rules except pawn promotion. I did not create the models for the pieces, they were taken from:
http://www.turbosquid.com/3d-models/chess-set-3ds-free/654392 

Further this program was created with JOGL (http://jogamp.org/jogl/www/), which should allow this program to be run on any OS, however I have only tested on Mac OS X 10.8/9, Windows 7, and Linux Ubuntu. 

I have included the Doxygen for the program, which includes detailed explanations of the classes, as well as an executable jar file which can be run. For whatever reason, if the jar file doesn't execute from double click, try from the command line using:
java -jar [Chess3D.jar]

Features:
-Includes undo, redo
-Includes basic movement animations and board rotations
-Allows abstraction for multiple game modes, currently only Standard, and Suicide (http://en.wikipedia.org/wiki/Antichess) variants are implemented
-Allows abstraction for multiple Board types such as rectangular (implemented), or hexagonal by extending Board


This project follows the MVC pattern as such the classes are broken up into the following packages:
view:
  This package contains all the GUI components, and resource loaders required to make the application visible, such as the Frame, any menus, and the GameCamera
  
model:
  This package contains all the data that defines the rules of chess. This includes the implementation of the Board, Gamemodes, and Pieces.
  
controller:
  This package contains all the code to control the game flow of the program, and is responsible for handling user input, then passing that information to the model and view components.

