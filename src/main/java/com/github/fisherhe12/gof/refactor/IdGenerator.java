package com.github.fisherhe12.gof.refactor;

import com.google.common.annotations.VisibleForTesting;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.github.fisherhe12.gof
 *
 * @author Fisher.He
 */

public class IdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    public String generate() {
        String hostName = getLastfieldOfHostName();
        String randomString = generateRandomAlphameric(8);
        return String.format("%s-%d-%s", hostName, System.currentTimeMillis(), randomString);
    }


    private String getLastfieldOfHostName() {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            return getLastSubstrSplittedByDot(hostName);
        } catch (UnknownHostException e) {
            logger.warn("Failed to get the host name.", e);
            throw new RuntimeException("Failed to get the host name", e);
        }
    }

    @VisibleForTesting
    protected String getLastSubstrSplittedByDot(String hostName) {
        String substrOfHostName;
        String[] tokens = hostName.split("\\.");
        substrOfHostName = tokens[tokens.length - 1];
        return substrOfHostName;
    }

    @VisibleForTesting
    protected String generateRandomAlphameric(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++count;
            }
        }
        return new String(randomChars);

    }

}
