package dev.milikkan.hellomockito;

public interface TranslationService {
    default String translate(String text,
                             String sourceLang,
                             String targetLang) {
        return text;
    }
}
