/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.logic;

import java.util.Random;

/**
 *
 * @author TM
 */
public final class Statics { 
        
    
    public static String generateID()                          //TODO: consider if it's better to use integer or string
        {
        return Integer.toString(new Random().nextInt(0));  //TODO: generate actually unique ID
        }
    
}
