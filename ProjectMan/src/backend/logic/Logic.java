/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.logic;

import backend.datatypes.Employee;
import backend.datatypes.Team;
import backend.server.Data;
import backend.server.DataStatic;
import java.io.IOException;

/**
 *
 * @author TM
 */
public class Logic {
    
    Data data = new Data();
    
    
    public void add (Object emp)
    {
        if((Team)emp != null)
            data.add((Team)emp);
        if((Employee)emp != null)
            data.add((Employee)emp);
    }
    
    //TODO test this!
    //For static class
     public void AddToStatic (Object emp)
    {
        if((Team)emp != null)
            DataStatic.add((Team)emp);
        if((Employee)emp != null)
            DataStatic.add((Employee)emp);
    }
}
