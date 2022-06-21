import java.util.ArrayList;
import java.util.Scanner;

public class TodoApplication {
    public static final String RESET = "\033[0m";

    public static final String RED = "\033[0;31m";

    public static final String WHITE_BOLD = "\033[1;97m";

    public static final String RED_BOLD = "\033[1;31m";

    public static ArrayList<String> todos = new ArrayList<>();

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        clearConsole();

        launch();
    }

    public static void clearConsole() {
        System.out.flush();

        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception ignored) {
        }
    }

    public static void header(String text, int padding) {
        String border = "=".repeat(text.length() + (padding * 2));

        System.out.println(border);
        System.out.println(" ".repeat(padding) + text + " ".repeat(padding));
        System.out.println(border);

        System.out.print(System.lineSeparator());
    }

    public static void launch() {
        label:
        while (true) {
            showTodoList();

            System.out.print(System.lineSeparator());

            System.out.println("Actions:");
            System.out.println("[1] Add new todo");
            System.out.println("[2] Remove todo");
            System.out.println("[q] Quit");

            System.out.print("Your input: ");

            String input = scanner.nextLine();

            clearConsole();

            switch (input) {
                case "1":
                    addNewTodo();
                    break;
                case "2":
                    removeTodo();
                    break;
                case "q":
                    System.out.println("Good bye :)");
                    break label;
                default:
                    System.out.print(System.lineSeparator());
                    System.out.println(RED_BOLD + "Error:" + RESET + RED + " Menu option is not registered!" + RESET);
                    System.out.print(System.lineSeparator());
                    break;
            }
        }
    }

    /**
     * Show list.
     */
    public static void showTodoList() {
        header("Your Todo List", 12);

        if (todos.isEmpty()) {
            System.out.println("What do you want to do today?");
            return;
        }

        for (String todo : todos) {
            if (todo != null) {
                System.out.println("- " + todo);
            }
        }
    }

    /**
     * Add new item.
     */
    public static void addNewTodo() {
        while (true) {
            header("Add New", 14);

            System.out.println("Type " + WHITE_BOLD + "!cancel" + RESET + " to cancel add todo.");

            System.out.print("Todo: ");

            String input = scanner.nextLine().trim();

            clearConsole();

            if (input.startsWith("!")) {
                String cmd = input.substring(1);

                if (cmd.equals("cancel")) {
                    break;
                } else {
                    System.out.print(System.lineSeparator());
                    System.out.println(RED_BOLD + "Error:" + RESET + RED + " Unknown command." + RESET);
                    System.out.print(System.lineSeparator());

                    continue;
                }
            }

            if (todos.contains(input)) {
                System.out.print(System.lineSeparator());
                System.out.println(RED_BOLD + "Error:" + RESET + RED + " Already exists." + RESET);
                System.out.print(System.lineSeparator());
            } else {
                todos.add(input);

                break;
            }
        }
    }

    private static int parseInt(String str) {
        int result;

        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = -1;
        }

        return result;
    }

    /**
     * Remove item.
     */
    public static void removeTodo() {
        while (true) {
            header("Remove Todo", 14);

            System.out.println("Type " + WHITE_BOLD + "!cancel" + RESET + " to cancel remove todo.");

            for (int i = 0; i < todos.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + todos.get(i));
            }

            System.out.print("Number: ");

            String input = scanner.nextLine().trim();

            clearConsole();

            if (input.startsWith("!")) {
                String cmd = input.substring(1);

                if (cmd.equals("cancel")) {
                    break;
                } else {
                    System.out.print(System.lineSeparator());
                    System.out.println(RED_BOLD + "Error:" + RESET + RED + " Unknown command." + RESET);
                    System.out.print(System.lineSeparator());

                    continue;
                }
            }

            int index = parseInt(input);

            if (index == -1) {
                System.out.print(System.lineSeparator());
                System.out.println(RED_BOLD + "Error:" + RESET + RED + " Unknown number." + RESET);
                System.out.print(System.lineSeparator());

                continue;
            }

            index -= 1;

            try {
                todos.remove(index);

                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.print(System.lineSeparator());
                System.out.println(RED_BOLD + "Error:" + RESET + RED + " Todo not found." + RESET);
                System.out.print(System.lineSeparator());
            }
        }
    }
}
