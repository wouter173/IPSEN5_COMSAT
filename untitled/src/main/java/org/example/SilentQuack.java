package org.example;

public class SilentQuack implements IQuackBehaviour{

    @Override
    public void quack() {
        System.out.println("** **\n");
    }
}
