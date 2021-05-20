package Test;

public class TupleHelper {

    private int firstNum;
    private int secondNum;

    private String  firstNumStr;
    private String  secondNumStr;

    public TupleHelper(int lowerLim, int upperLim)
    {
        generateTuple(lowerLim,upperLim);
    }
    public void generateTuple(int lowerLim,int upperLim){
        firstNum  = Integer.parseInt(Integer.toOctalString((int) (Math.random() * (upperLim+lowerLim)+lowerLim)));
        secondNum = Integer.parseInt(Integer.toOctalString((int) (Math.random() * (upperLim+lowerLim)+lowerLim)));

        firstNumStr = Integer.toString(firstNum);
        secondNumStr = Integer.toString(secondNum);
    }

    public int getFirstNum(){
        return firstNum;
    }
    public int getSecondNum(){
        return secondNum;
    }

    public String getFirstNumStr(){
        return firstNumStr;
    }
    public String getSecondNumStr(){
        return secondNumStr;
    }
    /*
    Returns and answer between 2 octals
     */
    public String getAndAnswer(){
        int i1 = Integer.parseInt(firstNumStr,8);
        int i2 = Integer.parseInt(secondNumStr,8);
        return Integer.toOctalString(i1&i2);
    }

    /*
    Returns the plus answer between 2 octals
     */
    public String getPlusAnswer(){
        int i1 = Integer.parseInt(firstNumStr,8);
        int i2 = Integer.parseInt(secondNumStr,8);
        return Integer.toOctalString(i1 + i2);
    }

    /*
    Return number of bytes to store the octal in hex
     */
    public double getNAnswer(int which){
      if(which == 1)
          return  Math.ceil(Integer.toHexString(Integer.parseInt(firstNumStr,8)).length()) / 2.0;
       return Math.ceil(Integer.toHexString(Integer.parseInt(secondNumStr,8)).length() / 2.0);
    }
}
