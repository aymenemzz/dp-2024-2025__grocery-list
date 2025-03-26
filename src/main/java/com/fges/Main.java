package com.fges;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.cli.facade.CLIApplicationFacade;

import java.io.IOException;

public class Main {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        return new CLIApplicationFacade(args).run();
    }
}
