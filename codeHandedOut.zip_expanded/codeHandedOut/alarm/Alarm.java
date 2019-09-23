package xxx.alarm;

import java.util.LinkedList;
import java.util.List;

import xxx.time.TimeType;

public class Alarm implements AlarmType
  {
  private boolean active;
  private TimeType time;
  private List<AlarmActionType> handlers = new LinkedList<AlarmActionType>();
  
  public Alarm(TimeType time, AlarmActionType handler)
    {
    setTime(time);
    addAlarmActionHandler(handler);
    active = true;
    }
  
  @Override
  public void setActive(boolean active)
    {
    //...
    }

  @Override
  public boolean isActive()
    {
    //...
    }

  @Override
  public void setTime(TimeType time)
    {
    //...
    }

  @Override
  public TimeType getTime()
    {
    //...
    }

  @Override
  public void addAlarmActionHandler(AlarmActionType handler)
    {
    // addera handler till listan handlers
    }

  @Override
  public void doAlarm()
    {
    if(active)
      for(AlarmActionType handler : handlers) // En "for-each" loop
        handler.alarmActivated();    
    }
  
  public String toString()
    {
    //... delegera till time
    }
  }
