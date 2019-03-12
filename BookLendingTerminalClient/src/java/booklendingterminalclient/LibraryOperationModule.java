/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklendingterminalclient;

import java.util.Scanner;

/**
 *
 * @author Marcus
 */
public class LibraryOperationModule {
    public LibraryOperationModule() {}
    
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
                
            }
            else if (response == LEND_BOOK) {
                
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
