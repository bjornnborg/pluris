package br.com.six2six.pluris;

import junit.framework.Assert;

import org.junit.Test;

public class MessageParameterTest {

    @Test
    public void createNewWithPluralStrings() {
        MessageParameters mp = MessageParameters.of(
                "some.message.key", 
                "{0} and {1}", 
                PluralDefinitions.of("apple", "apples"), 
                PluralDefinitions.of("no pineapple", "pineapple", "pineapples")
        );
        
        Assert.assertNotNull(mp.getPlurals());
        Assert.assertEquals(2, mp.getPlurals().size());
        Assert.assertEquals(mp.getPlurals().get(0).getZeroOccurs(), mp.getPlurals().get(0).getManyOccurs());
    }
}