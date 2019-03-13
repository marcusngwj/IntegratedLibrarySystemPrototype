/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.BookEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import util.database.MySqlJdbcHelper;
import util.exception.BookNotFoundException;
import util.exception.EntityManagerException;
import util.logger.Logger;

/**
 *
 * @author Marcus
 */
public class BookEntityManager {
    public BookEntityManager() {}
    
    public BookEntity createNewBook(BookEntity newBookEntity) throws EntityManagerException {
        Logger.log("BookEntityManager", "createNewBook");
        try {
            String sql = "INSERT INTO bookentity (`TITLE`, `ISBN`, `YEAR`) VALUES (?,?,?);";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newBookEntity.getTitle());
            preparedStatement.setString(2, newBookEntity.getIsbn());
            preparedStatement.setInt(3, newBookEntity.getYear());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1)) {
                sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'librarydb' AND TABLE_NAME = 'bookentity';";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet != null && resultSet.next()) {
                    newBookEntity.setBookId(Integer.valueOf(resultSet.getInt("AUTO_INCREMENT") - 1).longValue());
                    return newBookEntity;
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
    
    public static List<BookEntity> retrieveAllBooks() throws EntityManagerException {
        Logger.log("BookEntityManager", "retrieveAllBooks");
        
        try {
            List<BookEntity> bookEntities = new ArrayList<>();
            String sql = "SELECT * FROM bookentity ORDER BY BOOKID ASC;";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                do {
                    bookEntities.add(new BookEntity(resultSet.getLong("BOOKID"), resultSet.getString("TITLE"), resultSet.getString("ISBN"), resultSet.getInt("YEAR")));
                }
                while(resultSet.next());
            }
            else {
                throw new EntityManagerException("There are no entries in boookentity");
            }
            
            return bookEntities;
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException(ex.getMessage());
        }
    }
    
    public static BookEntity retrieveBookWithId(Long bookId) throws BookNotFoundException, EntityManagerException {
        Logger.log("BookEntityManager", "retrieveBookWithId");
        
        try {
            String sql = "SELECT * FROM bookentity WHERE BOOKID = ?;";
            
            Connection connection = new MySqlJdbcHelper().createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            BookEntity bookEntity;
            
            if(resultSet.next()) {
                bookEntity = new BookEntity(resultSet.getLong("BOOKID"), resultSet.getString("TITLE"), resultSet.getString("ISBN"), resultSet.getInt("YEAR"));
            }
            else {
                throw new BookNotFoundException("The system has no record of book " + bookId);
            }
            
            return bookEntity;
        }
        catch(NamingException | SQLException ex) {
            throw new EntityManagerException(ex.getMessage());
        }
    }
}
