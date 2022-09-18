import java.util.*;

class Main {
    public char offset;
    public int offsetIndex;
    public Hashtable<Integer, Character> baseEncodingTable = encodingTable(0);

    public static void main(String[] args) {
        Main mainClass = new Main();
        mainClass.setOffsetChar('+');
        System.out.println(mainClass.encode("HELLO WORLD"));
        System.out.println(mainClass.decode("*RU1KR4 JG4 U0Z"));
    }

    public void setOffsetChar(char offsetChar) {
        offset = offsetChar;
        for (Map.Entry<Integer, Character> each : baseEncodingTable.entrySet()) {
            if (each.getValue() == offsetChar) {
                offsetIndex = each.getKey();
            }
        }
    }

    public Hashtable<Integer, Character> encodingTable(int offsetIndex) {
        Hashtable<Integer, Character> encodingTable = new Hashtable();
        int i = offsetIndex;
        char start = 'A';
        char number = '0';
        while (start <= 'Z') {
            if (i == 44) {
                i = 0;
            }
            encodingTable.put(i, start);
            start++;
            i++;
        }
        while (number <= '9') {
            if (i == 44) {
                i = 0;
            }
            encodingTable.put(i, number);
            number++;
            i++;
        }
        char[] specialChar = new char[] { '(', ')', '*', '+', ',', '-', '.', '/' };
        for (char character : specialChar) {
            if (i == 44) {
                i = 0;
            }
            encodingTable.put(i, character);
            i++;
        }
        return encodingTable;
    }

    public String encode(String plaintext) {
        int[] encodedArray = new int[plaintext.length()];
        for (Map.Entry<Integer, Character> each : baseEncodingTable.entrySet()) {
            for (int i = 0; i < plaintext.length(); i++) {
                if (each.getValue() == plaintext.charAt(i)) {
                    encodedArray[i] = each.getKey();
                } else if (plaintext.charAt(i) == ' ') {
                    encodedArray[i] = -1;
                }
            }
        }
        System.out.println(Arrays.toString(encodedArray));
        Hashtable<Integer, Character> newEncodingTable = encodingTable(offsetIndex);
        String newString = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if (newEncodingTable.containsKey(encodedArray[i])) {
                newString += newEncodingTable.get(encodedArray[i]);
            } else if (encodedArray[i] == -1) {
                newString += " ";
            }
        }
        return (offset + newString);
    }

    public String decode(String encodedText) {
        char offset = encodedText.charAt(0);
        setOffsetChar(offset);
        int[] encodedArray = new int[encodedText.length()];
        String decoded = "";
        Hashtable<Integer, Character> newEncodingTable = encodingTable(offsetIndex);
        for (Map.Entry<Integer, Character> each : newEncodingTable.entrySet()) {
            for (int i = 1; i < encodedText.length(); i++) {
                if (each.getValue() == encodedText.charAt(i)) {
                    encodedArray[i] = each.getKey();
                } else if (encodedText.charAt(i) == ' ') {
                    encodedArray[i] = -1;
                }
            }
        }
        ;
        for (int i = 0; i < encodedText.length(); i++) {
            if (baseEncodingTable.containsKey(encodedArray[i])) {
                decoded += baseEncodingTable.get(encodedArray[i]);
            } else if (encodedArray[i] == -1) {
                decoded += " ";
            }
        }
        decoded = decoded.substring(1, decoded.length());

        return decoded;
    }

}