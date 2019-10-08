package se.hig.thlu.storage;

import java.util.Collection;

public interface Dao<T> {

	public void saveAll(Collection<T> all);

	public Collection<T> getAll();

}
