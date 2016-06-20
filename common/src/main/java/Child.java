/**
 * Created by zhangbing on 16/3/26.
 */

class Child extends Father{

    public void func1(int i){
        System.out.println("BBB");
    }

    public void func2(){
        System.out.println("CCC");
    }

    public static void main(String [] args){
        Father child = new Child();
        child.func1();
    }
}
