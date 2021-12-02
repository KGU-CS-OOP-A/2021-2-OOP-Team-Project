package org.corodiak.type;

public interface Factory<T extends Manageable> {
	public T create();
}