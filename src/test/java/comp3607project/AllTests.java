package comp3607project;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    AppTest.class,
    ZipHandlerTest.class,
    JavaEvaluatorTest.class,
    ScoreCalculatorTest.class,
    ReportGeneratorTest.class,
    InheritanceStrategyTest.class,
    NamingConventionStrategyTest.class,
    StructureStrategyTest.class
    
})
public class AllTests {
    // This class remains empty; it is only used to run the suite of tests
}
