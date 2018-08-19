
import java.io.*;

public class GoodWinRedirect {

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("USO java GoodWinRedirect <outputfile>");
            System.exit(1);
        }

        try {
            FileOutputStream fos = new FileOutputStream(args[0]);
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java Jecho 'Hola Mundo'");

            // ¿algún mensaje de error?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // ¿alguna salida?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT", fos);

            // Los lanzamos
            errorGobbler.start();
            outputGobbler.start();

            // ¿algún error?
            int exitVal = proc.waitFor();
            System.out.println("Valor de salida: " + exitVal);
            fos.flush();
            fos.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
