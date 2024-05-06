package org.example;

public class AudibleQuack implements IQuackBehaviour{

    @Override
    public void quack() {
        System.out.println("Quack\n");
    }
}
