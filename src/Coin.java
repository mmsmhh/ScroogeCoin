import java.util.UUID;

public class Coin {

	private UUID coinID;

	private String signature;

	public Coin() {

		UUID uuid = UUID.randomUUID();

		this.coinID = uuid;
	}

	public UUID getCoinID() {
		return coinID;
	}

	@Override
	public String toString() {
		return "********************" + "\n" + "Coin" + "\n" + "coinID=" + coinID + "\n" + "********************";
	}

	public String getStringToHash() {
		return "Coin [coinID=" + coinID + "]";
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
