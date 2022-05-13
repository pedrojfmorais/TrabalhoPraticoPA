package pt.isec.pa.apoio_poe.utils;

import java.util.Scanner;

public final class PAInput {
    private PAInput() {}

    private static Scanner sc;

    static {
        resetScanner();
    }

    public static void resetScanner() {
        sc = new Scanner(System.in);
    }

    public static String readString(String title,boolean onlyOneWord) {
        String value;
        do {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            value = sc.nextLine().trim();
        } while (value.isBlank());
        if (onlyOneWord) {
            Scanner auxsc = new Scanner(value);
            value = auxsc.next();
        }
        return value;
    }

    //Adicionado overload À funcção readString para aceitar input vazio, conforme a terceira variável
    public static String readString(String title,boolean onlyOneWord,boolean blankString) {
        String value;
        do {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            value = sc.nextLine().trim();
        } while (value.isBlank() && !blankString);
        if (onlyOneWord) {
            Scanner auxsc = new Scanner(value);
            value = auxsc.next();
        }
        return value;
    }

    public static Integer chooseOption(String title, String ... options) {
        int option = -1;
        do {
            if (title != null)
                System.out.println(System.lineSeparator()+title);
            System.out.println();
            for(int i = 0; i < options.length; i++) {
                System.out.printf("%3d - %s\n",i+1,options[i]);
            }
            System.out.print("\nOption: ");
            if (sc.hasNextInt())
                option = sc.nextInt();
            sc.nextLine();
        } while (option < 1 || option > options.length);
        return option;
    }

    public static Long readLong(String title) {
        while (true) {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            if (sc.hasNextLong()) {
                long longValue = sc.nextLong();
                sc.nextLine();
                return longValue;
            } else
                sc.nextLine();
        }
    }

    public static Double readDouble(String title) {
        while (true) {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            if (sc.hasNextDouble()) {
                double doubleValue = sc.nextDouble();
                sc.nextLine();
                return doubleValue;
            } else
                sc.nextLine();
        }
    }

    public static String readDouble(String title,boolean blankString) {

        String value;
        Scanner scanner;

        while (true){

            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");

            value = sc.nextLine();
            scanner = new Scanner(value);

            if (scanner.hasNextDouble()) {
                double doubleValue = scanner.nextDouble();
                return Double.toString(doubleValue);
            } else if(value.trim().isBlank() && blankString)
                return "";
        }
    }

}