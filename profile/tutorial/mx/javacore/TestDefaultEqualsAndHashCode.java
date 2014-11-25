package mx.javacore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hxiong on 6/26/14.
 */
public class TestDefaultEqualsAndHashCode {

    static void test(){
        Set<Person> set = new HashSet<>();
        Map<Person, Integer> map = new HashMap<>();

        Person a = new Person(1, "A");
        Person b = new Person(2, "B");
        Person c = new Person(3, "C");

        set.add(a);
        set.add(b);
        set.add(c);

        map.put(a, 10);
        map.put(b, 20);
        map.put(c, 30);

        System.out.println("set.size():" + set.size());
        System.out.println("map.size():" + map.size());
        System.out.println("map.get():" + map.get(new Person(3, "c")));

    }

    public static void main(String[] args) {
        test();
    }

}

class Person{
    int id;
    String name;

    Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Person person = (Person) o;
//
//        if (id != person.id) return false;
//        if (name != null ? !name.equals(person.name) : person.name != null) return false;
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//       return 1;
//    }
}
