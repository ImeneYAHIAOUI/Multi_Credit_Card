package fr.univcotedazur.multiCredit.cli.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@ShellComponent
public class DemoCommands {

    @ShellMethod("get last shop id")
    public String getLastShopId() {
        String[] tokens;
        try {
            Scanner scanner = new Scanner(new File("history"));
            while (scanner.hasNextLine())       // change this
            {
                tokens = scanner.nextLine().split(",");
                System.out.println(tokens.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
