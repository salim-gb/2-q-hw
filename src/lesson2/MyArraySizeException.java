package lesson2;

public class MyArraySizeException extends IllegalArgumentException{
    private String[][] array;

    public MyArraySizeException(String[][] array) {
        this.array = array;
    }

    public MyArraySizeException(String s, String[][] array) {
        super(s);
        this.array = array;
    }

    public String[][] getArray() {
        return array;
    }
}
