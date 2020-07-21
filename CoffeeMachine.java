import java.util.Scanner;

public class CoffeeMachine {
    private static final Scanner scanner = new Scanner(System.in);
    private static int water = 400;
    private static int milk = 540;
    private static int beans = 120;
    private static int disposableCups = 9;
    private static int total = 550;

    enum Action {
        BUY, FILL, TAKE, REMAINING, BACK, EXIT;

        public static Action selectAction(String selectedAction) {
            if (selectedAction.equals("buy")) {
                return BUY;
            } else if (selectedAction.equals("fill")) {
                return FILL;
            } else if (selectedAction.equals("take")) {
                return TAKE;
            } else if (selectedAction.equals("remaining")) {
                return REMAINING;
            } else if (selectedAction.equals("back")) {
                return BACK;
            } else if (selectedAction.equals("exit")) {
                return EXIT;
            }
            throw new IllegalArgumentException(selectedAction +
                    " is not a valid action");
        }
    }

    enum Coffee {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        private final int water, milk, beans, cost;

        Coffee(int water, int milk, int beans, int cost) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cost = cost;
        }

        public int getWater() {
            return water;
        }

        public int getMilk() {
            return milk;
        }

        public int getBeans() {
            return beans;
        }

        public int getCost() {
            return cost;
        }

        public static Coffee getCoffeeForChoice(String choice) {
            if (choice.equals("1")) {
                return ESPRESSO;
            } else if (choice.equals("2")) {
                return LATTE;
            } else if (choice.equals("3")) {
                return CAPPUCCINO;
            }

            throw new IllegalArgumentException(choice + " is not a valid choice for coffee");
        }
    }

    public static void main(String[] args) {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        while (scanner.hasNext()) {
            String currentAction = scanner.nextLine();
            Action action = Action.selectAction(currentAction);
            switch (action) {
                case BUY:
                    buyCoffee();
                    break;
                case FILL:
                    fillMachine();
                    break;
                case TAKE:
                    takeMoney();
                    break;
                case REMAINING:
                    printState();
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println("This is not a valid action");
            }
        }
    }

    public static void buyCoffee() {
        System.out.println("What would you want to buy? " +
                "1 - espresso, " +
                "2 - latte, " +
                "3 - cappuccino:");
        String command = scanner.nextLine();
        if (disposableCups < 1) {
            System.out.println("Sorry, not enough disposable coffee cups!");
            return;
        }
        if (command.equals("back")) {
            return;
        }
        Coffee selectedCoffee = Coffee.getCoffeeForChoice(command);

        if (water < selectedCoffee.getWater()) {
            System.out.println("Sorry, not enough water!");
            return;
        } else if (milk < selectedCoffee.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return;
        } else if (beans < selectedCoffee.getBeans()) {
            System.out.println("Sorry, not enough coffee beans");
            return;
        } else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= selectedCoffee.getWater();
            milk -= selectedCoffee.getMilk();
            beans -= selectedCoffee.getBeans();
            total += selectedCoffee.getCost();
            --disposableCups;
            return;
        }
    }

    public static void fillMachine() {
        System.out.println("Write how many ml of water do you want to add:");
        int addedWater = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int addedMilk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int addedBeans = scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add:");
        int addedDisposableCups = scanner.nextInt();
        water += addedWater;
        milk += addedMilk;
        beans += addedBeans;
        disposableCups += addedDisposableCups;
        scanner.nextLine();
    }

    public static void takeMoney() {
        System.out.println("I gave you " + total);
        total = 0;
    }

    public static void printState() {
        System.out.println("The coffee machine has:\n" +
                water + " of water\n" +
                milk + " of milk\n" +
                beans + " of coffee beans\n" +
                disposableCups + " of disposable cups\n" +
                total + " of money");
    }
}