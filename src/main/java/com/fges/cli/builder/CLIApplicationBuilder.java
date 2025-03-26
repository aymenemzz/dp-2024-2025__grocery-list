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
        return this;
    }

    public CLIApplicationBuilder parseArguments() throws ParseException {
        CommandLineParser parser = new DefaultParser();
        this.cmd = parser.parse(cliOptions, args);
        return this;
    }

    public GroceryListServiceImpl buildService() {
        String fileName = cmd.getOptionValue("s");
        return GroceryAppFactory.createGroceryApp(fileName);
    }
}