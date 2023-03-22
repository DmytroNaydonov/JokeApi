package config;

import factory.ConfigFactory;
import lombok.Data;

@Data
public class ConfigHolder {

    private final TestConfig config;

    public ConfigHolder(ConfigFactory configFactory) {
        this.config = configFactory.create();
    }
}
