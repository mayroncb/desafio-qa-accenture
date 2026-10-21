package br.com.accenture.desafioqa.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gerenciador centralizado de configurações do framework de automação.
 * Permite sobrescrita de propriedades via argumentos de linha de comando (-Dchave=valor).
 */
public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                PROPERTIES.load(input);
            } else {
                throw new IllegalStateException("Arquivo config.properties não encontrado no classpath.");
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Falha ao carregar config.properties: " + e.getMessage());
        }
    }

    private ConfigManager() {
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue.trim();
        }
        return PROPERTIES.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        return (value != null && !value.isBlank()) ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null && !value.isBlank()) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://demoqa.com");
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url", "https://demoqa.com");
    }

    public static String getUploadFilePath() {
        return getProperty("upload.file.path", "src/test/resources/files/arquivo_upload_desafio.txt");
    }
}

