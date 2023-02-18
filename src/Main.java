import java.util.*;

public class Main {


    public static String calc(String input) {
        Converter converter = new Converter();
        final int index;
        final char operator;
        final String operandOne;
        final String operandTwo;
        final int operand1;
        final int operand2;

        index = indexOfOperator(input);
        operator = input.charAt(index);
        operandOne = input.substring(0, index);
        operandTwo = input.substring(index + 1);

        if (checkRoman(operandOne) && checkRoman(operandTwo)) {
            operand1 = converter.convertToArabian(operandOne);
            operand2 = converter.convertToArabian(operandTwo);
            int result = calculator(operand1, operand2, operator);
            if (result > 0) return converter.convertToRoman(result);
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
        boolean roman = true;
        for (int i = 0; i < operand.length(); i++) {
            roman = roman && (operand.charAt(i) == 'I' || operand.charAt(i) == 'V' || operand.charAt(i) == 'X');
        }
        return roman;
    }

    private static int checkAndParseArabian(String operand){
        try {
            return Integer.parseInt(operand);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Ошибка ввода");
        }
    }

    private static Integer calculator(int operandOne, int operandTwo, char operator) {
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
                default -> {
                    throw new IllegalArgumentException("В метод передан неверный оператор");
                }
            }
        }
    }
}

class Converter {
    final private Comparator<Integer> comparator = (o1, o2) -> o2 - o1;
    final private Map<String, Integer> romanToArabian = new HashMap<>();
    final private Map<Integer, String> arabianToRoman = new TreeMap<>(comparator);

    Converter() {
        romanToArabian.put("I", 1);
        romanToArabian.put("II", 2);
        romanToArabian.put("III", 3);
        romanToArabian.put("IV", 4);
        romanToArabian.put("V", 5);
        romanToArabian.put("VI", 6);
        romanToArabian.put("VII", 7);
        romanToArabian.put("VIII", 8);
        romanToArabian.put("IX", 9);
        romanToArabian.put("X", 10);

        arabianToRoman.put(100, "C");
        arabianToRoman.put(90, "XC");
        arabianToRoman.put(50, "L");
        arabianToRoman.put(40, "XL");
        arabianToRoman.put(10, "X");
        arabianToRoman.put(9, "IX");
        arabianToRoman.put(5, "V");
        arabianToRoman.put(4, "IV");
        arabianToRoman.put(1, "I");
    }

    int convertToArabian(String operand) {
        if (romanToArabian.containsKey(operand)) return romanToArabian.get(operand);
        else throw new NumberFormatException("Ошибка при вводе римского числа");
    }

    String convertToRoman(int sum) {
        StringBuilder result = new StringBuilder();
        for (Integer key : arabianToRoman.keySet()) {
            while (sum >= key) {
                sum -= key;
                result.append(arabianToRoman.get(key));
            }
        }
        return result.toString();
    }
}