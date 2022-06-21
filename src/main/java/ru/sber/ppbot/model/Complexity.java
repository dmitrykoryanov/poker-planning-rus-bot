package ru.sber.ppbot.model;

/**
 * Enum with complexity levels
 * <p>
 *  @author Dmitry Koryanov
 */
public enum Complexity {
    VERY_EASY(1, "Очень лёгкая"),
    EASY(2, "Лёгкая"),
    MEDIUM(3, "Средней сложности"),
    DIFFICULT(4, "Сложная"),
    VERY_DIFFICULT(5, "Очень сложная");

    private final int complexity;

    private final String text;

    private Complexity(int complexity, String text){
        this.complexity = complexity;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getComplexity() {
        return complexity;
    }
}
