
public class BadWindowRedirect {

    public static void main(String args[]) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java Jecho 'Hola Mundo' > test.txt");
            // any error message?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // any output?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
            System.out.println("Valor de salida: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
