package br.com.six2six.pluris;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

public class PlurisUtilTest {
    
    @Test
    public void getMessageWithExactFileNameForLocaleAndNotExactMessageForLocale() {
        String message = PlurisUtil.getMessage(new Locale("pt", "BR", "es"), "vehicles.message", 1, 0);
        Assert.assertEquals("1 carro e nenhuma bicicleta", message);
    }
    
    @Test
    public void getMessageWithNotExactFileNameForLocaleAndExactMessageForLocale() {
        String message = PlurisUtil.getMessage(new Locale("pt", "BR"), "fruits.message", 1, 0);
        Assert.assertEquals("1 maçã e nenhum abacaxi", message);
    }
    
    @Test
    public void getMessageWithNotExactFileNameForLocaleAndNotExactMessageForLocale() {
        String message = PlurisUtil.getMessage(new Locale("en", "US"), "fruits.message", 4, 2);
        Assert.assertEquals("4 apples and 2 pineapples", message);
    }    

}
