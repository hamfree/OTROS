
public class GoodWindowsExec {

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("USO: java GoodWindowsExec <cmd>");
            System.exit(1);
        }

        try {
            String osName = System.getProperty("os.name");
            String[] cmd = new String[3];
            if (osName.equals("Windows NT") || osName.equals("Windows 10")) {
                cmd[0] = "cmd.exe";
                cmd[1] = "/C";
                cmd[2] = args[0];
            } else if (osName.equals("Windows 95")) {
                cmd[0] = "command.com";
                cmd[1] = "/C";
                cmd[2] = args[0];
            }

            Runtime rt = Runtime.getRuntime();
            System.out.println("Ejecutando " + cmd[0] + " " + cmd[1]
                    + " " + cmd[2]);
            Process proc = rt.exec(cmd);

            // ¿algún mensaje de error?
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");

            // ¿alguna salida?
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

            // Inicia los hilos
            errorGobbler.start();
            outputGobbler.start();

            // ¿algún error?
            int exitVal = proc.waitFor();
            System.out.println("Valor de salida: " + exitVal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
