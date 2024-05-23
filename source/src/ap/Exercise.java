package ap;

import java.util.Stack;

import javax.naming.directory.InvalidAttributesException;

import position.Position;
import source.LinkedBinaryTree;

public class Exercise {
	public static void Main(String[] args) {
		System.out.println("seila");
	}
	
	// b
	public LinkedBinaryTree<String> buildExpression(String expression) {
		Stack<LinkedBinaryTree<String>> emptyStack = new Stack<LinkedBinaryTree<String>>();
		String[] expressionArray = expression.split("");		
		for (String c : expressionArray) {
			if (c != "(" && c != ")") {
				LinkedBinaryTree<String> newTree = new LinkedBinaryTree<String>();
				newTree.addRoot(c);
				emptyStack.push(newTree);
			} else if (c == "(") {
				continue;
			} else {
				LinkedBinaryTree<String> first = emptyStack.pop();
				LinkedBinaryTree<String> second = emptyStack.pop();
				LinkedBinaryTree<String> third = emptyStack.pop();
				second.attach(second.root(), third, first);
				emptyStack.push(second);
			}
        }
		return emptyStack.pop();
	}
	
	// c
	public void binaryPostOrder(LinkedBinaryTree<String> tree, Position<String> position) {
		if (tree.hasLeft(position)) {
			binaryPostOrder(tree, tree.left(position));
		}
		if (tree.hasRight(position)) {
			binaryPostOrder(tree, tree.right(position));
		}
		System.out.print(position.element());
	}
	
	// d
	public int evaluateExpression(LinkedBinaryTree<String> tree, Position<String> position) throws InvalidAttributesException{
		if (tree.isInternal(position)) {
			int x = evaluateExpression(tree, tree.left(position));
			int y = evaluateExpression(tree, tree.right(position));
			
			switch (position.element()) {
				case "+":
					return x + y;
				case "-":
					return x - y;
				case "*":
					return x * y;
				case "/":
					return x / y;
				default:
					throw new InvalidAttributesException();
			}
		} else {
			return Integer.parseInt(position.element());
		}
	}
	
	// e
	public void inOrder(LinkedBinaryTree<String> tree, Position<String> position) {
		if (tree.hasLeft(position)) {
			inOrder(tree, tree.left(position));
		}
		System.out.print(position.element());
		if (tree.hasRight(position)) {
			inOrder(tree, tree.right(position));
		}
	}
	
	// g
	public void inOrderWithPositions(LinkedBinaryTree<String> tree, Position<String> position, int depth, int counter) {
		if (tree.hasLeft(position)) {
			inOrderWithPositions(tree, tree.left(position), depth + 1, counter++);
		}
		System.out.println("Element: " + position.element() + ", x: " + counter + ", y: " + depth);
		if (tree.hasRight(position)) {
			inOrderWithPositions(tree, tree.right(position), depth + 1, counter++);
		}
	}
	
	// h
	public void eulerTour(LinkedBinaryTree<String> tree, Position<String> position) {
		System.out.print(position.element());
		if (tree.hasLeft(position)) {
			eulerTour(tree, tree.left(position));
		}
		System.out.print(position.element());
		if (tree.hasRight(position)) {
			eulerTour(tree, tree.right(position));
		}
		System.out.print(position.element());
	}
	
	// i
	public void printExpression(LinkedBinaryTree<String> tree, Position<String> position) {
		if (tree.isInternal(position)) {
			System.out.print("(");
		}
		
		if (tree.hasLeft(position)) {
			printExpression(tree, tree.left(position));
		}
		
		if (tree.isInternal(position)) {
			System.out.print(position.element());
		} else {
			System.out.print(position.element());
		}
		
		if (tree.hasRight(position)) {
			printExpression(tree, tree.right(position));
		}
		
		if (tree.isInternal(position)) {
			System.out.print(")");
		}
	}
}
