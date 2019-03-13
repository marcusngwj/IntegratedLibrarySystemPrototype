/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateful.LibraryOperationControllerLocal;
import ejb.session.stateless.StaffEntityControllerLocal;
import entity.BookEntity;
import entity.MemberEntity;
import entity.StaffEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.EntityManagerException;
import util.logger.Logger;

/**
 *
 * @author Marcus
 */
@Startup
@Singleton
@LocalBean

public class DataInitializationSessionBean {

    @PersistenceContext(unitName = "IntegratedLibrarySystem-ejbPU")
    private EntityManager entityManager;
   
    @EJB
    private StaffEntityControllerLocal staffEntityControllerLocal;
    @EJB
    private LibraryOperationControllerLocal libraryOperationControllerLocal;
    
    public DataInitializationSessionBean() {}
    
    @PostConstruct
    public void postConstruct() {               
        verifyStaffEntityTable();
        verifyMemberEntityTable();
        verifyBookEntityTable();
    }
    
    private void verifyStaffEntityTable() {
        try {
            List<StaffEntity> staffList = staffEntityControllerLocal.retrieveAllStaffs();
        }
        catch(EntityManagerException ex) {
            initializeStaffEntityTable();
        }
    }
    
    private void initializeStaffEntityTable() {
        Logger.log("DataInitializationSessionBean", "initializeStaffEntityTable", "Initializing data...");
        
        try {
            staffEntityControllerLocal.createNewStaff(new StaffEntity((long)1, "Linda", "Chua", "linda", "password"));
            staffEntityControllerLocal.createNewStaff(new StaffEntity((long)2, "Barbara", "Durham", "barbara", "password"));
            staffEntityControllerLocal.createNewStaff(new StaffEntity((long)3, "Kelly", "Tan", "kelly", "password"));
        }
        catch(EntityManagerException ex) {
            Logger.log("DataInitializationSessionBean", "initializeStaffEntityTable", "Error in initializing Data: " + ex.getMessage());
        }
    }
    
    private void verifyMemberEntityTable() {
        try {
            List<MemberEntity> MemberList = libraryOperationControllerLocal.retrieveAllMembers();
        }
        catch(EntityManagerException ex) {
            initializeMemberEntityTable();
        }
    }
    
    private void initializeMemberEntityTable() {
        Logger.log("DataInitializationSessionBean", "initializeMemberEntityTable", "Initializing data...");
        
        try {
            libraryOperationControllerLocal.createNewMember(new MemberEntity((long)1, "Tony", "Teo", "Male", 44, "S7483027A", "87297373", "11 Tampines Ave 3"));
            libraryOperationControllerLocal.createNewMember(new MemberEntity((long)2, "Wendy", "Tan", "Female", 35, "S8381028X", "97502837", "15 Computing Dr"));
        }
        catch(EntityManagerException ex) {
            Logger.log("DataInitializationSessionBean", "initializeMemberEntityTable", "Error in initializing Data: " + ex.getMessage());
        }
    }
    
    private void verifyBookEntityTable() {
        try {
            List<BookEntity> BookList = libraryOperationControllerLocal.retrieveAllBooks();
        }
        catch(EntityManagerException ex) {
            initializeBookEntityTable();
        }
    }
    
    private void initializeBookEntityTable() {
        Logger.log("DataInitializationSessionBean", "initializeBookEntityTable", "Initializing data...");
        
        try {
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)1, "The Lord of the Rings", "S18018", 1954));
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)2, "Le Petit Prince", "S64921", 1943));
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)3, "Harry Potter and the Philosopher's Stone", "S38101", 1997));
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)4, "The Hobbit", "S19527", 1937));
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)5, "And Then There Were None", "S63288", 1939));
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)6, "Dream of the Red Chamber", "S32187", 1791));
            libraryOperationControllerLocal.createNewBook(new BookEntity((long)7, "The Lion, the Witch and the Wardrobe", "S74569", 1950));
        }
        catch(EntityManagerException ex) {
            Logger.log("DataInitializationSessionBean", "initializeBookEntityTable", "Error in initializing Data: " + ex.getMessage());
        }
    }
    
}
