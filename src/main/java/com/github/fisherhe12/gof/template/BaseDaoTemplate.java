package com.github.fisherhe12.gof.template;

/**
 * com.github.fisherhe12.gof.template
 *
 * @author Fisher.He
 */
public abstract class BaseDaoTemplate {

    protected <T> T execute(StatementCallback<T> action) {
        setUp();
        run();
        return action.doInStatement();
    }

    private void setUp() {
    }

    /**
     * Subclass implement run method
     */
    protected abstract void run();


}
