package lesson3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Phonebook {

    Map<String, Set<String>> map = new HashMap<>();

    public void addPhone(String name, String phone) {
        Set<String> set = new HashSet<>();
        if (map.get(name) != null) {
            set = map.get(name);
        }
        set.add(phone);
        map.put(name, set);
    }

    public void getPhone(String name) {
        if (map.get(name) == null) {
            System.out.println("У " + name + " нет неодного номера!");
        } else {
            System.out.println("У " + name + " есть следующие номера: " + map.get(name));
        }
    }
}
