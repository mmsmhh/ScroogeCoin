import java.util.ArrayList;

public class BlockChain {

	private ArrayList<Block> blocks;

	public BlockChain() {
		blocks = new ArrayList<Block>();
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void addBlock(Block block) {
		blocks.add(block);
	}

	@Override
	public String toString() {
		return "~~~~~~~~~~~~~~~~~~~~" + "\n" + "BlockChain" + "\n" + "blocks=" + HelperMethods.arrayListToString(blocks)
				+ "\n" + "~~~~~~~~~~~~~~~~~~~~";
	}

	public Block getLastBlock() {
		// TODO Auto-generated method stub
		return blocks.get(blocks.size() - 1);
	}

}
