package backend.datatypes;

import java.util.Arrays;

public class EmployeeTimes
{
    private int[] starts;       //Settings
    private int[] ends;         //
    private int totalPerWeek;   //Calculated from settings
    private int totalPerMonth;
    
    private int[] startsOverride;   //"Actual" work time to use for statistics
    private int[] endsOverride;     //holds it for a month and then dumps these... (by idea)
    private int totalPerMonthActual;
    
    public EmployeeTimes()
    {
        starts = new int[]{8, 8, 8, 8, 8};
        ends = new int[]{16, 16, 16, 16, 16};
        
        startsOverride = new int[31];
        endsOverride = new int[31];
        Arrays.fill(startsOverride, -1);
        Arrays.fill(endsOverride, -1);
    }
    
    public int[] getStarts()
    {
        return starts;
    }
    
    public void setStarts(int[] starts)
    {
        this.starts = starts;
    }
    
    public int[] getEnds()
    {
        return ends;
    }
    
    public void setEnds(int[] ends)
    {
        this.ends = ends;
    }
    
    public int getTotalPerWeek()
    {
        return totalPerWeek;
    }
    
    public void setTotalPerWeek(int totalPerWeek)
    {
        this.totalPerWeek = totalPerWeek;
    }
    
    public int[] getStartsOverride()
    {
        return startsOverride;
    }
    
    public int[] getEndsOverride()
    {
        return endsOverride;
    }
    
    public int getTotalPerMonth()
    {
        return totalPerMonth;
    }
    
    public void setTotalPerMonth(int totalPerMonth)
    {
        this.totalPerMonth = totalPerMonth;
    }
    
    public int getTotalPerMonthActual()
    {
        return totalPerMonthActual;
    }
    
    public void setTotalPerMonthActual(int totalPerMonthActual)
    {
        this.totalPerMonthActual = totalPerMonthActual;
    }
}
