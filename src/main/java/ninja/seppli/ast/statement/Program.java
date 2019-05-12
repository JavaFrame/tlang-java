package ninja.seppli.ast.statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ninja.seppli.ast.AstNode;

/**
 * the top most ast node
 * @author sebi
 *
 */
public class Program implements AstNode {
	/**
	 * the nodes
	 */
	private List<AstNode> nodes = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Program() {
	}

	/**
	 * Constructor
	 * @param nodes the nodes to add initialy
	 */
	public Program(AstNode[] nodes) {
		this.nodes.addAll(Arrays.asList(nodes));
	}

	/**
	 * Returns all nodes in an array
	 * @return the node array
	 */
	public AstNode[] getNodes() {
		return nodes.toArray(new AstNode[nodes.size()]);
	}

	/**
	 * adds the given ast node to the program
	 * @param n
	 */
	public void add(AstNode n) {
		nodes.add(n);
	}


	@Override
	public String getString() {
		StringBuilder out = new StringBuilder();
		for(AstNode n : nodes) {
			out.append(n.getString());
		}
		return out.toString();
	}

}
