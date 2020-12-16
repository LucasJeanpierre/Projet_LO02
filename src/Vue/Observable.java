package Vue;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class Observable {

	private Set<Observer> listObserver;
	private boolean hasChanged;

	public Observable() {
		listObserver = new HashSet<Observer>();
	}

	public void addObserver(Observer o) {
		this.listObserver.add(o);
	}

	public void deleteObserver(Observer o) {
		this.listObserver.remove(o);
	}

	public void deleteObservers() {
		this.listObserver.clear();
	}

	public int countObserver() {
		return listObserver.size();
	}

	protected void clearChanged() {
		this.hasChanged = false;
	}

	protected void setChanged() {
		this.hasChanged = true;
	}

	protected boolean hesChanged() {
		return true;
	}

	public void notifyObservers() {
		if (this.hasChanged){
			Iterator<Observer> it = this.listObserver.iterator();
			while (it.hasNext()) {
				Observer o = it.next();
				o.update(this);
			}
			this.hasChanged=false;
		}
	}
	

	public void notifyObserver(Observer o) {
        if (this.hasChanged) {
            o.update(this);
            this.hasChanged = false;
        }
	}

}
