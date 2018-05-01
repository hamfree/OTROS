/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.excepciones;

/**
 *
 * @author hamfree
 */
public class ConfiguracionException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConfiguracionException() {
    }

    public ConfiguracionException(String message) {
        super(message);
    }

    public ConfiguracionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfiguracionException(Throwable cause) {
        super(cause);
    }

    public ConfiguracionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
