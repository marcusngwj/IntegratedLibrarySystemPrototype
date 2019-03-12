/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklendingterminalclient;

import ejb.session.stateful.LibraryOperationRemote;
import ejb.session.stateless.StaffEntityControllerRemote;
import javax.ejb.EJB;

/**
 *
 * @author Marcus
 */
public class Main {
    @EJB
    private static StaffEntityControllerRemote staffEntityControllerRemote;
    @EJB
    private static LibraryOperationRemote libraryOperationRemote;
    
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(staffEntityControllerRemote, libraryOperationRemote);
//        MainApp mainApp = new MainApp();
        mainApp.runApp();
    }
    
}
