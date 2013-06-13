package com.ds.poc.springrest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class uses for a pseudo persistance of entities computer
 * 
 */
public class ComputerStorage {
	
	private static long uniqueId = 0;
	private static final Object addLock = new Object();
	private static final Object deleteUpdateLock = new Object();

	private static final HashMap<Long,Computer> computers = new HashMap<Long, Computer>();

	public void delete(long id) {
		
		synchronized (deleteUpdateLock) {
			computers.remove(new Long(id));
		}
	
	}

	public long add(Computer computer) {
		Computer copy = copyComputer(computer);
		copy.setId(newUniqId());
		computers.put(copy.getId(), copy);
		return copy.getId();
	}
	
	public List<Computer> getAll() {
		List<Computer> res = new ArrayList<Computer>();
		Set<Entry<Long, Computer>> entrySet = computers.entrySet();
		for (Entry<Long, Computer> entry : entrySet) {
			res.add(copyComputer(entry.getValue()));
		}
		return res;
	}
	
	public void update(Computer computer){
		Computer copy = copyComputer(computer);

		synchronized (deleteUpdateLock) {
			if (computers.containsKey(new Long(computer.getId()))){
				computers.put(copy.getId(), computer);
			}
			else{
				throw new RuntimeException("No computer with the id : "+copy.getId());
			}
		}
	}

	private long newUniqId(){
		long res=0;
		
		synchronized (addLock) {
			uniqueId++;
			res = uniqueId;
		}
		
		return res;
	}

	private Computer copyComputer(Computer computer) {
		Computer res = new Computer();
		res.setBrand(computer.getBrand());
		res.setDescription(computer.getDescription());
		res.setModel(computer.getModel());
		res.setReference(computer.getReference());
		res.setId(computer.getId());
		return res;
	}

}
