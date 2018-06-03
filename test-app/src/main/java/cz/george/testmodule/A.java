package cz.george.testmodule;

public class A extends B {
    public String getA() {
        return a;
    }

    public A setA(String a) {
        this.a = a;
        return this;
    }

    private String a;

    public Integer getB() {
        return b;
    }

    public A setB(Integer b) {
        this.b = b;
        return this;
    }

    private Integer b;
    private int c;


    public void methodForA() {
        A a = null;
        a.methodForA();

        O o = null;
        o.methodO();
    }
}
