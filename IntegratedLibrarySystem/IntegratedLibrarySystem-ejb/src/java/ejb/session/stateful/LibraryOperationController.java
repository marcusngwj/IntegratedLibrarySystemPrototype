/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import dao.StaffEntityManager;
import entity.StaffEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;

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

    
}
