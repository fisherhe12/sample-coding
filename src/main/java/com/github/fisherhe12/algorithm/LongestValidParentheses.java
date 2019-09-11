package com.github.fisherhe12.algorithm;

import java.util.HashMap;
import java.util.Stack;

/**
 * 统计一个字符串中小括号规则合法的次数(LeeCode-32)
 * <p>Given a string containing just the characters '(' and ')', find
 * the length of the longest valid (well-formed) parentheses substring.
 * </p>
 *
 * @author fisher
 */
public class LongestValidParentheses {

    private final HashMap<Character, Character> characterMap;

    public LongestValidParentheses() {
        characterMap = new HashMap<>();
        characterMap.put(')', '(');
    }

    //fixme
    public int longestValidParentheses(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int count = 0;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (characterMap.containsKey(c)) {
                char topElement = stack.empty() ? '#' : stack.pop();
                if (topElement == characterMap.get(c)) {
                    count = count + 2;
                }
            } else {
                stack.push(c);
            }
        }
        return count;
    }

}
