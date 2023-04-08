package fr.univcotedazur.multiCredit.cucumber.Admin;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/Admin")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "fr.univcotedazur.multiCredit.cucumber.Admin")
public class AdminCucumberRunnerIT{
}
