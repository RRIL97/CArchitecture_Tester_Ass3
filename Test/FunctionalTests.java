package Test;

import FileHandler.CommandProcessor;

import java.util.ArrayList;
import java.util.Stack;

public class FunctionalTests implements Runnable {

    private final CommandProcessor functionalTester = new CommandProcessor();

    public boolean randomizedActionsTest() {
        boolean failedTest = false;

        Stack<String> currentStack = new Stack<>();
        String generatedStackSizeStr = Integer.toOctalString((int) (Math.random() * (30 + 5)) + 5);
        int stackSizeRandom = Integer.parseInt(generatedStackSizeStr);

        ArrayList<String> commands = new ArrayList<>();

        functionalTester.initializeWithArgs(new String[]{Integer.toString(stackSizeRandom)});

        commands.add("p");
        commands.add("n");
        commands.add("+");
        commands.add("&");
        commands.add("d");
        commands.add("ADD_NUM_TO_STACK");

        currentStack.clear();

        int numberOfCommands = (int) (Math.random() * (500 + 50) + 50);

        for (int i = 0; i < numberOfCommands && !failedTest; i++) {
            int randomCommandNum = (int) (Math.random() * (commands.size()));
            String currentCommand = commands.get(randomCommandNum);
            System.out.println("Chosen Random Action : " + currentCommand);

            switch(currentCommand)
            {
                case "d":
                    if(currentStack.size() >0)
                    {
                        if(currentStack.size() < stackSizeRandom)
                        {
                            functionalTester.sendCommand("d");
                            String top = currentStack.pop();
                            String duped = functionalTester.sendCommand("p");
                            String before = functionalTester.sendCommand("p");

                            if(!top.equals(duped) && top.equals(before))
                            {
                                System.out.println("randomizedActionsTest | Dup Test Failed.. ");
                                break;
                            }else
                                System.out.println("randomizedActionsTest | Dup Test Success | Top : "+top);
                        }
                    }
                    break;
                case "p":
                    if(currentStack.size() == 0){
                        /*
                        If we are empty let's send one and pop it
                         */
                        String randomNumber = Integer.toOctalString((int)(Math.random()*(1000000000+2000)+2000));
                        functionalTester.sendCommand(randomNumber);
                        String outputPop = functionalTester.sendCommand("p");
                        if(outputPop.length() >0) {
                            if (!outputPop.trim().equals(randomNumber)) {
                                System.out.println("randomizedActionsTest | Pop Test Failed.. ");
                                failedTest = true;
                                break;
                            }else
                                System.out.println("randomizedActionsTest | Pop Passed | " +outputPop + " | " + randomNumber);
                        }
                    }else{
                        String outputPop = functionalTester.sendCommand("p");
                        if(outputPop.length() > 0) {
                            String stackValue = "";
                            try {
                                stackValue = currentStack.pop();
                            } catch (Exception e) {
                                stackValue = "";
                            }
                            System.out.println(outputPop + " | " + stackValue);
                            if (!outputPop.trim().equals(stackValue.trim())) {
                                failedTest = true;
                                System.out.println("randomizedActionsTest | Pop Test Failed.. ");
                                break;
                            }else
                                System.out.println("randomizedActionsTest | Pop Passed | " +outputPop + " | " + stackValue);
                        }
                    }
                    break;
                case "+":
                    if(currentStack.size() > 1) {
                        String popped1 = functionalTester.sendCommand("p");
                        String popped2 = functionalTester.sendCommand("p");
                        TupleHelper programTuple = new TupleHelper(0,0);
                        programTuple.setFirstNumStr(popped1);
                        programTuple.setSecondNumStr(popped2);

                        if(popped1.length() > 0 && popped2.length() > 0) {

                            String firstInStack = currentStack.pop();
                            String secondInStack = currentStack.pop();
                            TupleHelper stackTuple = new TupleHelper(0,0);
                            stackTuple.setFirstNumStr(firstInStack);
                            stackTuple.setSecondNumStr(secondInStack);

                            if (!stackTuple.getPlusAnswer().equals(programTuple.getPlusAnswer())) {
                                failedTest = true;
                                System.out.println("randomizedActionsTest | Add Test Failed..  ");
                            }else
                                System.out.println("randomizedActionsTest | Add Passed | " +stackTuple.getPlusAnswer() + " | " + programTuple.getPlusAnswer());

                        }
                        break;
                    }else{
                     if(currentStack.size() >= stackSizeRandom)
                    {
                        functionalTester.sendCommand("p");
                        functionalTester.sendCommand("p");
                    }
                        if(!addTest(1)){
                            System.out.println("randomizedActionsTest | Add Test Failed..  ");
                            failedTest = true;
                        }
                    }
                    break;
                case "&":
                    if(currentStack.size() > 1) {
                        String popped1 = functionalTester.sendCommand("p");
                        String popped2 = functionalTester.sendCommand("p");
                        TupleHelper programTuple = new TupleHelper(0,0);
                        programTuple.setFirstNumStr(popped1);
                        programTuple.setSecondNumStr(popped2);


                        if(popped1.length() > 0 && popped2.length() > 0) {

                            String firstInStack = currentStack.pop();
                            String secondInStack = currentStack.pop();
                            TupleHelper stackTuple = new TupleHelper(0,0);
                            stackTuple.setFirstNumStr(firstInStack);
                            stackTuple.setSecondNumStr(secondInStack);

                            if (!stackTuple.getAndAnswer().equals(programTuple.getAndAnswer())) {
                                failedTest = true;
                                System.out.println("randomizedActionsTest | And Test Failed..  ");
                            }else {
                                System.out.println("randomizedActionsTest | And Passed | " +stackTuple.getAndAnswer() + " | " + programTuple.getAndAnswer());
                              }
                        }
                        break;
                    }else{
                       if(currentStack.size() >= stackSizeRandom)
                       {
                           functionalTester.sendCommand("p");
                           functionalTester.sendCommand("p");
                       }
                       if(!andTest(1)) {
                           System.out.println("randomizedActionsTest | And Test Failed..  ");
                           failedTest = true;
                       }

                    }
                    break;
                case "n":
                    if(currentStack.size() > 0) {
                        String nAns = functionalTester.sendCommand("n");
                        String popped = functionalTester.sendCommand("p");
                        if(popped.length() > 0) {
                            String stackValue = "";
                            try {
                                stackValue = currentStack.pop();
                            } catch (Exception e) {
                                stackValue = "";
                            }

                            String hexStack = Integer.toHexString(Integer.parseInt(stackValue, 8));
                            if (Math.ceil(hexStack.length() / 2.0) != Integer.parseInt(popped)) {
                                failedTest = true;
                                System.out.println("randomizedActionsTest |  n Test Failed.. ");
                            }else
                                System.out.println("randomizedActionsTest |  n Test Passed | Hex : "+hexStack + " | Integer :" + stackValue + " | Your Result: " + popped );
                        }
                        break;
                    }else{
                        String randomNumN = Integer.toOctalString((int)(Math.random()*(1000000000+20000)+20000));

                        functionalTester.sendCommand(randomNumN);
                        functionalTester.sendCommand("n");
                        String outputN = functionalTester.sendCommand("p");
                        if(outputN.length() > 0) {
                            if (Integer.parseInt(outputN) != (Math.ceil(Integer.toHexString(Integer.parseInt(randomNumN, 8)).length() / 2.0))) {
                                System.out.println("randomizedActionsTest | N Test Failed.. ");
                                failedTest = true;
                                break;
                            }
                        }
                    }
                case "ADD_NUM_TO_STACK":
                    String numToAdd = Integer.toOctalString((int) (Math.random() * (1000000000 + 20000) + 20000));

                    if(currentStack.size() < stackSizeRandom) {
                        currentStack.push(numToAdd);
                        System.out.println("randomizedActionsTest | Adding Random Number To Stack | " + numToAdd);
                    }
                    functionalTester.sendCommand(numToAdd);
                    break;
            }

        }
        functionalTester.kill();

        return !failedTest;
    }

