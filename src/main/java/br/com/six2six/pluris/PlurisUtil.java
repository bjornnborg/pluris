package br.com.six2six.pluris;

import java.io.IOException;
import java.io.InputStream;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.six2six.pluris.config.JsonUtil;

public class PlurisUtil {

    private static final String LAST_LOCALE_PARAM = "_[a-zA-Z]*$";
    private static final Pattern FIND_LOCALE_PARAMS = Pattern.compile(LAST_LOCALE_PARAM);

    private static Map<String, List<MessageParameters>> messages = new HashMap<String, List<MessageParameters>>();
    private static final Logger logger = Logger.getLogger(PlurisUtil.class.getPackage().toString());
    
    static {
        Properties prop = new Properties();
        try {
            InputStream inputStream = PlurisUtil.class.getResourceAsStream("/pluris.properties");
            if (inputStream != null) {
                logger.info("Using pluris.properties.");
                prop.load(inputStream);
            } else {
                logger.info("Using default configuration.");
            }
        } catch (IOException e) {
            logger.info("Problem while loading pluris.properties. Will use default configuration.");
        }
        
        List<String> filesToLoad = getFilesToLoad(prop.getProperty("files.to.load"));
        for (String fileToLoad : filesToLoad) {
            InputStream inputStream = PlurisUtil.class.getResourceAsStream(fileToLoad);
            if (inputStream != null) {
                loadMessagesFromFile(inputStream);
            } else {
                logger.info("File " + fileToLoad + " not found. Check 'files.to.load' property in pluris.properties.");
            }
        }
        
    }
    
    public static String getMessage(Locale locale, String key, Object... args) {
        String mensagem = null;
        //loadMesssgesIfNecessary(locale);
        List<MessageParameters> messagesForKey = messages.get(key);        
        
        if (messagesForKey != null) {
            MessageParameters message = findMessageForLocale(locale, messagesForKey);
            if (message != null) {
                MessageFormat format = new MessageFormat(message.getMessagePattern());
                for (int i = 0; i < message.getPlurals().size(); i++) {
                    ChoiceFormat formatoMensagem = new ChoiceFormat(new double[]{0, 1, 2}, message.getPlurals().get(i).toArray());
                    format.setFormatByArgumentIndex(i, formatoMensagem);
                }
                mensagem = format.format(args);
            }
        }
        return mensagem;
    }
    
    private static List<String> getFilesToLoad(String filesInProperties) {
        List<String> filesToLoad = new ArrayList<String>();
        if (filesInProperties == null) {
            filesToLoad.add("/pluris.json");
        } else {
            for (String fileName : Arrays.asList(filesInProperties.split(","))) {
                filesToLoad.add(fileName.trim());                
            }
        }
        return filesToLoad;
    }

    public static String getMessage(String key, Object... args) {
        return getMessage(Locale.getDefault(), key, args);
    }
    
    private static MessageParameters findMessageForLocale(Locale locale, List<MessageParameters> messagesForKey) {
        MessageParameters found = null;
        if (locale != null) {
            for (MessageParameters messageParameters : messagesForKey) {
                if (messageParameters.getLocale().equals(locale)) {
                    found = messageParameters;
                    break;
                }
            }
            if (found == null) {
                found = findMessageForLocale(reduceLocale(locale), messagesForKey);
            }            
        }
        return found;
    }

    private static Locale reduceLocale(Locale locale) {
        Locale reduced = null;
        if (locale != null) {
            String localeString = locale.toString();
            Matcher matcher = FIND_LOCALE_PARAMS.matcher(localeString);
            if (matcher.find()) {
                localeString = localeString.replaceAll(LAST_LOCALE_PARAM, "");
                reduced = buildLocale(localeString);
            }
        }
        return reduced;
    }

    private static Locale buildLocale(String localeString) {
        Locale locale = null;
        String[] args = localeString.split("_");
        if (args.length == 3) {
            locale = new Locale(args[0], args[1], args[2]);
        } else if (args.length == 2) {
            locale = new Locale(args[0], args[1]);            
        } else if (args.length == 1) {
            locale = new Locale(args[0]);            
        }
        return locale;
    }
    
    private static void loadMessagesFromFile(InputStream stream) {
        List<MessageParameters> messageParameters = JsonUtil.read(stream);
        for (MessageParameters message : messageParameters) {
            List<MessageParameters> messagesForKey = messages.get(message.getMessageKey());
            if (messagesForKey == null) {
                messagesForKey = new ArrayList<MessageParameters>();
                messages.put(message.getMessageKey(), messagesForKey);
            }
            messagesForKey.add(message);
        }       
    }
    
}
