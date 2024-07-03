# Snake Game

![Alt Text](./frontpage.gif)

Made by Alfie Rushby

## About
This is a Snake Game that contains the follow features:
- A High-score system 
- Multiple Levels defined by .tmx files
- Theming via the Options menu
## How to install and run the project
To run the Application:

     mvn clean javafx:run -f pom.xml

To deploy, run:

    mvn deploy -f pom.xml

Afterwards, run the testing framework:

    mvn compile surefire:test -f pom.xml

And surefire should work then.

## How to use the project 
Click new game and you will be prompted for a name. <br> 
This will be used to record ur scores.  <br>
Afterwards, pick a map depending on difficulty, and the game should play.

## Credits
- FXGL: https://github.com/AlmasB/FXGL/
- Obstacles: https://game-endeavor.itch.io/mystic-woods