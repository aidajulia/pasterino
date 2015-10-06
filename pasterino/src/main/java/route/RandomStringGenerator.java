package route;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomStringGenerator {
    private static final String ALPHABET = "abcdefghkmnpqtuvxyzABCDEFGHKMNPQTUVXYZ12346789";
    private static final int ALPHABET_LENGTH = ALPHABET.length();
    private static final Random RANDOM = new Random();

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, length).forEach((i) -> {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET_LENGTH)));
        });

        return sb.toString();
    }
}
