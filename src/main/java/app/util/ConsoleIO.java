package app.util;

import java.util.Scanner;

public class ConsoleIO {
    private static final Scanner SC = new Scanner(System.in);

    public static String ask(String prompt) {
        System.out.print(prompt);
        return SC.nextLine();
    }

    public static int askInt(String prompt, int def) {
        System.out.print(prompt);
        String s = SC.nextLine();
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }
}