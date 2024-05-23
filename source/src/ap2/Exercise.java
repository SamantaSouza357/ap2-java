package ap2;

import java.util.Stack;

import javax.naming.directory.InvalidAttributesException;

import exceptions.EmptyTreeException;
import position.Position;
import source.LinkedBinaryTree;

public class Exercise {
	public static void main(String[] args) throws InvalidAttributesException, EmptyTreeException {
		LinkedBinaryTree<String> firstExpressionTree = buildExpression("(((5+2)*(2-1))/((2+9)+(7-2)-1))*8)");
		
		System.out.println("Desenho da expressão a partir da árvore:");
		printExpression(firstExpressionTree, firstExpressionTree.root());
		
		System.out.println();
		System.out.println();
		
		System.out.println("Árvore desenhada em plano cartesiano:");
		inOrderWithPositions(firstExpressionTree, firstExpressionTree.root(), 0, 0);
		
		// Como visto acima, a expressão de teste acima está incorreta, e para validar esse ponto, abaixo
		// está um teste com a expressão ((((3+1)*3)/((9-5)+2))-((3*(7-4))+6)), que foi retirada dos slides
		
		LinkedBinaryTree<String> secondExpressionTree = buildExpression("((((3+1)*3)/((9-5)+2))-((3*(7-4))+6))");
		
		System.out.println();
		System.out.println();
		
		System.out.println("Desenho da expressão a partir da árvore:");
		printExpression(secondExpressionTree, secondExpressionTree.root());
		
		System.out.println();
		System.out.println();
		
		System.out.println("Árvore desenhada em plano cartesiano:");
		inOrderWithPositions(secondExpressionTree, secondExpressionTree.root(), 0, 0);
	}
	
	// a
	public static LinkedBinaryTree<String> buildExpression(String expression) {
		Stack<LinkedBinaryTree<String>> emptyStack = new Stack<LinkedBinaryTree<String>>();
		String[] expressionArray = expression.split("");		
		for (String c : expressionArray) {
			if (!c.equals("(") && !c.equals(")")) {
				LinkedBinaryTree<String> newTree = new LinkedBinaryTree<String>();
				newTree.addRoot(c);
				emptyStack.push(newTree);
			} else if (c.equals("(")) {
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
	
	//b
	public static void binaryPreOrder(LinkedBinaryTree<String> tree, Position<String> position) {
		if (tree.root() == null) return;
		 System.out.print(" " + tree.root());
	     binaryPreOrder(tree, tree.left(position));
	     binaryPreOrder(tree, tree.right(position));
	}
	
	// c
	public static void binaryPostOrder(LinkedBinaryTree<String> tree, Position<String> position) {
		if (tree.hasLeft(position)) {
			binaryPostOrder(tree, tree.left(position));
		}
		if (tree.hasRight(position)) {
			binaryPostOrder(tree, tree.right(position));
		}
		System.out.print(position.element());
	}
	
	// d
	public static int evaluateExpression(LinkedBinaryTree<String> tree, Position<String> position) throws InvalidAttributesException{
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
	public static void inOrder(LinkedBinaryTree<String> tree, Position<String> position) {
		if (tree.hasLeft(position)) {
			inOrder(tree, tree.left(position));
		}
		System.out.print(position.element());
		if (tree.hasRight(position)) {
			inOrder(tree, tree.right(position));
		}
	}
	
	// f 
	public static LinkedBinaryTree<Integer> makerBtSearch(){
    	LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<Integer>();
    	tree.addRoot(58);
    	tree.insertLeft(tree.root(), 31);
    	tree.insertRight(tree.root(), 90);
    	
    	tree.insertLeft(tree.left(tree.root()), 25);
    	tree.insertRight(tree.left(tree.root()), 42);
    	
    	tree.insertLeft(tree.left(tree.left(tree.root())), 12);
    	tree.insertLeft(tree.right(tree.left(tree.root())), 36);
    	
    	tree.insertLeft(tree.right(tree.root()), 62);
    	tree.insertRight(tree.left(tree.right(tree.root())), 75);
    	
    	return tree;
    }
	
	// g
	public static void inOrderWithPositions(LinkedBinaryTree<String> tree, Position<String> position, int depth, int counter) {
		if (tree.hasLeft(position)) {
			inOrderWithPositions(tree, tree.left(position), depth + 1, counter++);
		}
		System.out.println("Element: " + position.element() + ", x: " + counter + ", y: " + depth);
		if (tree.hasRight(position)) {
			inOrderWithPositions(tree, tree.right(position), depth + 1, counter++);
		}
	}
	
	// h
	public static void eulerTour(LinkedBinaryTree<String> tree, Position<String> position) {
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
	public static void printExpression(LinkedBinaryTree<String> tree, Position<String> position) {
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
