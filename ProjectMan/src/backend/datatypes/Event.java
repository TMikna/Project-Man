package backend.datatypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Event
{
    public enum Scale
    {
        PERSONAL, TEAM, PROJECT, COMPANY, OTHER
    }
    
    private boolean importanceMandatory, reminder;
    private List<Employee> targets;
    private LocalDate eventDate;
    private int from, to, reminderHr;
    private String eventName, eventDescription;
    private Scale scale;
    
    public Event(boolean importanceMandatory, Scale scale, boolean reminder, List<Employee> targets, LocalDate eventDate, int from, int to, int reminderHr, String eventName, String eventDescription)
    {
        this.importanceMandatory = importanceMandatory;
        this.scale = scale;
        this.reminder = reminder;
        this.targets = targets;
        this.eventDate = eventDate;
        this.from = from;
        this.to = to;
        this.reminderHr = reminderHr;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
    }
    
    public LocalDateTime getEventStartDateTime()
    {
        return LocalDateTime.of(eventDate, LocalTime.of(from, 0));
    }
    public LocalDateTime getEventEndDateTime()
    {
        return LocalDateTime.of(eventDate, LocalTime.of(to, 0));
    }
    
    public boolean isImportanceMandatory()
    {
        return importanceMandatory;
    }
    
    public boolean isReminder()
    {
        return reminder;
    }
    
    public List<Employee> getTargets()
    {
        return targets;
    }
    
    public LocalDate getEventDate()
    {
        return eventDate;
    }
    
    public int getFrom()
    {
        return from;
    }
    
    public int getTo()
    {
        return to;
    }
    
    public int getReminderHr()
    {
        return reminderHr;
    }
    
    public String getEventName()
    {
        return eventName;
    }
    
    public String getEventDescription()
    {
        return eventDescription;
    }
    
    public Scale getScale()
    {
        return scale;
    }
}
