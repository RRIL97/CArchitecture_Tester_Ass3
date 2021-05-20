package Test;


import FileHandler.CommandProcessor;

public class SanityChecks  {

    /*
    Checks if the size of stack param is passed correctly
    ./calc x => Where is x is in octal
     */
    public static boolean checkStackSizeParamOctal(){
        CommandProcessor stackSizeProcessor = new CommandProcessor();;
        int maxSize = 15;
        int minSize = 1;
        try {

            int randomStackSize = (int) (Math.random() * (maxSize - minSize) + minSize);
            stackSizeProcessor.initializeWithArgs(new String[]{Integer.toString(randomStackSize)});

            String last = "";
            int paddedNumber = (int) (Math.random() * (100000+500)+500);

            for (int i = 0; i <= randomStackSize; i++)
                stackSizeProcessor.sendCommand(Integer.toOctalString(paddedNumber+i));

            last = Integer.toOctalString(paddedNumber+randomStackSize);
            String poppedValue = stackSizeProcessor.sendCommand("p");

            if(!last.equals(poppedValue))
                return true;
        }finally {
            stackSizeProcessor.kill();
        }
        return false;
    }

}
