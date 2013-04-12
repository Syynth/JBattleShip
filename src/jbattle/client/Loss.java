/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public class Loss extends Action implements Result {

    @Override
    public boolean getResult() {
        return true;
    }
    
    @Override
    public String toString() {
        return "<result type=\"loss\" id=\"-1\" value=\"true\" />";
    }
}
