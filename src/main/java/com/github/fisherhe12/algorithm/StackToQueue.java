package com.github.fisherhe12.algorithm;

import java.util.Stack;

/**
 * 用堆栈实现队列 stack->queue
 *
 * @author Yu.He
 */
public class StackToQueue {

    private Stack<Integer> input;

    private Stack<Integer> output;

    /**
     * Initialize your data structure here.
     */
    public StackToQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        input.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.peek();
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return output.isEmpty() && input.isEmpty();
    }
}
