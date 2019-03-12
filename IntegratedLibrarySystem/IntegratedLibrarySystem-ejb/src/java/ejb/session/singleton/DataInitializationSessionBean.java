/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateful.LibraryOperationRemote;
import ejb.session.stateless.StaffEntityControllerLocal;
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
import util.exception.StaffNotFoundException;
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
    
    public DataInitializationSessionBean() {}
    
    @PostConstruct
    public void postConstruct() {               
        try {
            List<StaffEntity> staffList = staffEntityControllerLocal.retrieveAllStaffs();
        }
        catch(EntityManagerException ex) {
            initializeData();
        }
    }
    
    private void initializeData() {
        Logger.log("DataInitializationSessionBean", "initializeData", "Initializing data...");
        
        try {
            staffEntityControllerLocal.createNewStaff(new StaffEntity((long)1, "Linda", "Chua", "linda", "password"));
            staffEntityControllerLocal.createNewStaff(new StaffEntity((long)2, "Barbara", "Durham", "barbara", "password"));
            staffEntityControllerLocal.createNewStaff(new StaffEntity((long)3, "Kelly", "Tan", "kelly", "password"));
        }
        catch(EntityManagerException ex) {
            Logger.log("DataInitializationSessionBean", "initializeData", "Error in initializing Data: " + ex.getMessage());
        }
        
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD001", "Product A1", "Product A1", 100, new BigDecimal("10.00"), "Category A"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD002", "Product A2", "Product A2", 100, new BigDecimal("25.50"), "Category A"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD003", "Product A3", "Product A3", 100, new BigDecimal("15.00"), "Category A"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD004", "Product B1", "Product B1", 100, new BigDecimal("20.00"), "Category B"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD005", "Product B2", "Product B2", 100, new BigDecimal("10.00"), "Category B"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD006", "Product B3", "Product B3", 100, new BigDecimal("100.00"), "Category B"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD007", "Product C1", "Product C1", 100, new BigDecimal("35.00"), "Category C"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD008", "Product C2", "Product C2", 100, new BigDecimal("20.05"), "Category C"));
//        productEntityControllerLocal.createNewProduct(new ProductEntity("PROD009", "Product C3", "Product C3", 100, new BigDecimal("5.50"), "Category C"));
    }
    
}
