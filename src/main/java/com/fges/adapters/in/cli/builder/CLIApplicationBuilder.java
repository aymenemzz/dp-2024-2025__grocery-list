package com.fges.adapters.in.cli.builder;

import com.fges.application.service.GroceryListServiceImpl;
import com.fges.factory.GroceryAppFactory;
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
        cliOptions.addOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "Storage format: csv or json (default: json)");
        cliOptions.addOption("c", "category",true, "Category of element, by default the categoy is 'default'");
        return this;
    }

    public CLIApplicationBuilder parseArguments() throws ParseException {
        CommandLineParser parser = new DefaultParser();
        // Permettre une exception à la règle des options obligatoires si la commande "info" est utilisée
        if (args.length > 0 && args[0].equals("info")) {
            this.cmd = parser.parse(cliOptions, args, false); // n'exige pas les options
        } else {
            this.cmd = parser.parse(cliOptions, args, true); // exige les options
        }
        return this;
    }

    public String[] getParsedArgs() {
        return cmd.getArgs(); // Récupère les arguments non-optionnels après parsing
    }

    public GroceryListServiceImpl buildService() {
        String[] remainingArgs = cmd.getArgs();
        if (remainingArgs.length > 0 && "info".equals(remainingArgs[0])) {
            return null;
        }
        String fileName;
        if (cmd.hasOption("s")) {
            fileName = cmd.getOptionValue("s");
        } else {
            throw new RuntimeException("Missing required option to manage your grocery list: -s");
        }
        String format = cmd.hasOption("f") ? cmd.getOptionValue("f") : "json";
        return GroceryAppFactory.createGroceryApp(format, fileName);
    }

    public String getCategory() {
        return cmd.hasOption("c") ? cmd.getOptionValue("c") : "default";
    }

}