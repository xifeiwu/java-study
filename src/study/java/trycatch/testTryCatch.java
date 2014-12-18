package study.java.trycatch;

public class testTryCatch {
    public static boolean catchMethod() {
        System.out.print("call catchMethod and return  --->>  ");
        return false;
    }

    // finally后续处理工作
    public static void finallyMethod() {
        System.out.println();
        System.out.print("call finallyMethod and do something  --->>  ");
    }
    
    //抛出Exception，没有finally，当catch遇上return
    public static boolean catchTest() {
        try {
            int i = 10 / 0; // 抛出 Exception，后续处理被拒绝
            System.out.println("i vaule is : " + i);
            return true; // Exception 已经抛出，没有获得被执行的机会
        } catch (Exception e) {
            System.out.println(" -- Exception --");
            return catchMethod(); // Exception 抛出，获得了调用方法并返回方法值的机会
        }
    }
    
    //抛出Exception，当catch体里有return，finally体的代码块将在catch执行return之前被执行
    public static boolean catchFinallyTest1() {
        try {
            int i = 10 / 0; // 抛出 Exception，后续处理被拒绝
            System.out.println("i vaule is : " + i);
            return true; // Exception 已经抛出，没有获得被执行的机会
        } catch (Exception e) {
            System.out.println(" -- Exception --");
            return catchMethod(); // Exception 抛出，获得了调用方法的机会，但方法值在 finally
                                  // 执行完后才返回
        } finally {
            finallyMethod(); // Exception 抛出，finally 代码块将在 catch 执行 return 之前被执行
        }
    }
    
    //不抛Exception，当finally代码块里面遇上return，finally执行完后将结束整个方法
    public static boolean catchFinallyTest2() {
        try {
            int i = 10 / 2; // 不抛出 Exception
            System.out.println("i vaule is : " + i);
            return true; // 获得被执行的机会，但执行需要在 finally 执行完成之后才能被执行
        } catch (Exception e) {
            System.out.println(" -- Exception --");
            return catchMethod();
        } finally {
            finallyMethod();
            return false; // finally 中含有 return 语句，这个 return 将结束这个方法，不会在执行完之后再跳回
                          // try 或 catch 继续执行，方法到此结束，返回 false
        }
    }
    
    //抛出Exception，当catch和finally同时遇上return，catch的return返回值将不会被返回，
    //finally的return语句将结束整个方法并返回
    public static boolean finallyTest1() {
        try {
            int i = 10 / 0; // 抛出 Exception，后续处理被拒绝
            System.out.println("i vaule is : " + i);
            return true; // Exception 已经抛出，没有获得被执行的机会
        } catch (Exception e) {
            System.out.println(" -- Exception --");
            return true; // Exception 已经抛出，获得被执行的机会，但返回操作将被 finally 截断
        } finally {
            finallyMethod();
            return false; // return 将结束整个方法，返回 false
        }
    }
    public static void main(String[] args){
//        catchTest();
//        catchFinallyTest1();
        catchFinallyTest2();
    }
    /**
    java 的异常处理中，
    在不抛出异常的情况下，程序执行完try里面的代码块之后，该方法并不会立即结束，而是继续试图去寻找该方法有没有finally的代码块，
    如果没有finally代码块，整个方法在执行完try代码块后返回相应的值来结束整个方法；
    如果有finally代码块，此时程序执行到try代码块里的return语句之时并不会立即执行return，而是先去执行finally代码块里的代码，
    若finally代码块里没有return或没有能够终止程序的代码，程序将在执行完finally代码块代码之后再返回try代码块执行return语句来结束整个方法；
    若finally代码块里有return或含有能够终止程序的代码，方法将在执行完finally之后被结束，不再跳回try代码块执行return。
    在抛出异常的情况下，原理也是和上面的一样的，你把上面说到的try换成catch去理解就OK了 *_*
    */
}
