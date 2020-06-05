import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;

public class HelperMethods {

	public static PrintStream printStream;

	private static byte[] getSHA(String input) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	private static String toHexString(byte[] hash) {

		BigInteger number = new BigInteger(1, hash);

		StringBuilder hexString = new StringBuilder(number.toString(16));

		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}

	public static String hash(String string) {
		try {
			return toHexString(getSHA(string));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String arrayListToString(ArrayList<T> arrayList) {

		String result = "\n";

		for (int i = 0; i < arrayList.size(); i++) {
			result += arrayList.get(i).toString();
			if (i != arrayList.size() - 1)
				result += "\n";
		}

		return result;

	}

	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();
		return pair;
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	public static String sign(String plainText, PrivateKey privateKey) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(privateKey);
		privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));

		byte[] signature = privateSignature.sign();

		return Base64.getEncoder().encodeToString(signature);
	}

	public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));

		byte[] signatureBytes = Base64.getDecoder().decode(signature);

		return publicSignature.verify(signatureBytes);
	}

	public static void printAndWrite(String string) throws IOException {

		System.out.println(string);

		printStream.println(string);

	}
}
