package se.hig.thlu.model.counter;

public interface CounterType {

	public void count();

	public int getCount();

	public void reset();

	public void pause();

	public void resume();

}
