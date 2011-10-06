package br.com.six2six.pluris.config;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

import br.com.six2six.pluris.MessageParameters;
import br.com.six2six.pluris.PluralDefinitions;

public class JsonTest {

    @Test
    public void writeJson() {
        MessageParameters fruits = MessageParameters.of(
                Locale.ENGLISH,
                "fruits.message", 
                "{0} and {1}", 
                PluralDefinitions.of("{0} apple", "{0} apples"), 
                PluralDefinitions.of("no pineapple", "{1} pineapple", "{1} pineapples")
        );
        MessageParameters frutas = MessageParameters.of(
                new Locale("pt", "BR"),
                "fruits.message", 
                "{0} e {1}", 
                PluralDefinitions.of("{0} maçã", "{0} maçãs"), 
                PluralDefinitions.of("nenhum abacaxi", "{1} abacaxi", "{1} abacaxis")
        );        
        MessageParameters vehicle = MessageParameters.of(
                Locale.US,
                "vehicles.message", 
                "{0} and {1}", 
                PluralDefinitions.of("{0} car", "{0} cars"), 
                PluralDefinitions.of("no bike", "{1} bike", "{1} bikes")
        );
        
        String json = this.loadFile();
        Assert.assertEquals(JsonUtil.write(fruits, frutas, vehicle), json);
    }    
    
    @Test
    public void readJson() {
        List<MessageParameters> messageParameters = JsonUtil.read(this.loadFile());
        Assert.assertNotNull(messageParameters);
        Assert.assertEquals(3, messageParameters.size());
        Assert.assertEquals("fruits.message", messageParameters.get(0).getMessageKey());
        Assert.assertEquals(Locale.ENGLISH, messageParameters.get(0).getLocale());
        Assert.assertEquals("vehicles.message", messageParameters.get(2).getMessageKey());
        Assert.assertEquals(2, messageParameters.get(0).getPlurals().size());
        Assert.assertEquals(2, messageParameters.get(1).getPlurals().size());
    }
    
    private String loadFile() {
        return this.readFile("/pluris.json");
    }
    
    private String readFile(String fileName) {
        StringBuffer stringBuffer = new StringBuffer("");
        Scanner scanner = this.getScanner(fileName); 
        while (scanner.hasNext()) {
            stringBuffer.append(readLineKeepingSeparator(scanner));
        }
        scanner.close();
        Matcher matcher = this.getRegex(stringBuffer);
        return matcher.replaceAll("");
    }

    private Matcher getRegex(StringBuffer stringBuffer) {
        Pattern pattern = Pattern.compile("\\s*[\\r\\n]+\\s*", Pattern.MULTILINE);
        return pattern.matcher(stringBuffer.toString());        
    }

    private String readLineKeepingSeparator(Scanner scanner){
        return scanner.next() + this.getLineSeparator();
    }

    private Scanner getScanner(String fileName) {
        Scanner scanner = new Scanner(JsonTest.class.getResourceAsStream(fileName));
        return scanner.useDelimiter(this.getLineSeparator());
    }

    private String getLineSeparator() {
        return System.getProperty("line.separator");
    }
    
}
