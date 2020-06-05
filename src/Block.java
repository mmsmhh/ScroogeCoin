import java.util.ArrayList;
import java.util.UUID;

public class Block {

	private UUID blockID;
	private String blockHash;
	private String PreviousBlockHash;
	private ArrayList<Transaction> transactions;

	public Block() {

		UUID uuid = UUID.randomUUID();

		this.blockID = uuid;

		transactions = new ArrayList<Transaction>();

	}

	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public String getPreviousBlockHash() {
		return PreviousBlockHash;
	}

	public void setPreviousBlockHash(String previousBlockHash) {
		PreviousBlockHash = previousBlockHash;
	}

	public UUID getBlockID() {
		return blockID;
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
	}

	public int getSize() {
		return transactions.size();
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public Transaction getLastTransaction() {
		return transactions.get(transactions.size() - 1);
	}

	public boolean isEmpty() {
		return getSize() == 0;
	}

	@Override
	public String toString() {
		String blockHashString = (blockHash != null) ? "blockHash=" + blockHash + "\n" : "";
		String PreviousBlockHashString = (PreviousBlockHash != null) ? "PreviousBlockHash=" + PreviousBlockHash + "\n"
				: "";
		return "##########" + "\n" + "Block" + "\n" + "blockID=" + blockID + "\n" + blockHashString
				+ PreviousBlockHashString + "transactions=" + HelperMethods.arrayListToString(transactions) + "\n"
				+ "##########";
	}

	public String getStringToHash() {
		return "Block [blockID=" + blockID + "]";
	}

}
