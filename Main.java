import Test.FunctionalTests;

public class Main {

    public static void main(String [] args)
    {
        new Thread(new FunctionalTests()).start();
    }
}
