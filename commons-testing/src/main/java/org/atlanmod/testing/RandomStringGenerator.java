package org.atlanmod.testing;
import java.util.Random;

public class RandomStringGenerator implements Generator<String> {

    @Override
    /**
     *Generate a string.
     */
 public String generate() {
        Random random = new Random();
        int length= random.nextInt(10)+1;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(10) + 48;
            int randomUpperCaseAlphabet = random.nextInt(26) + 65;
            int randomLowerCaseAlphabet = random.nextInt(26) + 97;
            int[] possibleValues = {randomInt, randomLowerCaseAlphabet, randomUpperCaseAlphabet};
            int choice = random.nextInt(3);
            char generatedChar = (char)possibleValues[choice];
            sb.append(generatedChar);
        }
        return sb.toString();
    }

    @Override
    /**
     *return an array of class which contains the string class.
     */
    public Class<String>[] types() {
        Class[] types={String.class};
        return types;
    }
}
