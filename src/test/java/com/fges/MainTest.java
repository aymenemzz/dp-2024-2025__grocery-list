package com.fges;

import com.fges.adapters.in.cli.facade.CLIApplicationFacade;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void should_delegateToFacadeAndReturnExitCode() {
        String[] args = {"list", "-s", "test.json"};

        try (MockedConstruction<CLIApplicationFacade> mocked = mockConstruction(CLIApplicationFacade.class,
                (mock, context) -> when(mock.run()).thenReturn(42))) {

            int result = Main.exec(args);

            assertThat(result).isEqualTo(42);
            verify(mocked.constructed().get(0)).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
