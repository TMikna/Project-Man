/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.logic;

import backend.datatypes.Employee;
import backend.datatypes.Team;
import backend.server.Data;
import java.io.IOException;

/**
 *
 * @author TM
 */
public class Logic {
    
        //TODO test this!
    
    
     public void add (Object emp)
    {
        if((Team)emp != null)
            Data.add((Team)emp);
        if((Employee)emp != null)
            Data.add((Employee)emp);
    }
}
