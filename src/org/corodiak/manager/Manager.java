package org.corodiak.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.corodiak.type.Factory;
import org.corodiak.type.Manageable;

public abstract class Manager<T extends Manageable> {

	private List<T> list = new ArrayList<T>();

	public T find(String kwd) {
		for(T t:list) {
			if(t.matches(kwd))
				return t;
		}
		return null;
	}
	
	public boolean delete(T t) {
		return list.remove(t);
	}
	
	public boolean delete(String code) {
		T t = null;
		for(T tmp:list) {
			if(tmp.matches(code)) {
				t = tmp;
				break;
			}
		}
		return list.remove(t);
	}
	
	public void add(T t) {
		list.add(t);
	}
	
	protected Scanner openFile(String filename) {
		Scanner filein = null;
		try {
			filein = new Scanner(new File(filename));
		} catch (Exception e) {
			System.out.println(filename + ": 파일 없음");
			System.exit(0);
		}
		return filein;
	}
	
	public void readAll(String filename, Factory<T> factory) {
		Scanner filein = openFile(filename);
		T t = null;
		while(filein.hasNext()) {
			t = factory.create();
			t.read(filein);
			list.add(t);
		}
		filein.close();
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
