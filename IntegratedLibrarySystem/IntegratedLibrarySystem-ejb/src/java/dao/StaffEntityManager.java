/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.StaffEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import static jdk.nashorn.internal.runtime.ListAdapter.create;
import util.database.MySqlJdbcHelper;
import util.logger.Logger;
import util.exception.EntityManagerException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author Marcus
 */
public class StaffEntityManager {
    @PersistenceContext(unitName = "IntegratedLibrarySystem-ejbPU")
    private EntityManager entityManager;
    
    public StaffEntityManager() {}
    
    public StaffEntity createNewStaff(StaffEntity newStaffEntity) throws EntityManagerException {
        Logger.log("StaffEntityManager", "createNewStaff");

        try {
            String sql = "INSERT INTO staffentity (`FIRSTNAME`, `LASTNAME`, `PASSWORD`, `USERNAME`) VALUES (?,?,?,?);";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newStaffEntity.getFirstName());
            preparedStatement.setString(2, newStaffEntity.getLastName());
            preparedStatement.setString(3, newStaffEntity.getPassword());
            preparedStatement.setString(4, newStaffEntity.getUserName());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1)) {
                sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'librarydb' AND TABLE_NAME = 'staffentity';";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet != null && resultSet.next()) {
                    newStaffEntity.setStaffId(Integer.valueOf(resultSet.getInt("AUTO_INCREMENT") - 1).longValue());
                    return newStaffEntity;
                }
                else {
                    throw new EntityManagerException("An unknown error has occurred while retrieving the new Staff ID");
                }
            }
            else {
                throw new EntityManagerException("An unknown error has occurred while creating the new staff record");
            }
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException(ex.getMessage());
        }
    }
    
    public static List<StaffEntity> retrieveAllStaffs() throws EntityManagerException {
        Logger.log("StaffEntityManager", "retrieveAllStaffs");
        
        try {
            List<StaffEntity> staffEntities = new ArrayList<>();
            String sql = "SELECT * FROM staffentity ORDER BY STAFFID ASC;";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                do {
                    staffEntities.add(new StaffEntity(resultSet.getLong("STAFFID"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("USERNAME"), resultSet.getString("PASSWORD")));
                }
                while(resultSet.next());
            }
            else {
                throw new EntityManagerException("There are no entries in staffentity");
            }
            
            return staffEntities;
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException(ex.getMessage());
        }
    }
        
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Logger.log("StaffEntityManager", "retrieveStaffByUsername");
        
        Query query = entityManager.createQuery("SELECT s FROM StaffEntity s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (StaffEntity)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }
}
