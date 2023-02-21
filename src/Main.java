import java.util.*;

public class Main {

    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение или пробел для выхода.");
        while (true) {
            input = scanner.nextLine();
            if ("".equals(input.trim())) break;
            else System.out.println("Ответ: "+calc(input));
        }
    }

    public static String calc(String input) {
        final int index;
        final char operator;
        final String operandOne;
        final String operandTwo;
        final int operand1;
        final int operand2;
        if (input.contains("\n"))
            throw new NumberFormatException("Данные передны не в одну строку");
        index = indexOfOperator(input);
        operator = input.charAt(index);
        operandOne = input.substring(0, index).trim();
        operandTwo = input.substring(index + 1).trim();
        if (operandOne.length() == 0 || operandTwo.length() == 0)
            throw new NumberFormatException("Отсутствует один из операндов");

        if (checkRoman(operandOne) && checkRoman(operandTwo)) {
            operand1 = romanToArabian(operandOne);
            operand2 = romanToArabian(operandTwo);
            int result = calculator(operand1, operand2, operator);
            if (result > 0) return arabianToRoman(result);
            else {
                throw new NumberFormatException("Римскими цифрами может быть представлен результат больше 0");
            }
        } else {
            operand1 = checkAndParseArabian(operandOne);
            operand2 = checkAndParseArabian(operandTwo);
            int result = calculator(operand1, operand2, operator);
            return String.valueOf(result);
        }
    }

    private static int indexOfOperator(String input) {
        if (input.contains("+")) return input.indexOf('+');
        else if (input.contains("-")) return input.indexOf('-');
        else if (input.contains("*")) return input.indexOf('*');
        else if (input.contains("/")) return input.indexOf('/');
        else {
            throw new NoSuchElementException("Оператор отсутствует во входной строке");
        }
    }

    private static boolean checkRoman(String operand) {
        boolean roman;
        for (int i = 0; i < operand.length(); i++) {
            roman = operand.charAt(i) == 'I' || operand.charAt(i) == 'V' || operand.charAt(i) == 'X';
            if (!roman) {
                return false;
            }
        }
        if (operand.contains("V") && !(operand.indexOf('V') == operand.lastIndexOf('V')))
            throw new NumberFormatException("В операнде может быть только одна цифра V");
        if (operand.contains("I")) {
            int betweenIndex = operand.lastIndexOf('I') - operand.indexOf('I');
            if ((betweenIndex > 2) || (betweenIndex == 2) && operand.charAt(operand.indexOf('I') + 1) != 'I') {
                throw new NumberFormatException("Ошибка записи в операнд римских цифр I");
            }
        }
        return true;
    }

    private static int checkAndParseArabian(String operand) {
        try {
            return Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ошибка ввода");
        }
    }

    private static int romanToArabian(String operand) {
        int sum = 0;
        for (Roman roman : Roman.values()) {
            while (operand.indexOf(roman.toString()) == 0) {
                sum += roman.toInt();
                operand = operand.substring(roman.toString().length());
            }
        }
        if (operand.length() != 0) throw new NumberFormatException("Ошибка при вводе римского числа");
        return sum;
    }

    private static String arabianToRoman(int sum) {
        StringBuilder result = new StringBuilder();
        for (Roman roman : Roman.values()) {
            while (sum >= roman.toInt()) {
                sum -= roman.toInt();
                result.append(roman);
            }
        }
        return result.toString();
    }

    private static int calculator(int operandOne, int operandTwo, char operator) {
        if (operandOne <= 0 || operandOne > 10 || operandTwo <= 0 || operandTwo > 10) {
            throw new NumberFormatException("Операнды должны быть в диапазоне 1-10");
        } else {
            switch (operator) {
                case '+' -> {
                    return operandOne + operandTwo;
                }
                case '-' -> {
                    return operandOne - operandTwo;
                }
                case '*' -> {
                    return operandOne * operandTwo;
                }
                case '/' -> {
                    return operandOne / operandTwo;
                }
                default -> throw new IllegalArgumentException("В метод передан неверный оператор");
            }
        }
    }
}

enum Roman {
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1);

    private final int value;

    Roman(int value) {
        this.value = value;
    }

    int toInt() {
        return value;
    }
}