package dev.milikkan.hellomockito;

import dev.milikkan.hellomockito.HelloMockito;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloMockitoTest {

    private HelloMockito helloMockito = new HelloMockito();

    @Test
    void greetPerson() {
        String greeting = helloMockito.greet("World");
        assertEquals("Hello, World, from Mockito!", greeting);
    }

}