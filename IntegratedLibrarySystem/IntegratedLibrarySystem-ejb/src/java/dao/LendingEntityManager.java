/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.LendingEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import util.database.MySqlJdbcHelper;
import util.exception.EntityManagerException;
import util.logger.Logger;

/**
 *
 * @author Marcus
 */
public class LendingEntityManager {
    public LendingEntityManager() {}
    
    public LendingEntity lendBook(LendingEntity newLendingEntity) throws EntityManagerException {
        Logger.log("BookEntityManager", "createNewBook");
        
        try {
            String sql = "INSERT INTO lendingentity (`MEMBERID`, `BOOKID`, `LENDDATE`) VALUES (?,?,?);";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, newLendingEntity.getMemberId());
            preparedStatement.setLong(2, newLendingEntity.getBookId());
            preparedStatement.setDate(3, newLendingEntity.getLendDate());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1)) {
                sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'librarydb' AND TABLE_NAME = 'lendingentity';";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet != null && resultSet.next()) {
                    newLendingEntity.setLendId(Integer.valueOf(resultSet.getInt("AUTO_INCREMENT") - 1).longValue());
                    return newLendingEntity;
                }
                else {
                    throw new EntityManagerException("An unknown error has occurred while retrieving the new Member ID");
                }
            }
            else {
                throw new EntityManagerException("An unknown error has occurred while creating the new member record");
            }
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException("\n" + ex.getMessage());
        }
    }
}
