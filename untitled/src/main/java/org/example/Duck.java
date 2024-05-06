package org.example;

public class Duck {
    IFlyBehaviour flyBehaviour;
    IQuackBehaviour quackBehaviour;

    public void doFly(){
        flyBehaviour.fly();
    }

    public void ChangeFlightBehaviour(IFlyBehaviour newFlyBehaviour){
        flyBehaviour = newFlyBehaviour;
    }

    public void doQuack(){
        quackBehaviour.quack();
    }

    public void ChangeQuackBehaviour(IQuackBehaviour newQuackBehaviour){
        quackBehaviour = newQuackBehaviour;
    }


}
