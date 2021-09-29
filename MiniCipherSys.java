package mcs;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a mini cipher system.
 * 
 * @author RU NB CS112
 */
public class MiniCipherSys {
	
	/**
	 * Circular linked list that is the sequence of numbers for encryption
	 */
	SeqNode seqRear;
	
	/**
	 * Makes a randomized sequence of numbers for encryption. The sequence is 
	 * stored in a circular linked list, whose last node is pointed to by the field seqRear
	 */
	public void makeSeq() {
		// start with an array of 1..28 for easy randomizing
		int[] seqValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < seqValues.length; i++) {
			seqValues[i] = i+1;
		}
		
		// randomize the numbers
		Random randgen = new Random();
 	        for (int i = 0; i < seqValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = seqValues[i];
	            seqValues[i] = seqValues[other];
	            seqValues[other] = temp;
	        }
	     
	    // create a circular linked list from this sequence and make seqRear point to its last node
	    SeqNode sn = new SeqNode();
	    sn.seqValue = seqValues[0];
	    sn.next = sn;
	    seqRear = sn;
	    for (int i=1; i < seqValues.length; i++) {
	    	sn = new SeqNode();
	    	sn.seqValue = seqValues[i];
	    	sn.next = seqRear.next;
	    	seqRear.next = sn;
	    	seqRear = sn;
	    }
	}
	
	/**
	 * Makes a circular linked list out of values read from scanner.
	 */
	public void makeSeq(Scanner scanner) 
	throws IOException {
		SeqNode sn = null;
		if (scanner.hasNextInt()) {
			sn = new SeqNode();
		    sn.seqValue = scanner.nextInt();
		    sn.next = sn;
		    seqRear = sn;
		}
		while (scanner.hasNextInt()) {
			sn = new SeqNode();
	    	sn.seqValue = scanner.nextInt();
	    	sn.next = seqRear.next;
	    	seqRear.next = sn;
	    	seqRear = sn;
		}
	}
	
	/**
	 * Implements Step 1 - Flag A - on the sequence.
	 */
	void flagA() {
	    // COMPLETE THIS METHOD
		
		SeqNode head = seqRear.next;
		SeqNode node = head;
		SeqNode nextNode = node.next;
		SeqNode prevNode = seqRear;
		SeqNode tail = seqRear;
		SeqNode temp;
		
		do {  
			if (node.seqValue == 27) {
				if (node.next == head) { //if 27 is last
					int tailValue = seqRear.seqValue;
					int headValue = head.seqValue;
					seqRear.seqValue = headValue;
					head.seqValue = tailValue;
					break;
				}
				else if (node.next == seqRear) {//if 27 is second to last
					temp = prevNode.next;
					prevNode.next = node.next;
					node.next = node.next.next;
					nextNode.next = temp;
					seqRear = node;
					break;
				}
				
				else {
					temp = prevNode.next;
					prevNode.next = node.next;
					node.next = node.next.next;
					nextNode.next = temp;
					break;
				}
			}
			prevNode = node;
			node = node.next;
			nextNode = nextNode.next;
			
		}while (node != null);
		System.out.println("A");
		printList(seqRear);
	}
	
	/**
	 * Implements Step 2 - Flag B - on the sequence.
	 */
	void flagB() {
	    // COMPLETE THIS METHOD
		SeqNode head = seqRear.next;
		SeqNode node = head;
		SeqNode nextNode = head.next;
		SeqNode prevNode = seqRear;
		SeqNode temp;
		
		do {
			if (node.seqValue == 28) {
				if (node == seqRear) {// if 28 is last
					int tailValue = seqRear.seqValue;
					int headValue = head.seqValue;
					seqRear.seqValue = headValue;
					head.seqValue = tailValue;
					prevNode = node;
					nextNode = nextNode.next;
					node = node.next;
					temp = prevNode.next;
					prevNode.next = node.next;
					node.next = node.next.next;
					nextNode.next = temp;
					break;
				}else if (node.next == seqRear) {//if 28 is second to last
					temp = prevNode.next;
					prevNode.next = node.next;
					node.next = node.next.next;
					nextNode.next = temp;
					seqRear = node;
					prevNode = node;
					nextNode = nextNode.next;
					node = node.next;
					int tailValue = seqRear.seqValue;
					int headValue = head.seqValue;
					seqRear.seqValue = headValue;
					head.seqValue = tailValue;
					head = node;
					break;
				}
				else {
					temp = prevNode.next;
					prevNode.next = node.next;
					node.next = node.next.next.next;
					nextNode.next.next = temp;
					break;
				}
			}
			else {
				prevNode = node;
				nextNode = nextNode.next;
				node = node.next;
			}
			
		}while (node != null);
		System.out.println("B");
		printList(seqRear);
	}
	
	/**
	 * Implements Step 3 - Triple Shift - on the sequence.
	 */
	void tripleShift() {
	    // COMPLETE THIS METHOD
		SeqNode head = seqRear.next;
		SeqNode lastNode = seqRear;
		int nodeValue = head.seqValue;
		int firstNodeValue = head.seqValue;
		SeqNode firstFlag;
		SeqNode node = head;
		if ((seqRear.seqValue == 28 || seqRear.seqValue == 27) && (head.seqValue == 28 || head.seqValue == 27)) {
			
		}
		else if (head.seqValue == 27 || head.seqValue == 28) {//if nothing before first flag
			firstFlag = node;
			node = node.next;
			nodeValue = node.seqValue;
			int firstFlagValue = head.seqValue;
			int secondFlagValue = 0;
			if (firstFlagValue == 27) {
				secondFlagValue = 28;
			}else if (firstFlagValue == 28) {
				secondFlagValue = 27;
			}
			while (nodeValue != secondFlagValue) {
				node = node.next;
				nodeValue = node.seqValue;
			}
			SeqNode swapNode = node;
			SeqNode temp;
			SeqNode lastSwap = seqRear;
			int nextNode = node.next.seqValue;
			node = node.next;
			nodeValue = node.seqValue;
			
			while (swapNode.next != seqRear) {
				temp = swapNode.next;
				swapNode.next = node.next;
				node.next = head;
				lastSwap.next = temp;
				lastSwap = node;
				node = swapNode.next;
				nodeValue = node.seqValue;
			}
			seqRear = swapNode;
			temp = seqRear.next;
			seqRear.next = seqRear.next.next;
			lastSwap.next = temp;
			node.next = firstFlag;
			printList(seqRear);
		}
		else if (seqRear.seqValue == 27 || seqRear.seqValue == 28){//if nothing after second flag
			SeqNode oldHead = seqRear.next;
			head = head.next;
			nodeValue = head.seqValue;
			seqRear.next = oldHead;
			seqRear = oldHead;
			printList(seqRear);
		}
		else {
			while (nodeValue != 27 && nodeValue != 28) {
				SeqNode oldHead = seqRear.next;
				head = head.next;
				nodeValue = head.seqValue;
				seqRear.next = oldHead;
				seqRear = oldHead;
			}
			int firstFlagValue = nodeValue;
			int secondFlagValue = 0;
			if (firstFlagValue == 27) {
				secondFlagValue = 28;
			}else if (firstFlagValue == 28) {
				secondFlagValue = 27;
			}
			head = seqRear.next;
			node = node.next;
			int nextNodeValue = node.next.seqValue;
			lastNode = seqRear;
			while (node.seqValue != secondFlagValue) {
				node = node.next;
				nodeValue = node.seqValue;
			}
			SeqNode temp;
			SeqNode swapNode = node;
			SeqNode lastSwap = seqRear;
			node = node.next;
			SeqNode prevNode = seqRear;
			nodeValue = node.seqValue;

			while (nodeValue != firstNodeValue) {
				temp = swapNode.next;
				swapNode.next = node.next;
				node.next = head;
				lastSwap.next = temp;
				lastSwap = node;
				node = swapNode.next;
				nodeValue = node.seqValue;
			}
			printList(seqRear);
		}
		System.out.println("T");
		printList(seqRear);
	}
	
	/**
	 * Implements Step 4 - Count Shift - on the sequence.
	 */
	void countShift() {		
	    // COMPLETE THIS METHOD
		SeqNode tail = seqRear;
		SeqNode head = seqRear.next;
		int tailValue = tail.seqValue;
		if (tailValue == 28) {
			tailValue = 27;
		}
		SeqNode currNode = head;
		SeqNode prevNode = tail;
		int count = 0;
		while (currNode.seqValue != tailValue) {
			currNode = currNode.next;
			currNode.seqValue = currNode.seqValue;
			prevNode = prevNode.next;
		}
		SeqNode secondLast = prevNode;
		while (count != tailValue) {
			if (tailValue == 27) {
				break;
			}
			SeqNode node = head;
			SeqNode temp = seqRear.next;
			seqRear.next = node.next;
			node.next = seqRear;
			secondLast.next = temp;
			secondLast = node;
			head = node.next.next;
			node = node.next;
			count++;
		}
		System.out.println("C");
		printList(seqRear);
	}
	
	/**
	 * Gets a key. Calls the four steps - Flag A, Flag B, Triple Shift, Count Shift, then
	 * counts down based on the value of the first number and extracts the next number 
	 * as key. But if that value is 27 or 28, repeats the whole process (Flag A through Count Shift)
	 * on the latest (current) sequence, until a value less than or equal to 26 is found, 
	 * which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
	    // COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		
		SeqNode head = seqRear.next;
		int headValue = head.seqValue;
		SeqNode node = head;
		if (headValue == 28) { headValue = 27;}
		int count = 0;
		int nodeValue = -1;
		do {
			flagA();
			flagB();
			tripleShift();
			countShift();
			head = seqRear.next;
			node = head;
			headValue = head.seqValue;
			count = 1;
			while (count != headValue) {
				node = node.next;
				count++;
			}
			if (count == headValue) {
				nodeValue = node.next.seqValue;
			}
		}while (nodeValue == 27 || nodeValue == 28);
		int key = node.next.seqValue;
	    return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(SeqNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.seqValue);
		SeqNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.seqValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
	    // COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		message = message.replaceAll("[^a-zA-Z0-9]", "");
		message = message.toUpperCase();
		int messageLength = message.length();
		char charac;
		int charVal;
		String encrypted = "";
		SeqNode node = seqRear.next;
		
		for (int i = 0 ; i <= messageLength-1 ; i++) {
			char newChar;
			charac = message.charAt(i);
			int key = getKey();
			charVal = charac - 'A'+1;
			charVal = charVal + key;
			if (charVal > 26) {
				charVal = charVal - 26;
			}
			newChar = (char)(charVal -1 +'A');
			//newChar = Character.toUpperCase(newChar);
			encrypted = encrypted + newChar;
			
			node = node.next;
		}
	    return encrypted;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
	    // COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		message = message.replaceAll("[^a-zA-Z0-9]", "");
		message = message.toUpperCase();
		int messageLength = message.length();
		char charac;
		int charVal;
		String decrypted = "";
		SeqNode node = seqRear.next;
		
		for (int i = 0 ; i <= messageLength-1 ; i++) {
			char newChar;
			charac = message.charAt(i);
			int key = getKey();
			charVal = charac - 'A'+1;
			if (charVal <= key) {
				charVal = charVal+26;
			}
			charVal = charVal - key;
			newChar = (char)(charVal -1 +'A');
			//newChar = Character.toUpperCase(newChar);
			decrypted = decrypted + newChar;
			
			node = node.next;
		}
	    return decrypted;
	}
}
