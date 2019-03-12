/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import util.exception.EntityManagerException;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author Marcus
 */
public interface StaffEntityControllerRemote {
    StaffEntity createNewStaff(StaffEntity newStaffEntity) throws EntityManagerException;
    
    List<StaffEntity> retrieveAllStaffs() throws EntityManagerException;
    
    StaffEntity retrieveStaffByStaffId(Long staffId) throws EntityManagerException, StaffNotFoundException;

    StaffEntity staffLogin(String username, String password) throws EntityManagerException, InvalidLoginException;

//    void updateStaff(StaffEntity staffEntity) throws EntityManagerException;
//    
//    void deleteStaff(StaffEntity staffEntity) throws EntityManagerException;
}
