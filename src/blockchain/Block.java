package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import application.Transaction;


public class Block {
	
	
	private Date datetime;
	private String currentHash;
	private String previousHash;
	private List <Transaction> trans;
	private int nonce;
	

	public int getNonce() {
		return nonce;
	}
	
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

	public Block(Date datetime, List<Transaction> trans) {
		
		this.datetime = datetime;		
		this.trans = trans;
		this.currentHash = calculateHash();
	}	
	
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




	public List<Transaction> getTrans() {
		return trans;
	}




	public void setTrans(List<Transaction> trans) {
		this.trans = trans;
	}

	//creating the hash algorithm to encrypt the block
	public String calculateHash ()
	{
		
		String toHash = this.datetime + this.previousHash + this.trans;
		
		MessageDigest digest;
		String encoded = null;
		String changed = null;
		
		try 
		{
			digest = MessageDigest.getInstance("SHA-256");
			byte [] hashbyte = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
			
			encoded = Base64.getEncoder().encodeToString(hashbyte);
			
			changed = encoded.replaceAll("\\d", "9");
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			System.out.println("Error on the hash algorithm");
		}
		
		this.currentHash = changed;
		return changed;
		
	}

}
