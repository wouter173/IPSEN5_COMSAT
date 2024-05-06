package org.example;

public class Main {
    public static void main(String[] args) {
        Duck houteneend = new Duck();
        houteneend.ChangeFlightBehaviour(new FlyWithoutWings());
        houteneend.doFly();
        houteneend.ChangeQuackBehaviour(new SilentQuack());
        houteneend.doQuack();

        Duck rubberEend = new Duck();
        rubberEend.ChangeFlightBehaviour(new FlyWithoutWings());
        rubberEend.doFly();
        rubberEend.ChangeQuackBehaviour(new AudibleQuack());
        rubberEend.doQuack();

        Duck eend = new Duck();
        eend.ChangeFlightBehaviour(new FlyWithWings());
        eend.doFly();
        eend.ChangeQuackBehaviour(new AudibleQuack());
        eend.doQuack();
    }
}