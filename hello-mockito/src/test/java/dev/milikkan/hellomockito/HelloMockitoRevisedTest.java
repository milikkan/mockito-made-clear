package dev.milikkan.hellomockito;

import dev.milikkan.hellomockito.HelloMockitoRevised;
import dev.milikkan.hellomockito.Person;
import dev.milikkan.hellomockito.PersonRepository;
import dev.milikkan.hellomockito.TranslationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelloMockitoRevisedTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TranslationService translationService;

    @InjectMocks
    private HelloMockitoRevised helloMockitoRevised;

    @Test
    @DisplayName("Greet Admiral Hopper")
    void greetAPersonThatExists() {
        // set expectations on mocks
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper",
                        LocalDate.of(1906, Month.DECEMBER, 9))));
        when(translationService
                .translate("Hello, Grace, from Mockito!", "en", "en"))
                .thenReturn("Hello, Grace, from Mockito!");

        // test greet method
        String greeting = helloMockitoRevised.greet(1, "en", "en");
        assertEquals("Hello, Grace, from Mockito!", greeting);

        // verify the methods are called once, in the right order
        InOrder inOrder = Mockito.inOrder(personRepository, translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
    }

    @Test
    @DisplayName("Greet a person not in the database")
    void greetAPersonThatDoesNotExist() {
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        when(translationService.translate("Hello, World, from Mockito!", "en", "en"))
                .thenReturn("Hello, World, from Mockito!");

        String greeting = helloMockitoRevised.greet(100, "en", "en");
        assertEquals("Hello, World, from Mockito!", greeting);

        InOrder inOrder = Mockito.inOrder(personRepository, translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
    }

}