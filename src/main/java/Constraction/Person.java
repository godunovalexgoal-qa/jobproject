package Constraction;

public class Person {
    //    public class Person {
//        String firstName;
//        String lastName;
//        int age;
//
//        public void introduce() {
//            System.out.println("Привет, меня зовут " + firstName + " " + lastName + ". Мне " + age + " лет.");
//        };
//
//        public static void main(String[] args) {
//            Person person = new Person();
//            person.firstName = "Nikolay";
//            person.lastName = "Baskov";
//            person.age = 25;
//            person.introduce();
//        }
//    }
    private String firstName;
    private String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public void introduce() {
        System.out.println("Меня зовут " + firstName + " " + lastName + ", мне " + age);
    }

    public static void main(String[] args) {
        Person p1 = new Person("Alex", "Ivanov", 25);
        Person p2 = new Person("Maria", "Petrova", 30);
        Person p3 = new Person("John", "Smith", 40);

        p1.introduce();
        p2.introduce();
        p3.introduce();
    }
}
