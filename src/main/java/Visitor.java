public class Visitor {

        private static int totalVisitors = 0;

        public Visitor() {
            totalVisitors++;
        }

        public static int getTotalVisitors() {
            return totalVisitors;
        }
}
