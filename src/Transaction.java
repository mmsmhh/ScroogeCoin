import java.util.ArrayList;
import java.util.UUID;

public class Transaction {

	private UUID transactionID;

	private UUID senderID;
	private UUID receiverID;

	private TransactionType transactionType;

	private ArrayList<Coin> coins;

	private String transactionHash;
	private String previousTransactionHash;

	private String signature;

	public Transaction(UUID senderID, UUID receiverID, ArrayList<Coin> coins, TransactionType type) {

		UUID uuid = UUID.randomUUID();

		this.transactionID = uuid;

		transactionType = type;

		this.senderID = senderID;

		this.receiverID = receiverID;

		this.coins = coins;

	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getPreviousTransactionHash() {
		return previousTransactionHash;
	}

	public void setPreviousTransactionHash(String previousTransactionHash) {
		this.previousTransactionHash = previousTransactionHash;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public UUID getTransactionID() {
		return transactionID;
	}

	public UUID getSenderID() {
		return senderID;
	}

	public UUID getReceiverID() {
		return receiverID;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	@Override
	public String toString() {
		return "$$$$$$$$$$$$$$$$$$$$" + "\n" + "Transaction" + "\n" + "transactionID=" + transactionID + "\n"
				+ "senderID=" + senderID + "\n" + "receiverID=" + receiverID + "\n" + "transactionType="
				+ transactionType + "\n" + "coins=" + HelperMethods.arrayListToString(coins) + "\n" + "transactionHash="
				+ transactionHash + "\n" + "previousTransactionHash=" + previousTransactionHash + "\n" + "signature="
				+ signature + "\n" + "$$$$$$$$$$$$$$$$$$$$";
	}

	public String getStringToHash() {
		return "Transaction [transactionID=" + transactionID + "]";
	}

}
