/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.logic;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author TM
 */
public final class Statics
{
/*****************************************************************************************
 * Constants
 *****************************************************************************************/
    
public static final String COMPANY = "Projct Man";
public static final String COMPANYDOMAIN = "projctman.com"; //[Tomas] Not sure if name is good, also could be a method for tht but since name is declared here this way avoids unessecary coding

    
    
    
/******************************************************************************************
 * Static methods
 *****************************************************************************************/

    public static void updatePersonalDayTableColumns(int from, int count, TableView<TableColumn<String, String>> table)
    {
        table.getColumns()
             .clear();
        for (int i = 0; i < count; ++i)
        {
            table.getColumns()
                 .add(new TableColumn<>((from + i > 24
                                         ? from + i - 24
                                         : from + i) + "-" + (from + i + 1 > 24
                                                              ? from + i - 23
                                                              : from + i + 1)));
        }
    }
}
