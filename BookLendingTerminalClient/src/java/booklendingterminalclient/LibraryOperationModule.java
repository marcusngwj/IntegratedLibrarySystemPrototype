/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklendingterminalclient;

import ejb.session.stateful.LibraryOperationRemote;
import entity.BookEntity;
import entity.MemberEntity;
import java.util.Scanner;
import javafx.util.Pair;
import util.exception.BookNotFoundException;
import util.exception.EntityManagerException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author Marcus
 */
public class LibraryOperationModule {
    private LibraryOperationRemote libraryOperationRemote;
    
    public LibraryOperationModule() {}
    
    public LibraryOperationModule(LibraryOperationRemote libraryOperationRemote) {
        this();
        
        this.libraryOperationRemote = libraryOperationRemote;
    }
    
    public void bootUpLibraryProgram() {
        final int REGISTER_MEMBER = 1;
        final int LEND_BOOK = 2;
        final int BACK = 3;
        
        while (true) {
            int response = 0;
            
            displayMessage(getMainMenu());
            
            while (response!=REGISTER_MEMBER && response!=LEND_BOOK && response!=BACK) {
                response = getUserResponse();
            }
            
            if (response == REGISTER_MEMBER) {
                executeRegistrationOfMember();
            }
            else if (response == LEND_BOOK) {
                executeBookLending();
            }
            else if (response == BACK) {
                break;
            }
            else {
                displayMessage("Invalid option, please try again!\n");
            }
            
            System.out.println();
        }
    }
    
    private void executeRegistrationOfMember() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println();
        System.out.println("*** ILS :: Library Operation :: Register Member ***\n");
        System.out.print("Enter First Name> ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name> ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter Gender> ");
        String gender = scanner.nextLine().trim();
        System.out.print("Enter Age> ");
        int age = Integer.valueOf(scanner.nextLine().trim());
        System.out.print("Enter Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        System.out.print("Enter Phone> ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter Address> ");
        String address = scanner.nextLine().trim();
        
        if (isFormComplete(firstName, lastName, gender, age, identityNumber, phone, address)) {
            try {
                libraryOperationRemote.createNewMember(firstName, lastName, gender, age, identityNumber, phone, address);
                displayMessage("Member has been registered successfully!\n");
            }
            catch (EntityManagerException ex) {
                displayMessage("Registration Unsuccessful. " + ex.getMessage());
            }
        }
        else {
            displayMessage("Registration Unsuccessful. Form incomplete. Please try again.");
        }
    }
    
    private boolean isFormComplete(String firstName, String lastName, String gender, int age, String identityNumber, String phone, String address) {
        return !firstName.equals("") && !lastName.equals("") && !gender.equals("") && age!=0 && !identityNumber.equals("") && !phone.equals("") && !address.equals("");
    }
    
    private void executeBookLending() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println();
        System.out.println("*** ILS:: Library Operation :: Lend Book ***\n");
        System.out.print("Enter Book Id> ");
        long bookId = Long.valueOf(scanner.nextLine().trim());
        System.out.print("Enter Member Identity Number> ");
        String identityNumber = scanner.nextLine().trim();
        
        if (bookId!=0 && !identityNumber.equals("")) {
            try {
                Pair<MemberEntity, BookEntity> loanDetails = libraryOperationRemote.lendBook(bookId, identityNumber);
                MemberEntity memberEntity = loanDetails.getKey();
                BookEntity bookEntity = loanDetails.getValue();
                displayMessage("Book: " + bookEntity.getTitle() + " lent to " + memberEntity.getFullName() + ".");
            }
            catch (MemberNotFoundException | BookNotFoundException | EntityManagerException ex) {
                displayMessage("Lending Failed. " + ex.getMessage());
            }
        }
        else {
            displayMessage("An error occurred. Book is not lent. Please try again.");
        }
    }
    
    private int getUserResponse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextInt();
    }
    
    private String getMainMenu() {
        return "*** ILS :: Library Operation ***\n\n" +
               "1: Register Member\n" +
               "2: Lend Book\n" +
               "3: Back\n";
    }
    
    private void displayMessage(String message) {
        System.out.println(message);
    }
}
