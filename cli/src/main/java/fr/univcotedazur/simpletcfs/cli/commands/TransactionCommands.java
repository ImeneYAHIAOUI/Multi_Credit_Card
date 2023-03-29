package fr.univcotedazur.simpletcfs.cli.commands;

import org.springframework.shell.standard.ShellMethod;

public class TransactionCommands {

    @ShellMethod("Get all transactions")
    public String getAllTransactions() {

        return "Get all transactions";
    }
}
