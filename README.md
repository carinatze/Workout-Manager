# Workout Manager

## Staying Healthy and Organized


**What will the application do?**
This Workout Manager will provide users to create their own collection of different workouts. Each workout will allow 
users to add multiple exercises with repetitions for each exercise to a specific workout. In addition, they can set and 
change a level of their workouts to one of: *Beginners, Intermediate, or Advanced*. It will keep track of all the 
workouts the user has created, and details of the exercises within each of their workouts. Features of the workout 
manager include: listing all the exercises within a workout, listing all the workouts in a collection, and setting
the level of each workout in the user's collection. 

**Who will use it?**
Anyone who is active, whether they are beginning their workout journey or for people who are more advanced.
This will be beneficial for people starting out on their fitness journey and for those individuals that prefer to 
plan out their workouts. It will allow users to organize their workouts and different exercises based on their specific 
workout goal at that time. 

**Why is this project of interest to you?**
This project is of interest to me because I want to personally be able to use this application. Since quarantine has 
begun, I noticed that I was not feeling my best mentally and physically. Thus, I decided to start investing in my 
health. When I first started out, it was really difficult for me to get into the routine of working out by myself and 
not in a gym with various equipment to use, where I would mainly utilize the treadmill and other weight machines. I had 
to research and learn how to motivate myself to fulfill the recommended physical activity per day.
I feel that making a workout manager based on the users' personal fitness goals will motivate them to go and 
complete the work out for the day. It allows the user to not have to worry about planning their workouts and motivates 
them to stay active when they are feeling lazy or tired. 

## User Stories
- As a user, I want to be able to add an exercise to my workout
- As a user, I want to be able to add a workout to my collection
- As a user, I want to be able to view a list of all the different exercises in my workout
- As a user, I want to be able to view a list of all the different workouts in my collection
- As a user, I want to be able to set the level of the workout

- As a user, I want to be able to save my workout collection to file, and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my workout collection from a file.

## Phase 4 Task 2
I tested and designed the Workout class to be robust. The method setWorkoutLevel throws an InvalidLevelException when
the level assigned is neither: beginner, intermediate, nor advanced. The test for the case where the exception is 
expected is in the WorkoutTest class called testSetWorkoutLevelInvalidException. Another case where the exception is not
expected is called testSetWorkoutLevelValidToInvalidException, where at first the workout was set to a valid level; thus, 
no exception was expected, and changed to an invalid level, where the exception was later expected. 
I also had to make changes to the addWorkout method in JsonReader class. This is where we caught the 
InvalidLevelException when the user attempts to set the workout level, and if it is invalid, an exception would be 
thrown and is caught in this method. The console prints out a statement indicating that the level is invalid. 
In addition, I added a try catch clause to the rateWorkout method in the WorkoutManager UI class. The method would
print out that the given level was invalid, if it was not beginner, intermediate, nor advanced. A catch clause was added
to the method: testWriterGeneralWorkoutCollection in the test class for Json writer because it called on the 
setWorkoutLevel method, and tests the case where no exception was expected to be thrown. The WorkoutManagerGUI class 
ensures that whenever an invalid workout level was set, the console would print out "___ is an invalid workout level."

## Phase 4 Task 3
If I had more time to work on the project, I would refactor my code to reduce coupling and increase cohesion. I would 
increase cohesion in my WorkoutManagerGUI class and split the different responsibilities of each GUI aspect into 
different sub-classes to adhere to the Single Responsibility Principle. For example, a class handling all actions of 
the buttons-- ButtonGUI, another class setting up images --ImageGUI, and another class setting up the layout 
--LayoutGUI, so that the WorkoutMangerGUI is not super long in length. 
In addition, I would try to decrease the amount of coupling between the classes by aiming to 
reduce the dependency among the classes. For instance, I would have the WorkoutManager only have a field of 
WorkoutCollection not Workout to reduce coupling because Workout can be accessed through Workout Collection.
Another design choice I would implement if I had more time would be to implement Java's Observer, so that the user 
would be notified every time a new workout was added to their collection.
Also, when adding level I would add a drop down menu instead of typing it in. This would ensure the user does not add an 
invalid workout, and can only choose from a list of three levels.