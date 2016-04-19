
package com.websystique.spring.model;


public class Bar {

    private Long foo;
    private double bar;

    public Long getFoo() {
        return foo;
    }

    public void setFoo(Long inputFoo) {
        this.foo = inputFoo;
    }

    public double getBar() {
        return bar;
    }

    public void setBar(double inputBar) {
        this.bar = inputBar;
    }

    public enum REPORT_COLUMNS {

        FOO_BAR("fooBar", true);
        private final String column;
        private final boolean filterable;

        private REPORT_COLUMNS(String column, boolean filterable) {
            this.column = column;
            this.filterable = filterable;
        }

        public String getColumn() {
            return column;
        }

        public boolean isFilterable() {
            return filterable;
        }

    }

}
