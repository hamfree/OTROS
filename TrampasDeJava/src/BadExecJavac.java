
public class BadExecJavac {

    public static void main(String args[]) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("javac");
            int exitVal = proc.exitValue();
            System.out.println("Valor de salida de Process: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
