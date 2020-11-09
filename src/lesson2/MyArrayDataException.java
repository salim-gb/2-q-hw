package lesson2;

public class MyArrayDataException extends NumberFormatException{
    private String s;

    public MyArrayDataException(String s) {
        this.s = s;
    }

    public MyArrayDataException(String s, String string) {
        super(s);
        this.s = s;
    }

    public String getArray() {
        return s;
    }
}
