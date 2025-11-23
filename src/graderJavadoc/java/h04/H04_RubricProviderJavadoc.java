package h04;

import org.sourcegrade.jagr.api.rubric.*;

public class H04_RubricProviderJavadoc implements RubricProvider {

    @Override
    public Rubric getRubric() {
        return Rubric.builder()
            .title("H4 | Schere-Stein-Papier Turnier")
            .addChildCriteria(Criterion.builder()
                .shortDescription("Documentation")
                .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef.ofMethod(() -> JavadocExtractor.class.getDeclaredMethod("extractJavadoc")))
                    .pointsFailedMin()
                    .pointsPassedMax()
                    .build())
                .build())
            .build();
    }
}
