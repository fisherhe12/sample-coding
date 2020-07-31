package com.github.fisherhe12.gof.snapshot;

/**
 * com.github.fisherhe12.gof.snapshot
 *
 * @author Fisher.He
 */
public class InputText {

    private final StringBuilder text = new StringBuilder();

    public String getText() {
        return text.toString();
    }

    public void append(String input) {
        text.append(input);
    }

    public Snapshot createSnapshot() {
        return new Snapshot(text.toString());
    }

    public void restoreSnapshot(Snapshot snapshot) {
        this.text.replace(0, this.text.length(), snapshot.getText());
    }
}