    public boolean addTest(int numOfTests){
        for(int i = 1; i < numOfTests ; i++) {
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
    public boolean andTest(int numOfTests){
        for(int i = 1; i < numOfTests ; i++) {
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
    public boolean nOperationTest(int numOfTests){

        for(int i = 1; i < numOfTests ; i++) {
            TupleHelper nGener = new TupleHelper(i*10, i*1000*i);

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

    public boolean popTest(int numOfTests) {
        functionalTester.kill();
        functionalTester.initializeWithArgs(null); /* Defaulted to 5 stack */

        ArrayList<Integer> generatedValues = new ArrayList<Integer>();
        for (int i = 0; i < numOfTests; i++) {
            for (int j = 0; j < 5; j++) {
                int current = Integer.parseInt(Integer.toOctalString((int) (Math.random() * (100000+1)+1)));
                generatedValues.add(current);
                functionalTester.sendCommand(Integer.toString(current));
            }
            for (int j = 0; j < 5; j++) {
                String answerPop = functionalTester.sendCommand("p");
                if(answerPop.length() > 0) {
                    if (!answerPop.equals(Integer.toString(generatedValues.get(4 - j)))) {
                        System.out.println("Invalid Pop Test | Answer Of Program : " + answerPop + " | Should Be : " + generatedValues.get(4 - j));
                        return false;
                    } else
                        System.out.println("Correct Pop Test | Answer Of Program : " + answerPop + " | Should Be : " + generatedValues.get(4 - j));
                }
            }
            generatedValues.clear();
        }
        return true;
    }

    public boolean complementaryCheck(int numOfTests){
        for(int i = 1; i < numOfTests ; i++) {
            int sizeComplementStr = (int)(Math.random()*(20+1)+1);
            StringBuilder firstNum = new StringBuilder();
            StringBuilder secondNum = new StringBuilder();

            for(int j = 0; j < sizeComplementStr ; j++) {
                firstNum.append("5");
                secondNum.append("2");
            }
            TupleHelper nGener = new TupleHelper(0,0);
            nGener.setFirstNumStr(firstNum.toString());
            nGener.setSecondNumStr(secondNum.toString());


            functionalTester.sendCommand(nGener.getFirstNumStr());
            functionalTester.sendCommand(nGener.getSecondNumStr());

            functionalTester.sendCommand("&");
            functionalTester.sendCommand("n");
            String answerComplement = functionalTester.sendCommand("p");
            if (answerComplement.trim().length() > 0) {
                if (!answerComplement.equals("1")) {
                    System.out.println("Invalid Complementary Test");
                    return false;
                } else
                    System.out.println("Correct Complementary Test | Program Answer : "+answerComplement + " | Should Give : 1");
            }
        }
        return true;
    }

    public boolean checkOverrideWithDup()
    {
        for(int i = 0 ; i < 10; i++)
            functionalTester.sendCommand(Integer.toString(i));
        functionalTester.sendCommand("d");
        String top = functionalTester.sendCommand("p");
        String before = functionalTester.sendCommand("p");

        for(int i = 0; i < 3 ;i++)
            functionalTester.sendCommand("p");

        return !top.equals(before);
    }
    @Override
    public void run() {



        boolean sanityCheck = false;
        boolean nOperation  = false;
        boolean addOperation = false;
        boolean andOperation = false;
        boolean popOperation = false;
        boolean passedRandomActions = false;
        boolean complementaryTest = false;
        boolean duplicateBoundaryTest = false;

        int numberOfSingleTests = 500;

        sanityCheck = SanityChecks.checkStackSizeParamOctal();
        System.out.println("SanityCheck.java | Stack Size Param Test | Success : "+ sanityCheck);


        functionalTester.initializeWithArgs(null);

        System.out.println("Starting nOperation Test....\n-----------------------");
        nOperation = nOperationTest(numberOfSingleTests);
        System.out.println("nOperation Test | Success : " + nOperation);
        System.out.println("\nStarting And Test....\n-----------------------");
        andOperation = andTest(numberOfSingleTests);
        System.out.println("And Test | Success : " + andOperation);
        System.out.println("\nStarting Add Test....\n-----------------------");
        addOperation = addTest(numberOfSingleTests);
        System.out.println("Add Test | Success : " + addOperation);
        System.out.println("\nStarting PopTest....\n-----------------------");
        popOperation = popTest(15);
        System.out.println("Pop Test | Success : " + popOperation);
        complementaryTest = complementaryCheck(45);


        duplicateBoundaryTest = checkOverrideWithDup();
        System.out.println("Boundary Test With Duplicate | Success: "+duplicateBoundaryTest);

        functionalTester.kill();

        passedRandomActions = randomizedActionsTest();
        System.out.println("Randomized Action Test | " + passedRandomActions);

        if(duplicateBoundaryTest && complementaryTest && nOperation && andOperation && addOperation && popOperation && sanityCheck && passedRandomActions)
            System.out.println("Seems like everything is alright..\n");
        else
            System.out.println("Something is wrong..");

         functionalTester.kill();
    }
}
