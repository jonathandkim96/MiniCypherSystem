# MiniCipherSystem
A cipher system that can be used to encrypt messages.
The system uses integers 1 to 26 as keys, plus two flag numbers which are 27 and 28. These keys and flag numbers are stored in a circular linked list.
The algorithm starts with a sequence in some random order. It uses this sequence to generate what is called a keystream, which is a sequence of numbers called keys. Each key will be a number between 1 and 26

Starting with a sequence, a keystream is generated, one key for every alphabetic character in the message to be encrypted.
Encryption is then done by simply adding each key of the keystream to the corresponding alphabetic position, and if this sum is greater than 26, subtracting 26 from the sum.
The numbers are converted back to letters.

Decryption follows a similar process.
When the decrypter gets the coded message, it generates the keystream in exactly the same way, using the same initial sequence as the encryption. Then, the keystream is subtracted from the alphabetic position values of the letters in the coded message. If a code value is equal or smaller than the corresponding decryption key, 26 is first added to it and then the key is subtracted.

This is the algorithm to generate each key of the keystream, starting with the initial sequence.

**Get Key**

Executes the following four steps:

Step 1 (Flag A): 
Find Flag "A" (27) and move it ONE position down by swapping it with the number below (after) it.
This results in the following, after swapping 27 with 7 in the starting sequence:
  INITIAL SEQUENCE:      13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 27 7 14 5 4 28 11 16 6
                                                                             ^^^^
  SEQUENCE AFTER STEP 1: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7 27 14 5 4 28 11 16 6
                                                                             ^^^^
If the flag happens to be the last one in the sequence, then loop around and swap it with the first. For example:
  5 ... 27
Here 5 is the first number and 27 is the last number. Swapping them will give:
  27 ... 5
  
Step 2 (Flag B): 
Find Flag "B" (28) and move it TWO numbers down by swapping it with the numbers below (after) it.
This results in the following, after moving 28 two numbers down in the sequence that resulted after step 1:
  SEQUENCE AFTER STEP 1: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7 27 14 5 4 28 11 16 6
                                                                                         ^^^^^^^^
  SEQUENCE AFTER STEP 2: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7 27 14 5 4 11 16 28 6
                                                                                         ^^^^^^^^
If the flag happens to be the last (or second to last) number in the sequence, then loop around and swap it with the number(s) in the front. For example:
   5 6 ... 10 28
Here 28 is the last one. Moving it one position down gives:
   28 6 ... 10 5
and moving it one more position down gives:
   6 28 ... 10 5
   
Step 3 (Triple Shift): 
Swap all the numbers before the first (closest to the top/front) flag with the numbers after the second flag:
  SEQUENCE AFTER STEP 2: 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7|27 14 5 4 11 16 28|6
                         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^                    ^
  SEQUENCE AFTER STEP 3: 6|27 14 5 4 11 16 28|13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7
                         ^                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
If there are no numbers before the first flag, then the second flag will become the last one in the modified sequence. Similarly, if there are no numbers after the second flag, then the first flag will become the first number in the modified sequence.

Step 4 (Count Shift): 
Look at the value of the last number in the sequence. Count down that many numbers from the first number, and move those numbers to just before the last number:
  SEQUENCE AFTER STEP 3: 6 27 14 5 4 11 16 28 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 7

  SEQUENCE AFTER STEP 4: 28 13 10 19 25 8 12 20 18 26 1 9 22 15 3 17 24 2 21 23 6 27 14 5 4 11 16 7
                                                                                ^^^^^^^^^^^^^^^^^
If the last number happens to be Flag B (28), use 27 (instead of 28) as its value for this step.
