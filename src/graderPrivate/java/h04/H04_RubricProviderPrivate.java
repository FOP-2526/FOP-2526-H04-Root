package h04;

import h04.participants.PaperTests;
import h04.participants.RockTests;
import h04.participants.ScissorsTests;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H04_RubricProviderPrivate implements RubricProvider {

    private static final Criterion H_1 = Criterion.builder()
        .shortDescription("H4.1 | Die Teilnehmer")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H4.1.1 | Schere")
                .addChildCriteria(
                    criterion(
                        "Die Klasse Scissors ist korrekt deklariert ink. dem Erben aus Participant",
                        JUnitTestRef.ofMethod(() -> ScissorsTests.class.getDeclaredMethod("testDefinition"))),
                    criterion(
                        "Der Konstruktor der Klasse Scissors ist korrekt deklariert und ruft den Super-Konstruktor auf.",
                        JUnitTestRef.ofMethod(() -> ScissorsTests.class.getDeclaredMethod("testConstructor"))),
                    criterion(
                        "Die Methode doVictoryDance ist korrekt implementiert.",
                        JUnitTestRef.ofMethod(() -> ScissorsTests.class.getDeclaredMethod("testDoVictoryDance", int.class))),
                    criterion(
                        "Die Methode fight behandelt den Fall korrekt, dass die Schere gewinnt.",
                        JUnitTestRef.ofMethod(() -> ScissorsTests.class.getDeclaredMethod("testFightWin", ScissorsTests.Outcome.class)) ),
                    criterion(
                        "Die Methode fight behandelt alle anderen Fälle korrekt",
                        JUnitTestRef.ofMethod(() -> ScissorsTests.class.getDeclaredMethod("testFightOther", ScissorsTests.Outcome.class)))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4.1.2 | Papier")
                .addChildCriteria(
                    criterion(
                        "Die Klasse Paper ist korrekt deklariert ink. dem Erben aus Participant und der Deklaration der Attribute.",
                        JUnitTestRef.ofMethod(() -> PaperTests.class.getDeclaredMethod("testDefinition"))),
                    criterion(
                        "Der Konstruktor der Klasse Scissors ist korrekt deklariert, initialisiert die Attribute korrekt und ruft den Super-Konstruktor auf.",
                        JUnitTestRef.ofMethod(() -> PaperTests.class.getDeclaredMethod("testConstructor", int.class))),
                    criterion(
                        "Die Methode doVictoryDance ist korrekt implementiert.",
                        JUnitTestRef.ofMethod(() -> PaperTests.class.getDeclaredMethod("testDoVictoryDance", int.class))),
                    criterion(
                        "Die Methode fight behandelt den Fall korrekt, dass das Papier gewinnt.",
                        JUnitTestRef.ofMethod(() -> PaperTests.class.getDeclaredMethod("testFightWin", PaperTests.Outcome.class))),
                    criterion(
                        "Die Methode fight behandelt alle anderen Fälle korrekt",
                        JUnitTestRef.ofMethod(() -> PaperTests.class.getDeclaredMethod("testFightOther", PaperTests.Outcome.class)))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4.1.3 | Stein")
                .addChildCriteria(
                    criterion(
                        "Die Klasse Rock ist korrekt deklariert ink. dem Erben aus Participant und der Deklaration der Attribute.",
                        JUnitTestRef.ofMethod(() -> RockTests.class.getDeclaredMethod("testDefinition"))),
                    criterion(
                        "Der Konstruktor der Klasse Scissors ist korrekt deklariert, initialisiert die Attribute korrekt und ruft den Super-Konstruktor auf.",
                        JUnitTestRef.ofMethod(() -> RockTests.class.getDeclaredMethod("testConstructor"))),
                    criterion(
                        "Die Methode doVictoryDance ist korrekt implementiert.",
                        JUnitTestRef.ofMethod(() -> RockTests.class.getDeclaredMethod("testDoVictoryDance", int.class))),
                    criterion(
                        "Die Methode fight behandelt den Fall korrekt, dass der Stein gewinnt.",
                        JUnitTestRef.ofMethod(() -> RockTests.class.getDeclaredMethod("testFightWin", RockTests.Outcome.class))),
                    criterion(
                        "Die Methode fight behandelt alle anderen Fälle korrekt",
                        JUnitTestRef.ofMethod(() -> RockTests.class.getDeclaredMethod("testFightOther", RockTests.Outcome.class)))
                )
                .build()
        )
        .build();

    private static final Criterion H_2 = Criterion.builder()
        .shortDescription("H4.2 | Das Turnier")
        .minPoints(0)
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H4.2.1 | Zur nächsten Runde laufen")
                .addChildCriteria(
                    criterion(
                        "Die Methode ascend bewegt alle Teilnehmer nach oben und in keine andere Richtung.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testAscend_movesUp"))),
                    criterion(
                        "Die Reihenfolge und Ausrichtungen der Teilnehmer nach Ablauf der Methode ascend ist korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testAscend_orientation"))),
                    criterion(
                        "Die Methode moveUp bewegt alle Teilnehmer nach links und in keine andere Richtung.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testMoveUp_leftOnly"))),
                    criterion(
                        "Nach der Methode moveUp sind zwischen den einzelnen Teilnehmern, sowie der linken Wand keine Lücken.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testMoveUp_noSpaces"))),
                    criterion(
                        "Die Reihenfolge und Ausrichtungen der Teilnehmer nach Ablauf der Methode moveUp ist korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testMoveUp_orientation")))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4.2.2 | Zur nächsten Runde laufen")
                .addChildCriteria(
                    criterion(
                        "Nach der Methode ist die Länge des Teilnehmer-Arrays korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testPlaceLineUp_arrayLength"))),
                    criterion(
                        "Nach der Methode sind die Einträge des Teilnehmer-Arrays bezüglich der Typen korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testPlaceLineUp_participantTypes"))),
                    criterion(
                        "Alle Teilnehmer befinden sich nach der Methode an der korrekten Position und haben die korrekte Ausrichtung",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testPlaceLineUp_orientation"))),
                    criterion(
                        "Es befinden sich nach der Methode keine sonstigen Objekte in der Welt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testPlaceLineUp_callsReset")))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4.2.3 | Der Gegner neben einem")
                .addChildCriteria(
                    criterion(
                        "Die Methode getOpponent behandelt den Fall, dass der Gegner links ist, korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testGetOpponent_left"))),
                    criterion(
                        "Die Methode getOpponent behandelt den Fall, dass der Gegner rechts ist, korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testGetOpponent_right"))),
                    criterion(
                        "Die Methode getOpponent behandelt den Fall korrekt, dass es keinen Gegner mehr gibt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testGetOpponent_none"))),
                    criterion(
                        "Die Reihenfolge und Ausrichtungen der Teilnehmer nach Ablauf der Methode ist korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testGetOpponent_orientation")))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4.2.4 | Die Sieger finden")
                .addChildCriteria(
                    criterion(
                        "Die Länge des zurückgegeben Arrays wird immer korrekt berechnet.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testDetermineVictors_checkArrayLength", int.class))),
                    criterion(
                        "Die Einträge des zurückgegeben Arrays werden immer korrekt berechnet.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testDetermineVictors_checkArrayEntries", int.class)))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4.2.5 | Eine Runde spielen")
                .addChildCriteria(
                    criterion(
                        "Die Aufrufe von determineVictors und arrangeParticipants sind korrekt.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testDoRound_methodCalls"))),
                    criterion(
                        "Das Teilnehmer Array zeigt nach Ablauf der Methode korrekt auf die Sieger.",
                        JUnitTestRef.ofMethod(() -> RefereeTests.class.getDeclaredMethod("testDoRound_setField")))
                )
                .build()
        )
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H4 | Schere-Stein-Papier Turnier")
        .addChildCriteria(H_1, H_2)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        RubricProvider.super.configure(configuration);
        configuration.addTransformer(new AccessTransformer());
    }
}
