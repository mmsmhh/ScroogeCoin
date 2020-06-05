import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.UUID;

public class User {

	private UUID userID;
	private KeyPair keyPair;
	private ArrayList<Coin> coins;

	public User() throws NoSuchAlgorithmException {

		UUID uuid = UUID.randomUUID();

		this.userID = uuid;

		this.keyPair = HelperMethods.generateKeyPair();
		this.coins = new ArrayList<Coin>();
	}

	public void addCoin(Coin coin) {
		coins.add(coin);
	}

	public void removeCoin(UUID coinID) {
		coins.removeIf(coin -> coin.getCoinID().equals(coinID));
	}

	public UUID getUserID() {
		return userID;
	}

	public PublicKey getPublicKey() {
		return keyPair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}

	public ArrayList<Coin> getCoins() {
		return coins;
	}

	@Override
	public String toString() {
		return "--------------------" + "\n" + "User" + "\n" + "userID=" + userID + "\n" + ", publicKey="
				+ getPublicKey() + "\n" + "PrivateKey=" + getPrivateKey() + "\n" + "coins=" + coins.size() + "\n"
				+ "--------------------";
	}

	public String getStringToHash() {
		return "User [userID=" + userID + "]";
	}

}
