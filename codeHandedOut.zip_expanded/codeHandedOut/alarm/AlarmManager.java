package xxx.alarm;
import java.util.Collection;
import java.util.HashMap;

import xxx.time.TimeType;

public class AlarmManager
  {
  private HashMap<String,AlarmType> map = new HashMap<String,AlarmType>();
  
  public void addAlarm(AlarmType alarm)
    {
    map.put(alarm.toString(), alarm);
    }
  
  public void removeAlarm(AlarmType alarm)
    {
    map.remove(alarm.toString());
    }
  
  public Collection<AlarmType> getAlarms()
    {
    return map.values();
    }
  
  public void checkForAlarm(TimeType time)
    {
    AlarmType alarm = map.get(time.toString());
    if(alarm != null && alarm.isActive())
      alarm.doAlarm();
    }
  }
