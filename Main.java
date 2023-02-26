package chucknorris;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        operationLoop: while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();
            switch (operation.toLowerCase()) {
                case "encode" -> encodeMsg();
                case "decode" -> decodeMsg();
                case "exit" -> { break operationLoop; }
                default -> System.out.println("There is no '" + operation + "' operation\n");
            }
        }
        System.out.println("Bye!");
    }

    private static void encodeMsg() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string:");
        String msg = scanner.nextLine();
        System.out.println("Encoded string:");
        System.out.println(encode(msg) + "\n");
    }

    private static void decodeMsg() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input encoded string:");
        String code = scanner.nextLine();
        try {
            String decoded = decode(code);
            System.out.println("Decoded string:\n" + decoded + "\n");
        } catch (Exception e) {
            System.out.println("Encoded string is not valid." + "\n");
        }
    }

    private static String encode(String msg) {
        StringBuilder encoded = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        for (char c : msg.toCharArray()) {
            tmp.append(String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        char[] bin = tmp.toString().toCharArray();
        while (bin.length > 0) {
            switch (bin[0]) {
                case '0' -> {
                    encoded.append("00 ");
                    int i = 0;
                    for (; i < bin.length && bin[i] == '0'; i++) {
                        encoded.append("0");
                    }
                    encoded.append(" ");
                    bin = Arrays.copyOfRange(bin, i, bin.length);
                }
                case '1' -> {
                    encoded.append("0 ");
                    int i = 0;
                    for (; i < bin.length && bin[i] == '1'; i++) {
                        encoded.append("0");
                    }
                    encoded.append(" ");
                    bin = Arrays.copyOfRange(bin, i, bin.length);
                }
            }
        }
        return encoded.toString().trim();
    }

    private static String decode(String code) throws Exception {
        if (!Pattern.matches("^(0+ )+0+$", code)) throw new Exception("Characters other than 0 and spaces found");
        String[] codeArray = code.split(" ");
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < codeArray.length; i++) {
            switch (codeArray[i].length()) {
                case 1 -> binary.append("1".repeat(codeArray[++i].length()));
                case 2 -> binary.append("0".repeat(codeArray[++i].length()));
                default -> throw new Exception("Block does not start with 0 or 00");
            }
        }
        String bin = binary.toString();
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < bin.length(); i += 7) {
            decoded.append((char) Integer.parseInt(bin.substring(i, i + 7), 2));
        }
        return decoded.toString();
    }
}