package com.github.fisherhe12.gof.template;

/**
 * com.github.fisherhe12.gof.template
 *
 * @author Fisher.He
 */
public class DaoClass extends BaseDaoTemplate {

    public void query() {
        execute(new StatementCallback<String>() {
            @Override
            public String doInStatement() {
                return null;
            }
        });
    }

    @Override
    protected void run() {
        // run business
    }
}
