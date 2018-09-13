package backend;

import java.time.LocalDate;
import java.util.List;

public class Event
{
    private boolean importanceMandatory, reminder;
    private List<Employee> targets;
    private LocalDate eventDate;
    private int from, to, reminderHr;
    private String eventName, eventDescription;
    
    public Event(boolean importanceMandatory, boolean reminder, List<Employee> targets, LocalDate eventDate, int from, int to, int reminderHr, String eventName, String eventDescription)
    {
        this.importanceMandatory = importanceMandatory;
        this.reminder = reminder;
        this.targets = targets;
        this.eventDate = eventDate;
        this.from = from;
        this.to = to;
        this.reminderHr = reminderHr;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
    }
}
