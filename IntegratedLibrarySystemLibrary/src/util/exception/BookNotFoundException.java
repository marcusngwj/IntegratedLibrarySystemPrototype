/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Marcus
 */
public class BookNotFoundException extends Exception {
    public BookNotFoundException() {}
    
    public BookNotFoundException(String msg) {
        super(msg);
    }
}
