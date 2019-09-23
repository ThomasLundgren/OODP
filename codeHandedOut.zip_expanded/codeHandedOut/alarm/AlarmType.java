package xxx.alarm;
import xxx.time.TimeType;

public interface AlarmType
  {
  public void setActive(boolean active);
  public boolean isActive();
  public void setTime(TimeType time);
  public TimeType getTime();
  public void addAlarmActionHandler(AlarmActionType handler);
  public void doAlarm();
  }
