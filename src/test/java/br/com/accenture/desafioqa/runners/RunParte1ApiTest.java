package br.com.accenture.desafioqa.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runner dedicado à Parte 1 do Desafio (Automação de API - BookStore e Account).
 * Executa todos os steps de forma contínua e em uma única execução.
 * Comando: mvn test -Dtest=RunParte1ApiTest
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/parte1_api")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.accenture.desafioqa.hooks,br.com.accenture.desafioqa.parte1_api.steps")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@parte1")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, summary, html:target/cucumber-reports/parte1-api-report.html, json:target/cucumber-reports/parte1-api.json")
public class RunParte1ApiTest {
}

