package br.com.six2six.pluris;

import junit.framework.Assert;

import org.junit.Test;

public class PluralDefinitionsTest {

    
    @Test
    public void pluralizeZeroOccurs() {
        PluralDefinitions pd = new PluralDefinitions("car", "cars");
        Assert.assertEquals(pd.getZeroOccurs(), pd.getManyOccurs());
    }
    
    @Test
    public void doNotPluralizeZeroOccurs() {
        String zeroOccurs = "zero";
        PluralDefinitions pd = new PluralDefinitions("zero", "car", "cars");
        Assert.assertEquals(zeroOccurs, pd.getZeroOccurs());
    }
    
    @Test
    public void createNewFromStringsPluralizeZeroOccurs() {
        PluralDefinitions pd  = PluralDefinitions.of("car", "cars");
        Assert.assertEquals(pd.getZeroOccurs(), pd.getManyOccurs());
        Assert.assertEquals("car", pd.getOneOccur());
    }
    
    @Test
    public void createNewFromStringsDoNotPluralizeZeroOccurs() {
        String zeroOccurs = "zero";
        PluralDefinitions pd  = PluralDefinitions.of("zero", "car", "cars");
        Assert.assertEquals(zeroOccurs, pd.getZeroOccurs());
    }    
}
