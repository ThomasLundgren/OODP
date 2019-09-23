package xxx.clock;
import java.util.Collection;
import xxx.alarm.AlarmType;
import xxx.time.TimeType;

public interface AlarmClockType
  {
  public void tickTack();
  public void setTime(TimeType time);
  public void addAlarm(AlarmType larm);
  public void removeAlarm(AlarmType alarm);
  public Collection<AlarmType> getAlarms();
  public TimeType getTime();
  public String toString();
  }
