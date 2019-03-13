## Description
A pilot integrated library system that streamlines the process of registering new members and loaning of books from the library. It consists of a core backend developed with a component-base architecture and a book lending application to support the workflow of the library. Details of the requirements can be found [here](miscellaneous/docs/IS2103-Assignment1.pdf).

## Setup
1. Download and install `mysql-installer-community`
2. Create a new schema called `librarydb`
3. Install `netbeans-8.2`

## Instructions
1. Open netbeans
2. Open project `IntegratedLibrarySystem`
3. Double click on the project to open Java EE modules `IntegratedLibrarySystem-ejb.jar` and `BookLendingTerminalClient.jar`
4. Connect to database via Services > Databases > jdbc:mysql://localhost:3306/librarydb...
5. Clean and build all projects
6. Deploy `IntegratedLibrarySystem-ejb`
7. Run `BookLendingTerminalClient`
8. Always stop glasfish server before clean, build and deploy

## Demo
1. [Deployment](miscellaneous/video/Deploy_and_Run.mp4)
1. [Login](miscellaneous/video/Login.mp4)