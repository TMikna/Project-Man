package backend.datatypes;

public class EmployeeTimes
{
    private int[] starts;    //Settings
    private int[] ends;      //(Using int because it is enough and saves memory
    
    private int totalPerWeek;
    
    public EmployeeTimes()
    {
        starts = new int[]{8, 8, 8, 8, 8};
        ends = new int[]{16, 16, 16, 16, 16};
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
}
