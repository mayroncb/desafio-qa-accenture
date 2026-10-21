package br.com.accenture.desafioqa.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner dedicado à Parte 2 do Desafio (Automação Frontend - Selenium WebDriver + Page Object Model).
 * Executa os 5 fluxos visuais + Bônus de 12 registros dinâmicos.
 * Comando: mvn test -Dtest=RunParte2FrontendTest
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/parte2_frontend")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.accenture.desafioqa.hooks,br.com.accenture.desafioqa.parte2_frontend.steps")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@parte2")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, summary, html:target/cucumber-reports/parte2-frontend-report.html, json:target/cucumber-reports/parte2-frontend.json")
public class RunParte2FrontendTest {
}

