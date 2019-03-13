/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.BookEntity;
import entity.MemberEntity;
import java.util.List;
import javax.ejb.Local;
import util.exception.EntityManagerException;

/**
 *
 * @author Marcus
 */
@Local
public interface LibraryOperationControllerLocal {
    
    /** MEMBER_OPERATIONS**/
    MemberEntity createNewMember(MemberEntity newMemberEntity) throws EntityManagerException;
    List<MemberEntity> retrieveAllMembers() throws EntityManagerException;
    
    /** BOOK_OPERATIONS**/
    BookEntity createNewBook(BookEntity newBookEntity) throws EntityManagerException;
    List<BookEntity> retrieveAllBooks() throws EntityManagerException;
}
