import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Simulation extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	public static PrintStream printStream;

	public static ArrayList<User> users;

	public Simulation() throws FileNotFoundException {

		JPanel panel = new JPanel();
		JLabel label = new JLabel("Press space to exit!");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(500, 500);
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		panel.setLayout(new GridBagLayout());
		panel.add(label);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(panel);
		addKeyListener(this);
		setVisible(true);

		HelperMethods.printStream = new PrintStream(new File("output.txt"));
	}

	public static void main(String[] args) throws Exception {

		new Simulation();

		users = new ArrayList<User>();

		Scrooge scrooge = new Scrooge();

		HelperMethods.printAndWrite("Loading please wait!");

		for (int i = 0; i < 100; i++) {

			User newUser = new User();

			users.add(newUser);

			scrooge.createCoins(10, users.get(i).getUserID());
		}

		for (int i = 0; i < 100; i++) {
			HelperMethods.printAndWrite(users.get(i).toString());
		}

		HelperMethods.printAndWrite("Starting random transactions!");

		while (true) {
			int userOneIndex = HelperMethods.getRandomNumber(0, users.size() - 1);

			int userTwoIndex = HelperMethods.getRandomNumber(0, users.size() - 1);

			while (userOneIndex == userTwoIndex) {
				userTwoIndex = HelperMethods.getRandomNumber(0, users.size() - 1);
			}

			User UserOne = users.get(userOneIndex);

			if (UserOne.getCoins().size() < 1)
				continue;

			User UserTwo = users.get(userTwoIndex);

			int amount = HelperMethods.getRandomNumber(1, UserOne.getCoins().size() - 1);

			scrooge.sendCoins(amount, UserOne.getUserID(), UserTwo.getUserID());

		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.exit(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
