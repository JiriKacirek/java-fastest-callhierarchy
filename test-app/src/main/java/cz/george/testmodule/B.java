package cz.george.testmodule;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

/**
 * Created by John on 4/9/2017.
 */
public class B {
    public void methodForA() {
        A a = new A().setA("");
        a.getA();
        String s = a.getA();

        XMLDecoder de;

    }
}
