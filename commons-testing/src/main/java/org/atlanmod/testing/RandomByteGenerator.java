package org.atlanmod.testing;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class RandomByteGenerator implements Generator<Byte> {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Random random = new Random();
        int length= random.nextInt(10)+2;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(10) + 48;
            //int randomUpperCaseAlphabet = random.nextInt(26) + 65;
            //int randomLowerCaseAlphabet = random.nextInt(26) + 97;
            int[] possibleValues = { randomInt/*randomLowerCaseAlphabet, randomUpperCaseAlphabet*/};
            int choice = random.nextInt(1);
            char generatedChar = (char)possibleValues[choice];
            sb.append(generatedChar);
        }
        String str = sb.toString();
        byte[] bytes;

        bytes = str.getBytes("UTF-16");
        System.out.println(bytes[length]);
    }

    @Override
    public Byte generate() {
        Random random = new Random();
        int length= random.nextInt(10)+2;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(10) + 48;
            //int randomUpperCaseAlphabet = random.nextInt(26) + 65;
            //int randomLowerCaseAlphabet = random.nextInt(26) + 97;
            int[] possibleValues = { randomInt/*randomLowerCaseAlphabet, randomUpperCaseAlphabet*/};
            int choice = random.nextInt(1);
            char generatedChar = (char)possibleValues[choice];
            sb.append(generatedChar);
        }
        String str = sb.toString();
        byte[] bytes= new byte[str.length()];
        try {
            bytes = str.getBytes("UTF-16");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       return bytes[length];
    }


    @Override
    public Class<Byte>[] types() {
        Class[] types={Byte.class,byte.class};
        return types;
    }
}
