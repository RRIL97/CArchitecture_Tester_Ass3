package FileHandler;

import java.io.*;

public class StreamReader implements Runnable {

    private final   InputStream is;
    private         String  lastOutput = "";

    private boolean continueRead  = true;
    private boolean readyToWrite  = false;

    private boolean quitActivated = false;

    public StreamReader(InputStream is) {
        this.is = is;
    }
    public void    kill()            {  continueRead = false; }

    public String  getLastResponse() {  return lastOutput;    }
    public void    clearLastResponse(){
        lastOutput = "";
    }
    public boolean isReadyToWrite(){
        return readyToWrite;
    }

    public void setQuit(){
        quitActivated = true;
    }
    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br     = new BufferedReader(isr);
            int currentCharRead    = 0;
            String numberArgsError   = "Error: Insufficient Number of Arguments on Stack";
            String operandStackError = "Error: Operand Stack Overflow";
            String calcIdentifier    = "calc: ";

            while(continueRead) {
                while ((currentCharRead = br.read()) != -1 ) {
                    lastOutput += (char) currentCharRead;
                     if(lastOutput.contains(calcIdentifier))
                        break;

                    if(lastOutput.contains(numberArgsError) || lastOutput.contains(operandStackError)) {
                        br.read(); /* Read the /n */
                        break;
                    }

                }
                readyToWrite = true;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
