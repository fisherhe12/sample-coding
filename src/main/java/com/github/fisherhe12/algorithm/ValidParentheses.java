package com.github.fisherhe12.algorithm;

import java.util.HashMap;
import java.util.Stack;

/**
 * String判断大中小括号是否合法(LeeCode-20)
 * <p>Given a string containing just the characters '(', ')', '{', '}',
 * '[' and ']', determine if the input string is valid.</p>
 *
 * @author Yu.He
 */
public class ValidParentheses {

    private final HashMap<Character, Character> characterMap;

    public ValidParentheses() {
        characterMap = new HashMap<>();
        characterMap.put(')', '(');
        characterMap.put(']', '[');
        characterMap.put('}', '{');
    }

    public boolean isValid(String s) {

        if (s == null || s.isEmpty()) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (characterMap.containsKey(c)) {

                char topElement = stack.empty() ? '#' : stack.pop();

                if (topElement != characterMap.get(c)) {
                    return false;
                }

            } else {
                stack.push(c);
            }

        }

        return stack.isEmpty();
    }
}
