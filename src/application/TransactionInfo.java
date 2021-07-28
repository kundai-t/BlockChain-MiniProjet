package application;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class TransactionInfo extends TransactionData implements List<TransactionData> {

	public TransactionInfo(Date datetime,List<String> myList) {
		
		this.datetime = datetime;
		this.trans = myList;
	}
	
	@Override
	public int compareTo(TransactionData o) {
		
		return 0;
	}

	@Override
	public int size() {
		
		return 0;
	}

	@Override
	public boolean isEmpty() {
		
		return false;
	}

	@Override
	public boolean contains(Object o) {
		
		return false;
	}

	@Override
	public Iterator<TransactionData> iterator() {
		
		return null;
	}

	@Override
	public Object[] toArray() {
		
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(TransactionData e) {
		
		return false;
	}

	@Override
	public boolean remove(Object o) {
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends TransactionData> c) {
		
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends TransactionData> c) {
		
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		
		return false;
	}

	@Override
	public void clear() {
		
		
	}

	@Override
	public TransactionData get(int index) {
		
		return null;
	}

	@Override
	public TransactionData set(int index, TransactionData element) {
		
		return null;
	}

	@Override
	public void add(int index, TransactionData element) {
		
		
	}

	@Override
	public TransactionData remove(int index) {
		
		return null;
	}

	@Override
	public int indexOf(Object o) {
		
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		
		return 0;
	}

	@Override
	public ListIterator<TransactionData> listIterator() {
		
		return null;
	}

	@Override
	public ListIterator<TransactionData> listIterator(int index) {
		
		return null;
	}

	@Override
	public List<TransactionData> subList(int fromIndex, int toIndex) {
		
		return null;
	}

}
