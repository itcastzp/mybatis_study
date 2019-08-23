package org.apache.ibatis.scripting.xmltags;


public class B {
    private String b = "1";

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public static void main(String[] args) {
        B b = new B();
        A a = new A();
        System.out.println(b.equals(a));
        System.out.println(b.getB().equals(a.getA()));
        System.out.println( a.getA().intern()==b.getB());

        String hellos = new String("hello");

        String hellos1 = new String("hello");

        System.out.println(hellos1 .intern()== hellos.intern());
        String hello = "hello";
        String hel = "hel";
        String lo = "lo";
        System.out.println(hello == "hel" + "lo" + "");
        System.out.println(hello == (hel + lo).intern());

        System.out.println(hellos.intern() == hello);

        System.out.println(hello == ("hel"+ lo).intern());
    }
}
