package application;

import java.util.Date;
import java.util.List;

public abstract class TransactionData implements Comparable <TransactionData> {


	protected Date datetime;
	protected String currentHash;
	protected String previousHash;
	protected List<String> trans;
	
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getCurrentHash() {
		return currentHash;
	}
	public void setCurrentHash(String currentHash) {
		this.currentHash = currentHash;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	public List<String> getTrans() {
		return trans;
	}
	public void setTrans(List<String> trans) {
		this.trans = trans;
	}
	
	
	
}
