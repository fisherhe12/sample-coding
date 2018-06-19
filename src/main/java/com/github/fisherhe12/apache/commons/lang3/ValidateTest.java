package com.github.fisherhe12.apache.commons.lang3;

import org.apache.commons.lang3.Validate;
import org.junit.Test;

/**
 * Validate Test Case
 *
 * @author fisher
 * @date 2018-01-25
 */
public class ValidateTest {

    @Test
    public void validate() {
        Validate.notEmpty(" ");
        Validate.validIndex(new String[] {"123", "234", "345"}, 2);
    }
}
