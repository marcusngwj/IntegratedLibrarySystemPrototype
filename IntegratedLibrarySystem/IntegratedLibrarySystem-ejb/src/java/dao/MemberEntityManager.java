/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.MemberEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import util.database.MySqlJdbcHelper;
import util.exception.EntityManagerException;
import util.exception.MemberNotFoundException;
import util.logger.Logger;

/**
 *
 * @author Marcus
 */
public class MemberEntityManager {
    public MemberEntityManager() {}
    
    public MemberEntity createNewMember(MemberEntity newMemberEntity) throws EntityManagerException {
        Logger.log("MemberEntityManager", "createNewMember");
        try {
            String sql = "INSERT INTO memberentity (`FIRSTNAME`, `LASTNAME`, `GENDER`, `AGE`, `IDENTITYNUMBER`, `PHONE`, `ADDRESS`) VALUES (?,?,?,?,?,?,?);";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newMemberEntity.getFirstName());
            preparedStatement.setString(2, newMemberEntity.getLastName());
            preparedStatement.setString(3, newMemberEntity.getGender());
            preparedStatement.setInt(4, newMemberEntity.getAge());
            preparedStatement.setString(5, newMemberEntity.getIdentityNumber());
            preparedStatement.setString(6, newMemberEntity.getPhone());
            preparedStatement.setString(7, newMemberEntity.getAddress());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1)) {
                sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'librarydb' AND TABLE_NAME = 'memberentity';";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet != null && resultSet.next()) {
                    newMemberEntity.setMemberId(Integer.valueOf(resultSet.getInt("AUTO_INCREMENT") - 1).longValue());
                    connection.close();
                    return newMemberEntity;
                }
                else {
                    connection.close();
                    throw new EntityManagerException("An unknown error has occurred while retrieving the new Member ID");
                }
            }
            else {
                connection.close();
                throw new EntityManagerException("An unknown error has occurred while creating the new member record");
            }
        }
        catch (NamingException ex) {
            throw new EntityManagerException("\n" + ex.getMessage());
        }
        catch (SQLException ex) {
            if (ex.getErrorCode() == MySqlJdbcHelper.DUPLICATE_ENTRY) {
                throw new EntityManagerException("Member with identification number of " + newMemberEntity.getIdentityNumber() + " was already registered.");
            }
            else {
                throw new EntityManagerException(ex.getMessage());
            }
        }
    }
    
    public static List<MemberEntity> retrieveAllMembers() throws EntityManagerException {
        Logger.log("MemberEntityManager", "retrieveAllMembers");
        
        try {
            List<MemberEntity> memberEntities = new ArrayList<>();
            String sql = "SELECT * FROM memberentity ORDER BY MEMBERID ASC;";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                do {
                    memberEntities.add(new MemberEntity(resultSet.getLong("MEMBERID"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("GENDER"), resultSet.getInt("AGE"), resultSet.getString("IDENTITYNUMBER"), resultSet.getString("PHONE"), resultSet.getString("ADDRESS")));
                }
                while(resultSet.next());
            }
            else {
                connection.close();
                throw new EntityManagerException("There are no entries in staffentity");
            }
            
            connection.close();
            return memberEntities;
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException(ex.getMessage());
        }
    }
    
    public static MemberEntity retrieveMemberWithIdentityNumber(String identityNumber) throws MemberNotFoundException, EntityManagerException {
        Logger.log("MemberEntityManager", "retrieveMemberIdUsingIdentityNumber");
        
        try {
            String sql = "SELECT * FROM memberentity WHERE IDENTITYNUMBER= ?;";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1, identityNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            MemberEntity memberEntity;
            
            if(resultSet.next()) {
                memberEntity = new MemberEntity(resultSet.getLong("MEMBERID"), resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("GENDER"), resultSet.getInt("AGE"), resultSet.getString("IDENTITYNUMBER"), resultSet.getString("PHONE"), resultSet.getString("ADDRESS"));
            }
            else {
                connection.close();
                throw new MemberNotFoundException("The system has no record of member " + identityNumber);
            }
            
            connection.close();
            return memberEntity;
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException(ex.getMessage());
        }
    }
}
