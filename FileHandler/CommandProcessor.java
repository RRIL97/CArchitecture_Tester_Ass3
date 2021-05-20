package FileHandler;

import java.io.PrintWriter;
import java.util.Arrays;

public class CommandProcessor {

    private PrintWriter  consoleWriter;
    private StreamReader consoleReader;
    private Process      consoleProcess;

    private  String binName = "./calc";

    public void kill() {
        consoleProcess.destroy();
        consoleReader.kill();
    }

    public void initializeWithArgs(String  [] args) {
        try {
            StringBuilder execution = new StringBuilder(binName + " ");
            if(args != null) {
                for (String arg : args) execution.append(arg).append(" ");
            }
            consoleProcess =Runtime.getRuntime().exec(new String[] {
                    "/bin/sh",
                    "-c",
                    execution.toString()
            });
            consoleReader = new StreamReader(
                    consoleProcess.getInputStream());
            new Thread(consoleReader).start();
            consoleWriter = new PrintWriter(consoleProcess.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        String response = "";

        try {
            do{
                Thread.sleep(10);
            }while(!consoleReader.isReadyToWrite());

            consoleWriter.write(command);
            consoleWriter.write("\n");
            consoleWriter.flush();

            Thread.sleep(2);
            response = consoleReader.getLastResponse();
            consoleReader.clearLastResponse();
        }catch(Exception e){
            e.printStackTrace();
        }
        return response.replace("calc: ","").trim();
    }
}
