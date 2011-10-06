package br.com.six2six.pluris;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MessageParameters {
    private Locale locale;
    private String messageKey;
    private String messagePattern;
    private List<PluralDefinitions> plurals;
    
    @Override
    public String toString() {
        return locale.toString() + " : " + messageKey + " : " + messagePattern;
    }

    private MessageParameters(Locale locale, String messageKey, String messagePattern, List<PluralDefinitions> plurals) {
        this.locale = locale;
        this.messageKey = messageKey;
        this.messagePattern = messagePattern;
        this.plurals = plurals;
    }

    public static MessageParameters of(Locale locale, String messageKey, String messagePattern, PluralDefinitions... plurals) {
        List<PluralDefinitions> helper = Arrays.asList(plurals);
        return new MessageParameters(locale, messageKey, messagePattern, helper);
    }
    
    public static MessageParameters of(String messageKey, String messagePattern, PluralDefinitions... plurals) {
        List<PluralDefinitions> helper = Arrays.asList(plurals);
        return new MessageParameters(Locale.getDefault(), messageKey, messagePattern, helper);
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getMessagePattern() {
        return messagePattern;
    }

    public List<PluralDefinitions> getPlurals() {
        return plurals;
    }

    public Locale getLocale() {
        return locale;
    }

}
