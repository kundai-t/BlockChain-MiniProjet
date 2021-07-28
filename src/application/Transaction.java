package application;

/**@author KR Tambo
 * @version miniproject
 * 201707367
 * 
 * */

import java.util.ArrayList;
import java.util.List;

public class Transaction implements Comparable <Transaction> {
	
	List<String> trans = new ArrayList<String>();

	public Transaction(List<String> trans) {
		
		this.trans = trans;
	}

	public List<String> getTrans() {
		return trans;
	}

	public void setTrans(List<String> trans) {
		this.trans = trans;
	}

	@Override
	public int compareTo(Transaction trans) {
		
		return 0;
	}    

}
