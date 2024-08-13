package org.opencart.Zones;

public class LengthOfLastWord {
    public static void main(String[] args) {
        String str = "   hello good morning   ";
        
        // Step 1: Trim the string to remove leading and trailing spaces
        str = str.trim();
        
        // Step 2: Split the string into words
        String[] words = str.split(" ");
        
        // Step 3: Get the last word
        String lastWord = words[words.length - 1];
        
        // Step 4: Fetch the length of the last word
        int lengthOfLastWord = lastWord.length();
        
        // Print the length
        System.out.println("Length of the last word: " +str+lengthOfLastWord);
    }
}
