import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
/* References: 
 * https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/
 * https://stackoverflow.com/questions/24086968/tell-if-string-contains-a-z-chars
 */

//HuffmanNode class
class HuffmanNode {
	
	double data;
	char c;
	
	HuffmanNode left;
	HuffmanNode right;
	
}//close class

//compareData class
class compareData implements Comparator<HuffmanNode> {
	
	//compare method
	public int compare (HuffmanNode x, HuffmanNode y) {
		
		//if-else x is less than y in frequency 
		if (x.data - y.data < 0) {
			
			return -1;
			
		} else {
			
			return 1;
			
		}//close if-else x is less than y in frequency
		
	}//close compare method
	
}//close class

//HuffmanTester class
public class HuffmanTester {

	//Create a Huffman algorithm that will read a text file and use it to create a Huffman algorithm
	//Use that algorithm to decode or encode a string of binary numbers or alphabetical characters
	
	//printCode
	public static void printCode(HuffmanNode root, String s) {
		
		//if node has no children and the character in the node is a letter
		if (root.left == null && root.right == null && Character.isLetter(root.c)) {
			
			//print character with its corresponding binary code
			System.out.println(root.c + ": " + s);
			
			return;
			
		}//close if command printing codes
		
		//recursive call printCode
		printCode(root.left, s + "0");
        printCode(root.right, s + "1");
		
	}//close printCode method
	
	//readTextFile method
	public static PriorityQueue<HuffmanNode> readTextFile(String file) {
		
		PriorityQueue<HuffmanNode> huffmanNodes = new PriorityQueue<HuffmanNode>(100, new compareData());
		
		//try-catch priority queue list making
		try {
			
			//initialize variables
			File txt = new File(file);
			Scanner in = new Scanner(txt);
			
			//while text file still has data
			while (in.hasNextLine()) {
				
				//create new node
				HuffmanNode hn = new HuffmanNode();
				hn.c = in.next().charAt(0);
				hn.data = in.nextDouble();
				hn.left = null;
				hn.right = null;
				
				//add node to priority queue
				huffmanNodes.add(hn);
				
			}//close while text file still has data
			
			in.close();
			
		} catch (FileNotFoundException ex) {
			
			System.out.println("File not found. Maybe you didn't rename the file?");
			
		}//close try-catch priority queue list making
		
		//return a list of Huffman nodes
		return huffmanNodes;
		
	}//close ReadTextFile
	
	//encodeString
	public static void encodeChar(String input, String code, HuffmanNode root, HuffmanNode pointer) {
		
		//if-else input char does not equal pointer char
		if (input.charAt(0) != pointer.c) {
			
			//if left pointer is not null
			if (pointer.left != null) {
				
				//add 0 to code and recursively call encodeChar method
				code = code + "0";
				encodeChar(input, code, root, pointer.left);
				
				//undo addition in case dead end is reached
				code = code.substring(0, code.length() - 1);
				
			}//close if left pointer is not null
			
			//if right pointer is not null
			if (pointer.right != null) {
				
				//add 1 to code and recursively call encodeChar method
				code = code + "1";
				encodeChar(input, code, root, pointer.right);
				
				//undo addition in case dead end is reached
				code = code.substring(0, code.length() - 1);
				
			}//close if right pointer is not null
			
		} else {
			
			//print complete code and return
			System.out.print(code);
			
		}//close if-else input char does not equal pointer char
		
	}//close encodeString
	
	//decodeString
	public static String decodeString(String decodedString, String input, HuffmanNode root) {
		
		//initialize variables
		HuffmanNode pointer = root;
		
		//for each character in string
		for (int i = 0; i < input.length(); i++) {
			
			//if-else input at i is 0
			if (input.charAt(i) == '0' && pointer.c == '-') {
				
				//move pointer to right node
				pointer = pointer.left;
				
			} else if (input.charAt(i) == '1' && pointer.c == '-') {
				
				//move pointer to left node
				pointer = pointer.right;
				
			}//close if-else input at i is 0
			
			//if pointer is not null
			if (pointer.c != '-') {
				
				//add character to decodedString
				decodedString = decodedString + pointer.c;
				
				//reset pointer
				pointer = root;
				
			}//close if pointer is not null
			
		}//close for each character in string
		
		//return decoded string
		return decodedString;
		
	}//close decodeString
	
