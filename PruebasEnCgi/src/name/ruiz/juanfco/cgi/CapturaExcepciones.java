/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.cgi;

/**
 *
 * @author hamfree
 */
public class CapturaExcepciones {
    public static void main(String[] args) {
        CapturaExcepciones ce = new CapturaExcepciones();
        ce.ejecuta();
        System.out.println("Despues de llamar a ejecuta()");
    }

    public void ejecuta() {
        try {
            Class clazz = Class.forName("X");
            if (clazz != null) {
                System.out.println(clazz.getDeclaredFields().toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
