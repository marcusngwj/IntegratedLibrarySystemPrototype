/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklendingterminalclient;

import ejb.session.stateless.StaffEntityControllerRemote;
import ejb.session.stateful.LibraryOperationRemote;

import entity.StaffEntity;
import java.util.Scanner;
import util.exception.EntityManagerException;
import util.exception.InvalidLoginException;

/**
 *
 * @author Marcus
 */
public class MainApp {
    private StaffEntityControllerRemote staffEntityControllerRemote;
    
    private LibraryOperationRemote libraryOperationRemote;
    private LibraryOperationModule libraryOperationModule;
    
    private StaffEntity currentStaffEntity;
    
    public MainApp() {}
    
    public MainApp(StaffEntityControllerRemote staffEntityControllerRemote, LibraryOperationRemote libraryOperationRemote) {
        this.staffEntityControllerRemote = staffEntityControllerRemote;
        this.libraryOperationRemote = libraryOperationRemote;
    }
    
    public void runApp() {
        final int LOGIN_OPERATION = 1;
        final int EXIT_OPERATION = 2;
        
        while (true) {
            int response = 0;
            displayMessage(getWelcomeMessage());
            
            while(response != LOGIN_OPERATION && response != EXIT_OPERATION) {
                response = getUserResponse();
            }
            
            if (response == LOGIN_OPERATION) {
                try {
                    executeLogin();
                    libraryOperationModule = new LibraryOperationModule(libraryOperationRemote);
                    executeMainAction();
                }
                catch (InvalidLoginException e) {
                    // Log: Invalid Login Exception
                }
                
            }
            else if (response == EXIT_OPERATION) {
                System.out.println();
                System.out.println("Goodbye!");
                break;
            }
            
            System.out.println();
        }
    }
    
    private void executeMainAction() {
        final int LIBRARY_OPERATION = 1;
        final int LOGOUT = 2;
                
        while (true) {
            int response = 0;
            
            displayMessage(getMainMenu());
            
            while (response != LIBRARY_OPERATION && response !=  LOGOUT) {
                response = getUserResponse();
            }
            
            if (response == LIBRARY_OPERATION) {
                System.out.println();
                libraryOperationModule.bootUpLibraryProgram();
            }
            else if (response == LOGOUT) {
                System.out.println();
                displayMessage("You have successfully logout!");
                break;
            }
            else {
                displayMessage("Invalid option, please try again!\n");
            }
            
            System.out.println();
        }
        
    }
    
    private void executeLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println();
        System.out.println("*** ILS :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0) {
            try {
                currentStaffEntity = staffEntityControllerRemote.staffLogin(username, password);
                System.out.println("Login successful!\n");
            }
            catch (EntityManagerException e) {
                System.out.println("\nInvalid login credential: " + e.getMessage());
            } 
            catch (InvalidLoginException e) {
                System.out.println("\nInvalid login credential: " + e.getMessage());
                
                throw new InvalidLoginException();
            }           
        }
        else {
            System.out.println("Invalid login credential!");
        }
    }
    
    private int getUserResponse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextInt();
    }
    
    private void displayMessage(String message) {
        System.out.println(message);
    }
    
    private String getMainMenu() {
        return "*** Integrated Library System (ILS) ***\n\n" +
               "You are login as " + currentStaffEntity.getFirstName() + " " + currentStaffEntity.getLastName() + "\n\n" +
               "1: Library Operation\n" +
               "2: Logout\n";
    }
    
    private String getWelcomeMessage() {
        return "*** Welcome to Integrated Library System (ILS) ***\n\n" +
               "1: Login\n" +
               "2: Exit\n";
    }
}
