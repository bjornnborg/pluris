package br.com.six2six.pluris.config;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import br.com.six2six.pluris.MessageParameters;
import br.com.six2six.pluris.PluralDefinitions;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class JsonUtil {

    public static List<MessageParameters> read(InputStream is) {
        ConfigurationHolder configuration = (ConfigurationHolder) getJsonXstream().fromXML(is);
        return configuration.getMessageParameters();
    }
    
    public static List<MessageParameters> read(String jsonString) {
        ConfigurationHolder configuration = (ConfigurationHolder) getJsonXstream().fromXML(new StringReader(jsonString));
        return configuration.getMessageParameters();        
    }
    
    public static String write(MessageParameters... messageParameters) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(baos, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid charset");
        }
        getJsonXstream().toXML(buildConfigurationHolder(messageParameters), writer);
        return new String(baos.toByteArray());
    }

    private static ConfigurationHolder buildConfigurationHolder(MessageParameters... messageParameters) {
        ConfigurationHolder messagesHolder = new ConfigurationHolder();
        messagesHolder.getMessageParameters().addAll(Arrays.asList(messageParameters));
        return messagesHolder;
    }
    
    private static XStream getJsonXstream() {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("config", ConfigurationHolder.class);
        xstream.addImplicitCollection(ConfigurationHolder.class, "messageParameters");
        xstream.alias("messages", MessageParameters.class);
        xstream.addImplicitCollection(MessageParameters.class, "plurals");
        xstream.alias("plurals", PluralDefinitions.class);
        return xstream;
    }
}
