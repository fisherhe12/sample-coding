package com.github.fisherhe12.gof.snapshot;

import java.util.Stack;

/**
 * com.github.fisherhe12.gof.snapshot
 *
 * @author Fisher.He
 */
public class SnapshotHolder {

    private final Stack<Snapshot> snapshots = new Stack<>();

    public Snapshot popSnapshot() {
        return snapshots.pop();
    }

    public void pushSnapshot(Snapshot snapshot) {
        snapshots.push(snapshot);
    }

    public static void main(String[] args) {
        InputText inputText = new InputText();
        SnapshotHolder snapshotHolder = new SnapshotHolder();
        snapshotHolder.pushSnapshot(inputText.createSnapshot());
        inputText.append("fisher");
        System.out.println(inputText.getText());
        snapshotHolder.pushSnapshot(inputText.createSnapshot());
        inputText.append("he");

        Snapshot snapshot = snapshotHolder.popSnapshot();
        System.out.println(snapshot.getText());
        inputText.restoreSnapshot(snapshot);
        System.out.println(inputText.getText());

    }
}


