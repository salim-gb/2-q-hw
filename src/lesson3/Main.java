package lesson3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Moscow");
        arrayList.add("Damascus");
        arrayList.add( "Paris");
        arrayList.add("NewYork");
        arrayList.add( "Berlin");
        arrayList.add( "Aleppo");
        arrayList.add( "Ekaterinburg");
        arrayList.add( "Moscow");
        arrayList.add("California");
        arrayList.add("Berlin");
        arrayList.add("Paris");
        arrayList.add("Dubai");
        arrayList.add("Mexico");

        Set<String> set = new HashSet<>(arrayList);

        System.out.println("Начальный списаок: " + arrayList);
        System.out.println("Список без дубликатов: " + set);

        Map<String , Integer> map = new HashMap<>();
        for (String s : arrayList) {
            Integer value = map.getOrDefault(s, 0);
            map.put(s, value + 1);
        }
        System.out.println();
        System.out.println(map);

        System.out.println("***************************");

        Phonebook phonebook = new Phonebook();
        phonebook.addPhone("Салим", "89264798156");
        phonebook.addPhone("Салим", "89678241594");
        phonebook.addPhone("Салим", "89678241594");
        phonebook.addPhone("Алексей", "89673450912");
        phonebook.getPhone("Салим");
        phonebook.getPhone("Алексей");
    }
}
