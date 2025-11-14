package calculator;

public class Calculator {

    private final Tokenizer tokenizer;
    private final Parser parser;
    private final Evaluator evaluator;

    Calculator(Tokenizer tokenizer, Parser parser, Evaluator evaluator) {
        this.tokenizer = tokenizer;
        this.parser = parser;
        this.evaluator = evaluator;
    }

    public Result evaluate(String expression) {



        return new Result();
    }

}
