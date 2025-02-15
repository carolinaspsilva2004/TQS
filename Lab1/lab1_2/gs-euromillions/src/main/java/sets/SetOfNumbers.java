package sets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * a simple set of numbers with helper set operators
 * @author ico0
 */
public class SetOfNumbers  implements Iterable<Integer> {
	
	private ArrayList<Integer> collection = null;
	

	public static SetOfNumbers fromArray( int[] values) {
		SetOfNumbers newSet = new SetOfNumbers();
		for( int n : values) {
			newSet.add( n);
		}
		return newSet;
	}
	
	public SetOfNumbers() {
		super();
		collection = new ArrayList<Integer>();
	}
        
        public int size() {
            return this.collection.size();
        }

        /**
         * put a new member in the set; duplicates not allowed
         * @param element 
         */
	public void add(int element) {
		if( this.collection.contains(element )) {
			throw new IllegalArgumentException("duplicate value: " + element);
		}
		
		collection.add( element);		
	}

	public boolean intersects(SetOfNumbers subset) {
		return false;
	}

	/**
	 * subtract (exclude) the elements in the passed subset from the current set
	 * @return
	 */
	public void  subtract (SetOfNumbers subset) {
		 //TODO: implement this method
	}


	public  boolean contains(Integer element) {
		return collection.contains( element);
	}

	@Override
	public Iterator<Integer> iterator() {		
		return collection.iterator();
	}


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.collection);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SetOfNumbers other = (SetOfNumbers) obj;
        if (!Objects.equals(this.collection, other.collection)) {
            return false;
        }
        return true;
    }
              
	
}