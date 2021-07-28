package blockchain;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {
	
	private List <Block> chain;
	
	public BlockChain ()
	{
		chain = new ArrayList<Block>();
	}
	

	public Block getLatestBlock ()
	{
		return this.chain.get(chain.size()-1);
	}



	public void addBlock (Block myBlock)
	{
		Block newBlock = myBlock;
		
		if (chain.size()==0) {
				
			newBlock.setPreviousHash(null);
		}
		else 
		{
			
			newBlock.setPreviousHash(chain.get(chain.size()-1).getCurrentHash());	
		}
	   
		
		newBlock.calculateHash();
		this.chain.add(newBlock);
	}
	
	public boolean isBlockValid()
	{
		for (int i = chain.size()-1; i > 0; i--)
		{
			if (!(chain.get(i).getPreviousHash().equals(chain.get(i-1).getCurrentHash()))) 
			{
				System.out.println("This Block is not valid, Cannot add it to the Chain");
				return false;
			}
			
		}
		
		System.out.println("This Block is valid and is added");
		return true;
	}

	public List<Block> getChain() {
		return chain;
	}

	public void setChain(List<Block> chain) {
		this.chain = chain;
	}

}
