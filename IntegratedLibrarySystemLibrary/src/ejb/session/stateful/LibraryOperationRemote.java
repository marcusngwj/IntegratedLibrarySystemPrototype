/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.BookEntity;
import entity.MemberEntity;
import javafx.util.Pair;
import util.exception.BookNotFoundException;
import util.exception.EntityManagerException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author Marcus
 */
public interface LibraryOperationRemote {
    MemberEntity createNewMember(String firstName, String lastName, String gender, Integer age, String identityNumber, String phone, String address) throws EntityManagerException;

    Pair<MemberEntity, BookEntity> lendBook(Long bookId, String memberIdentityNumber) throws MemberNotFoundException, BookNotFoundException, EntityManagerException;
}
