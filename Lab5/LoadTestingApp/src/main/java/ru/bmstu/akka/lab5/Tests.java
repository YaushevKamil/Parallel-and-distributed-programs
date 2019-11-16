package ru.bmstu.akka.lab5;

public class Tests {
    private Test test; //maybe List
    private Long sum;

    public Tests(Test test, Long sum) {
        this.test = test;
        this.sum = sum;
    }

    public Test getTest() {
        return test;
    }

    public Long getSum() {
        return sum;
    }
}
