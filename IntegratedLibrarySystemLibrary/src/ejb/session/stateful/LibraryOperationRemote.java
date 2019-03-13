/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import entity.MemberEntity;
import util.exception.EntityManagerException;

/**
 *
 * @author Marcus
 */
public interface LibraryOperationRemote {
    MemberEntity createNewMember(String firstName, String lastName, String gender, Integer age, String identityNumber, String phone, String address) throws EntityManagerException;
}
