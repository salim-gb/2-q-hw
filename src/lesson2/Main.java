package lesson2;

public class Main {
    public static void main(String[] args) {
        String[][] array = {
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "l"}
        };

        try {
            checkArray(array);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }

    static void checkArray(String[][] arr) throws MyArraySizeException {
        int sum = 0;
        if (arr.length == 4) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    if (arr[i].length != 4) {
                        throw new MyArraySizeException("Массив должен быть двумерный 4 * 4", arr);
                    } else {
                        sum += convertStringToInt(arr[i][j], i, j);
                    }
                }
            }
        } else {
            throw new MyArraySizeException("Массив должен быть двумерный 4 * 4", arr);
        }
        System.out.println("Сумма всех ячейк равна: " + sum);
    }

    static int convertStringToInt(String s, int row, int col) {
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new MyArrayDataException("В ячейке [" + (row + 1) + "][" + (col + 1) + "] " +
                    "в массиве неверные данные: " + s , s);
        }
        return i;
    }
}
