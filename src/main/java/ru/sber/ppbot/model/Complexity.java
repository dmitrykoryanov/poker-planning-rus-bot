package ru.sber.ppbot.model;

public enum Complexity {
    VERY_EASY(1),
    EASY(2),
    MEDIUM(3),
    DIFFICULT(4),
    VERY_DIFFICULT(5);

    private final int complexity;

    private Complexity(int complexity){
        this.complexity = complexity;
    }

    public int getComplexity() {
        return complexity;
    }
}
