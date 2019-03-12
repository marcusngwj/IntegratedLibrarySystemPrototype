/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import dao.StaffEntityManager;
import entity.StaffEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.EntityManagerException;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;
import util.logger.Logger;

/**
 *
 * @author Marcus
 */
@Stateless
@Local(StaffEntityControllerLocal.class)
@Remote(StaffEntityControllerRemote.class)
public class StaffEntityController implements StaffEntityControllerLocal, StaffEntityControllerRemote {
    private StaffEntityManager staffEntityManager;
    
    public StaffEntityController() {
        staffEntityManager = new StaffEntityManager();
    }

    @Override
    public StaffEntity createNewStaff(StaffEntity newStaffEntity) throws EntityManagerException {
        Logger.log("StaffEntityController", "createNewStaff");
        return staffEntityManager.createNewStaff(newStaffEntity);
    }
    
    @Override
    public List<StaffEntity> retrieveAllStaffs() throws EntityManagerException {
        Logger.log("StaffEntityController", "retrieveAllStaffs");
        return staffEntityManager.retrieveAllStaffs();
    }
    
    
    
    @Override
    public StaffEntity retrieveStaffByStaffId(Long staffId) throws EntityManagerException, StaffNotFoundException {
        Logger.log("StaffEntityController", "retrieveStaffByStaffId");
        List<StaffEntity> staffEntities = staffEntityManager.retrieveAllStaffs();
        
        for(StaffEntity staffEntity:staffEntities)
        {
            if(staffEntity.getStaffId().equals(staffId))
            {
                return staffEntity;
            }
        }
//        
        throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
    }
    
    @Override
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Logger.log("StaffEntityController", "retrieveStaffByUsername");
        return staffEntityManager.retrieveStaffByUsername(username);
    }
    
    @Override
    public StaffEntity staffLogin(String username, String password) throws EntityManagerException, InvalidLoginException {
        Logger.log("StaffEntityController", "staffLogin");
        
        List<StaffEntity> staffEntities = retrieveAllStaffs();
        
        for(StaffEntity staffEntity:staffEntities) {
            if(staffEntity.getUserName().equals(username) && staffEntity.getPassword().equals(password)) {
                return staffEntity;
            }
        }
        
        throw new InvalidLoginException("Username does not exist or invalid password!");
    }
    
    
    
//    @Override
//    public void updateStaff(StaffEntity staffEntity) throws EntityManagerException
//    {
//        entityManager.update(staffEntity);
//    }
    
    
    
//    @Override
//    public void deleteStaff(StaffEntity staffEntity) throws EntityManagerException {
//        entityManager.delete(staffEntity);
//    }
}
