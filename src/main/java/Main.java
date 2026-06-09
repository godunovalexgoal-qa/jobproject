public class Main {
    public static void main1(String[] args) {
        BankAccount account = new BankAccount();

        account.setOwnerName("Alex");

//        account.setBalance(1000);
        account.setBalance(-500);

        System.out.println("Владелец: " + account.getOwnerName());
        System.out.println("Баланс: " + account.getBalance());
    }

    public static void main2(String[] args) {
        int s = MathHelper.sum(10, 5);
        int m = MathHelper.max(10, 5);
        boolean even = MathHelper.isEven(10);

        System.out.println("Сумма: " + s);
        System.out.println("Максимум: " + m);
        System.out.println("10 четное? " + even);
    }

    public static void main3(String[] args) {

        Visitor v1 = new Visitor();
        System.out.println("Всего посетителей: " + Visitor.getTotalVisitors());

        Visitor v2 = new Visitor();
        System.out.println("Всего посетителей: " + Visitor.getTotalVisitors());

        Visitor v3 = new Visitor();
        System.out.println("Всего посетителей: " + Visitor.getTotalVisitors());

        Visitor v4 = new Visitor();
        System.out.println("Всего посетителей: " + Visitor.getTotalVisitors());

        Visitor v5 = new Visitor();
        System.out.println("Всего посетителей: " + Visitor.getTotalVisitors());

        // Даже если вызвать через объект:
        // Значение одинаковое для всех объектов, потому что totalVisitors — статическая переменная.
        // Она хранится один раз на весь класс, а не в каждом объекте отдельно.
        System.out.println("Через объект v3: " + v3.getTotalVisitors());
    }

    public static void main(String[] args) {
        Product apple = new Product("Apple", 50);
        Product banana = new Product("Banana", 30);
        Product orange = new Product("Orange", 40);

        Cart cart = new Cart();

        cart.addProduct(apple, 3);
        cart.addProduct(banana, 2);
        cart.addProduct(orange, 5);
        cart.addProduct(apple, 2);

        System.out.println("Общее количество товаров: " + cart.getProductCount());
        System.out.println("Общая сумма: " + cart.getTotalPrice());
    }


}
