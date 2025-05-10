package com.fges.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class SystemInfo {
    LocalDate date = LocalDate.now();
    String osName = System.getProperty("os.name");
    String javaVersion = System.getProperty("java.version");

    @Override
    public String toString() {
        return String.format("""
        Today's date: %s
        Operating System: %s
        Java version: %s
        """,
                date,
                osName,
                javaVersion
        );
    }
}
