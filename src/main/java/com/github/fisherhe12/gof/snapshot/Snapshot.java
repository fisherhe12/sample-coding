package com.github.fisherhe12.gof.snapshot;

/**
 * com.github.fisherhe12.gof.snapshot
 *
 * @author Fisher.He
 */
public class Snapshot {

    private String inputText;

    public Snapshot(String inputText) {
        this.inputText = inputText;
    }

    public String getText() {
        return inputText;
    }
}
