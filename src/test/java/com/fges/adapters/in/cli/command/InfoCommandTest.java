package com.fges.adapters.in.cli.command;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class InfoCommandTest {

    @Test
    void should_printSystemInfoAndReturnZero() {
        // Redirect System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            // Act
            InfoCommand command = new InfoCommand();
            int result = command.execute();

            // Assert
            String output = outContent.toString();
            assertThat(output).contains("Operating System").contains("Java version").contains("Today's date");
            assertThat(result).isEqualTo(0);
        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }
}
