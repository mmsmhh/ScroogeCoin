import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.UUID;

public class Scrooge {

	private KeyPair keyPair;
	private BlockChain blockChain;
	private Block tempBlock;
	private Block finalBlock;
	private UUID scroogeID;

	public Scrooge() throws IOException, NoSuchAlgorithmException {
		this.keyPair = HelperMethods.generateKeyPair();
		blockChain = new BlockChain();
		tempBlock = new Block();
		UUID uuid = UUID.randomUUID();
		scroogeID = uuid;
	}

	public void createCoins(int amount, UUID receiverID) throws Exception {

		ArrayList<Coin> coins = new ArrayList<Coin>();

		for (int i = 0; i < amount; i++) {

			Coin coin = new Coin();

			coin.setSignature(HelperMethods.sign(coin.toString(), getPrivateKey()));

			coins.add(coin);
		}

		Transaction newTransaction = new Transaction(scroogeID, receiverID, coins, TransactionType.CREATE);

		newTransaction.setTransactionHash(HelperMethods.hash(newTransaction.getStringToHash()));

		newTransaction.setSignature(HelperMethods.sign(newTransaction.getTransactionHash(), getPrivateKey()));

		if (!transactionIsDoubleSpending(newTransaction))
			addTransactionToTempBlock(newTransaction, getPublicKey());

	}

	private boolean transactionIsDoubleSpending(Transaction transaction) {

		ArrayList<Transaction> transactions = tempBlock.getTransactions();

		for (int i = 0; i < transactions.size(); i++) {

			ArrayList<Coin> oldCoins = transactions.get(i).getCoins();

			ArrayList<Coin> myCoins = transaction.getCoins();

			for (int j = 0; j < myCoins.size(); j++) {
				if (oldCoins.contains(myCoins.get(j)))
					return true;
			}

		}

		return false;
	}

	public void sendCoins(int amount, UUID senderID, UUID receiverID) throws Exception {

		User sender = null;

		for (User user : Simulation.users) {
			if (user.getUserID().equals(senderID)) {
				sender = user;
				break;
			}
		}

		ArrayList<Coin> coins = new ArrayList<Coin>();

		while (coins.size() != amount) {

			Coin coin = sender.getCoins().get(HelperMethods.getRandomNumber(0, sender.getCoins().size() - 1));

			if (!coins.contains(coin)) {
				coins.add(coin);
			}

		}

		Transaction newTransaction = new Transaction(senderID, receiverID, coins, TransactionType.TRANSFER);

		newTransaction.setTransactionHash(HelperMethods.hash(newTransaction.getStringToHash()));

		newTransaction.setSignature(HelperMethods.sign(newTransaction.getTransactionHash(), sender.getPrivateKey()));

		addTransactionToTempBlock(newTransaction, sender.getPublicKey());

	}

	public void addTransactionToTempBlock(Transaction transaction, PublicKey publicKey) throws Exception {

		if (!HelperMethods.verify(transaction.getTransactionHash(), transaction.getSignature(), publicKey))
			return;

		if (tempBlock.isEmpty()) {

			if (finalBlock != null) {
				transaction.setPreviousTransactionHash(finalBlock.getLastTransaction().getTransactionHash());
			}

		} else {
			transaction.setPreviousTransactionHash(tempBlock.getLastTransaction().getTransactionHash());
		}

		tempBlock.addTransaction(transaction);

		HelperMethods.printAndWrite(tempBlock.toString());

		if (tempBlock.getSize() == 10) {
			doTransactions(tempBlock);
			tempBlock.setBlockHash(HelperMethods.hash(tempBlock.getStringToHash()));
			if (finalBlock != null)
				tempBlock.setPreviousBlockHash(finalBlock.getBlockHash());
			blockChain.addBlock(tempBlock);
			HelperMethods.printAndWrite(blockChain.toString());
			finalBlock = blockChain.getLastBlock();
			tempBlock = new Block();
		}

	}

	private void doTransactions(Block block) {
		ArrayList<Transaction> transactions = block.getTransactions();

		for (int i = 0; i < transactions.size(); i++) {
			Transaction transaction = transactions.get(i);

			UUID senderID = transaction.getSenderID();
			UUID receiverID = transaction.getReceiverID();

			ArrayList<Coin> coins = transaction.getCoins();

			if (transaction.getTransactionType().equals(TransactionType.CREATE)) {

				int receiver = -1;

				for (int j = 0; j < Simulation.users.size(); j++) {
					if (Simulation.users.get(j).getUserID().equals(receiverID)) {
						receiver = j;
						break;
					}
				}

				for (int j = 0; j < coins.size(); j++) {
					Simulation.users.get(receiver).addCoin(coins.get(j));
				}

			} else {

				int receiver = -1;
				int sender = -1;

				for (int j = 0; j < Simulation.users.size(); j++) {
					if (Simulation.users.get(j).getUserID().equals(senderID)) {
						sender = j;
					} else if (Simulation.users.get(j).getUserID().equals(receiverID)) {
						receiver = j;
					}
				}

				for (int j = 0; j < coins.size(); j++) {
					Simulation.users.get(sender).removeCoin(coins.get(j).getCoinID());
					Simulation.users.get(receiver).addCoin(coins.get(j));
				}

			}

		}
	}

	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

}
