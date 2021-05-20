package Test;

import FileHandler.CommandProcessor;

import java.util.ArrayList;

public class FunctionalTests implements Runnable {

    private final CommandProcessor functionalTester = new CommandProcessor();

    public boolean addTest(){
        for(int i = 0; i < 250 ; i++) {
            TupleHelper nGener = new TupleHelper(i * 10, i * 10 * i);

            functionalTester.sendCommand(nGener.getFirstNumStr());
            functionalTester.sendCommand(nGener.getSecondNumStr());
            functionalTester.sendCommand("+");
            String answerAdd = functionalTester.sendCommand("p");
            if (answerAdd.trim().length() > 0) {
                if (!answerAdd.equals(nGener.getPlusAnswer())) {
                    System.out.println("Invalid Add Test | Answer Of Program : " + answerAdd + " | Should Be : " + nGener.getPlusAnswer() + " | First Num : " + nGener.getFirstNumStr() + " | Second Num : " + nGener.getSecondNumStr());
                    return false;
                } else
                    System.out.println("Correct Add Test | Answer Of Program : " + answerAdd + " | Should Be : " + nGener.getPlusAnswer() + " | First Num : " + nGener.getFirstNumStr() + " | Second Num : " + nGener.getSecondNumStr());

            }
        }
        return true;
    }
    public boolean andTest(){
        for(int i = 0; i < 250 ; i++) {
            TupleHelper nGener = new TupleHelper(i * 10, i * 10 * i);

            functionalTester.sendCommand(nGener.getFirstNumStr());
            functionalTester.sendCommand(nGener.getSecondNumStr());
            functionalTester.sendCommand("&");
            String answerAnd = functionalTester.sendCommand("p");
            if (answerAnd.trim().length() > 0) {
                if (!answerAnd.equals(nGener.getAndAnswer())) {
                    System.out.println("Invalid And Test | Answer Of Program : " + answerAnd + " | Should Be : " + nGener.getAndAnswer() + " | First Num : " + nGener.getFirstNumStr() + " | Second Num : " + nGener.getSecondNumStr());
                    return false;
                } else
                    System.out.println("Correct And Test | Answer Of Program : " + answerAnd + " | Should Be : " + nGener.getAndAnswer() + " | First Num : " + nGener.getFirstNumStr() + " | Second Num : " + nGener.getSecondNumStr());

            }
        }
        return true;
    }

    /*
    Checks the n Operation
    */
    public boolean nOperationTest(){

        for(int i = 0; i < 250 ; i++) {
            TupleHelper nGener = new TupleHelper(i*10, i*10*i);

            functionalTester.sendCommand(nGener.getFirstNumStr());
            functionalTester.sendCommand(nGener.getSecondNumStr());
            functionalTester.sendCommand("n");
            String answerN = functionalTester.sendCommand("p");
            if(answerN.trim().length() > 0) {
                functionalTester.sendCommand("p");
                if (!answerN.equals(Integer.toString((int) nGener.getNAnswer(2)))) {
                    System.out.println("Invalid nOperation Test | Answer Of Program : " + answerN + " | Should Be : " + (int) nGener.getNAnswer(2) + " | Num : " + nGener.getSecondNumStr());
                    return false;
                } else
                    System.out.println("Correct nOperation Test | Answer Of Program : " + answerN + " | Should Be : " + (int) nGener.getNAnswer(2) + " | Num : " + nGener.getSecondNumStr());

            }
            }
        return true;
    }

    public boolean popTest() {
        functionalTester.kill();
        functionalTester.initializeWithArgs(null); /* Defaulted to 5 stack */

        ArrayList<Integer> generatedValues = new ArrayList<Integer>();
        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 5; j++) {
                int current = Integer.parseInt(Integer.toOctalString((int) (Math.random() * (100000+1)+1)));
                generatedValues.add(current);
                functionalTester.sendCommand(Integer.toString(current));
            }
            for (int j = 0; j < 5; j++) {
                String answerPop = functionalTester.sendCommand("p");
                if(answerPop.length() > 0){
                if (!answerPop.equals(Integer.toString(generatedValues.get(4-j)))) {
                    System.out.println("Invalid Pop Test | Answer Of Program : " + answerPop + " | Should Be : " + generatedValues.get(4-j));
                    return false;
                } else
                    System.out.println("Correct Pop Test | Answer Of Program : " + answerPop + " | Should Be : " + generatedValues.get(4-j));
                }
            }
            generatedValues.clear();
        }
        return true;
    }
    @Override
    public void run() {
        boolean sanityCheck = false;
        boolean nOperation  = false;
        boolean addOperation = false;
        boolean andOperation = false;
        boolean popOperation = false;

        sanityCheck = SanityChecks.checkStackSizeParamOctal();
        System.out.println("SanityCheck.java | Stack Size Param Test | Success : "+ sanityCheck);

        functionalTester.initializeWithArgs(null); /* Defaulted to 5 stack */

        System.out.println("Starting nOperation Test....\n-----------------------");
        nOperation = nOperationTest();
        System.out.println("nOperation Test | Success : " + nOperation);
        System.out.println("\nStarting And Test....\n-----------------------");
        andOperation = andTest();
        System.out.println("And Test | Success : " + andOperation);
        System.out.println("\nStarting Add Test....\n-----------------------");
        addOperation = addTest();
        System.out.println("Add Test | Success : " + addOperation);
        System.out.println("\nStarting PopTest....\n-----------------------");
        popOperation = popTest();
        System.out.println("Pop Test | Success : " + popOperation);
        functionalTester.kill();

        if(nOperation && andOperation && addOperation && popOperation && sanityCheck)
            System.out.println("Seems like everything is alright..\n");
        else
            System.out.println("Something is wrong..");
    }
}
