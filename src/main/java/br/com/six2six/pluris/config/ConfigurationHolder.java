package br.com.six2six.pluris.config;

import java.util.ArrayList;
import java.util.List;

import br.com.six2six.pluris.MessageParameters;

public class ConfigurationHolder {
    private List<MessageParameters> messageParameters = new ArrayList<MessageParameters>();

    public List<MessageParameters> getMessageParameters() {
        return messageParameters;
    }

}
