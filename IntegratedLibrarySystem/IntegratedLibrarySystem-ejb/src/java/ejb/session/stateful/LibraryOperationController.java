/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import dao.BookEntityManager;
import dao.MemberEntityManager;
import entity.BookEntity;
import entity.MemberEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.EntityManagerException;
import util.logger.Logger;

/**
 *
 * @author Marcus
 */
@Stateful
@Local(LibraryOperationControllerLocal.class)
@Remote(LibraryOperationRemote.class)

public class LibraryOperationController implements LibraryOperationControllerLocal, LibraryOperationRemote {  
    @PersistenceContext(unitName = "IntegratedLibrarySystem-ejbPU")
    private EntityManager entityManager;
    
    private MemberEntityManager memberEntityManager;
    private BookEntityManager bookEntityManager;
    
    private static final long DUMMY_ID = 0;

    public LibraryOperationController() {
        this.memberEntityManager = new MemberEntityManager();
        this.bookEntityManager = new BookEntityManager();
    }
    
    /************ MEMBER_OPERATIONS ************/
    
    public MemberEntity createNewMember(String firstName, String lastName, String gender, Integer age, String identityNumber, String phone, String address) throws EntityManagerException {
        Logger.log("LibraryOperationController", "createNewMember", "Using Remote Interface");
        return createNewMember(new MemberEntity(DUMMY_ID, firstName, lastName, gender, age, identityNumber, phone, address));
    }
    
    public MemberEntity createNewMember(MemberEntity newMemberEntity) throws EntityManagerException {
        Logger.log("LibraryOperationController", "createNewMember", "Using Local Interface");
        return memberEntityManager.createNewMember(newMemberEntity);
    }
    
    public List<MemberEntity> retrieveAllMembers() throws EntityManagerException {
        Logger.log("LibraryOperationController", "retrieveAllMembers", "Using Local Interface");
        return memberEntityManager.retrieveAllMembers();
    }
    
    
    /************ BOOK_OPERATIONS ************/
    
    public BookEntity createNewBook(BookEntity newBookEntity) throws EntityManagerException {
        Logger.log("LibraryOperationController", "createNewBook", "Using Local Interface");
        return bookEntityManager.createNewBook(newBookEntity);
    }
    
    public List<BookEntity> retrieveAllBooks() throws EntityManagerException {
        Logger.log("LibraryOperationController", "retrieveAllBooks", "Using Local Interface");
        return bookEntityManager.retrieveAllBooks();
    }
}
