/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.logger;

/**
 *
 * @author Marcus
 */
public class Logger {
    public static void log(String className, String methodName) {
        System.out.println("[" + className + "] " + methodName);
    }
    
    public static void log(String className, String methodName, String message) {
        System.out.println("[" + className + "] " + methodName + " -> " + message);
    }
}