	//main method
	public static void main(String[] args) {

		//initialize variables
		HuffmanNode root = null;
		PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(100, new compareData());
		Scanner in = new Scanner(System.in);
		String file = "";
		String input = "";
		boolean done = false;
		
		//get file name from user input
		System.out.print("\nEnter file name to construct Huffman algorithm: ");
		file = in.nextLine();
		
		//get priority queue from file
		q = readTextFile(file);
		
		/*
		 * Code for creating a Huffman Priority Queue taken from here:
		 * https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/
		 */
		
		//while more than 1 node exists in queue
		while (q.size() > 1) {
			  
            //first min extract
            HuffmanNode x = q.peek();
            q.poll();
  
            //second min extract
            HuffmanNode y = q.peek();
            q.poll();
  
            //new node f
            HuffmanNode f = new HuffmanNode();
  
            //to the sum of the frequency of the two nodes assigning values to the f node.
            f.data = x.data + y.data;
            f.c = '-';
  
            //first extracted node as left child.
            f.left = x;
  
            //second extracted node as the right child.
            f.right = y;
  
            //marking the f node as the root node.
            root = f;
  
            //add this node to the priority-queue.
            q.add(f);
            
        }//close while more than 1 node exists in queue
  
        // print the codes by traversing the tree
		System.out.println("\nCode guide:");
        printCode(root, "");
        
        //Ask user to input a sequence of binary characters or string of alphabetical characters
	    System.out.println("\nEnter a string of alphabetical characters to encode. Otherwise, enter a string of binary characters to decode.");
	    input = in.nextLine();
        
        //do-while user wants to continue with program
        do {
        	
		    //while input has not been verified
		    while (!done) {
		    	
		    	/*
		    	 * Code for checking if a string contains letters from here:
		    	 * https://stackoverflow.com/questions/24086968/tell-if-string-contains-a-z-chars
		    	 */
		    	
		    	//if input contains both a letter and a binary character
		    	if ((input.matches(".*[a-z].*") && (input.contains("0") || input.contains("1"))) || input.matches(".*[2-9].*")) {
		    		
		    		System.out.println("Sorry, this is not a valid input. Please try again.");
		    		input = in.nextLine();
		    		
		    	} else {
		    		
		    		done = true;
		    		
		    	}//close if input contains both a letter and a binary character
		    	
		    }//close while input has not been verified
		    
		    //if input contains letters
		    if (input.matches(".*[a-z].*")) {
		    	
		    	//make letters uppercase
		    	input = input.toUpperCase();
		    	
		    }//close if input contains letters
		    
		    //print input to user
		    System.out.println("This is your input: " + input);
		    
		    //if-else input is string of letters
		    if (input.matches(".*[A-Z].*")) {
		    	
		    	//print encodedString
		    	System.out.print("Here is your encoded string: ");
		    	
		    	//for each character in string
		    	for (int i = 0; i < input.length(); i++) {
		    		
		    		//print encoded version of character
		    		encodeChar(input.substring(i), "", root, root);
		    		
		    	}//for each character in string
		    	
		    	System.out.println();
		    	
		    } else {
		    	
		    	//print decodedString
		    	System.out.println("Here is your decoded string: " + decodeString("", input, root));
		    	
		    }//close if-else input contains letters
		    
		    System.out.println("To continue, enter another string of letters or numbers. To stop, hit enter.");
		    
		    input = in.nextLine();
		    
        } while (!input.equals(""));
        
        System.out.println("Program terminated.\n");
        
        //close scanner
        in.close();
        
	}//close main

}//close class
