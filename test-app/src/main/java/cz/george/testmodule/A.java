package cz.george.testmodule;

/**
 * Created by John on 4/9/2017.
 */
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
