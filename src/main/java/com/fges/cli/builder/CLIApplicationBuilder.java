package com.fges.cli.builder;

import com.fges.factory.GroceryAppFactory;
import com.fges.serviceimpl.GroceryListServiceImpl;
import org.apache.commons.cli.*;

public class CLIApplicationBuilder {
    private String[] args;
    private Options cliOptions;
    private CommandLine cmd;

    public CLIApplicationBuilder(String[] args) {
        this.args = args;
        this.cliOptions = new Options();
    }

    public CLIApplicationBuilder setupOptions() {
        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Storage format: csv or json (default: json)");
        cliOptions.addOption("c", "category",true, "Category of element, by default the categoy is 'default'");
        return this;
    }

    public CLIApplicationBuilder parseArguments() throws ParseException {
        CommandLineParser parser = new DefaultParser();
        this.cmd = parser.parse(cliOptions, args, true);
        return this;
    }

    public String[] getParsedArgs() {
        return cmd.getArgs(); // Récupère les arguments non-optionnels après parsing
    }

    public GroceryListServiceImpl buildService() {
        String fileName = cmd.getOptionValue("s");
        String format = cmd.hasOption("f") ? cmd.getOptionValue("f") : "json"; // Par défaut JSON
        String[] remainingArgs = cmd.getArgs();
        if (remainingArgs.length == 0) {
            throw new IllegalArgumentException("Aucune commande fournie après les options.");
        }

        return GroceryAppFactory.createGroceryApp(format, fileName);
    }

    public String getCategory() {
        return cmd.hasOption("c") ? cmd.getOptionValue("c") : "default";
    }
}