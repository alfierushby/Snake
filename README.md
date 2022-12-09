# Snake Game
## About
This is a Snake Game that contains the follow features:
- A High-score system 
- Multiple Levels defined by .tmx files
- Theming via the Options menu
## How to run
To run the Application :

     mvn clean javafx:run -f pom.xml

To run the testing framework:

    mvn compile surefire:test -f pom.xml

If the tests do not run, try:

    mvn deploy -f pom.xml

And surefire should work then.