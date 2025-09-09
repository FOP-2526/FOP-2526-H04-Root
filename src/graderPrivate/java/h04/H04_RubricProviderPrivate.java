package h04;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H04_RubricProviderPrivate implements RubricProvider {

    private static final Criterion H_1 = Criterion.builder()
        .shortDescription("H4.1 | Die Teilnehmer")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1.1.1 | Schere")
                .addChildCriteria(
                    criterion(
                        "Die Klasse Scissors ist korrekt deklariert ink. dem Erben aus Participant",
                        (JUnitTestRef) null),
                    criterion(
                        "Der Konstruktor der Klasse Scissors ist korrekt deklariert und ruft den Super-Konstruktor auf.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode doVictoryDance ist korrekt implementiert.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode fight behandelt den Fall korrekt, dass die Schere gewinnt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode fight behandelt alle anderen Fälle korrekt",
                        (JUnitTestRef) null)
                )
                .build(),
            Criterion.builder()
                .shortDescription("H1.1.2 | Papier")
                .addChildCriteria(
                    criterion(
                        "Die Klasse Paper ist korrekt deklariert ink. dem Erben aus Participant und der Deklaration der Attribute.",
                        (JUnitTestRef) null),
                    criterion(
                        "Der Konstruktor der Klasse Scissors ist korrekt deklariert, initialisiert die Attribute korrekt und ruft den Super-Konstruktor auf.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode doVictoryDance ist korrekt implementiert.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode fight behandelt den Fall korrekt, dass das Papier gewinnt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode fight behandelt alle anderen Fälle korrekt",
                        (JUnitTestRef) null)
                )
                .build(),
            Criterion.builder()
                .shortDescription("H1.1.3 | Stein")
                .addChildCriteria(
                    criterion(
                        "Die Klasse Rock ist korrekt deklariert ink. dem Erben aus Participant und der Deklaration der Attribute.",
                        (JUnitTestRef) null),
                    criterion(
                        "Der Konstruktor der Klasse Scissors ist korrekt deklariert, initialisiert die Attribute korrekt und ruft den Super-Konstruktor auf.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode doVictoryDance ist korrekt implementiert.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode fight behandelt den Fall korrekt, dass der Stein gewinnt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode fight behandelt alle anderen Fälle korrekt",
                        (JUnitTestRef) null)
                )
                .build()
        )
        .build();

    private static final Criterion H_2 = Criterion.builder()
        .shortDescription("H4.2 | Das Turnier")
        .minPoints(0)
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1.2.1 | Zur nächsten Runde laufen")
                .addChildCriteria(
                    criterion(
                        "Die Methode ascend bewegt alle Teilnehmer nach oben und in keine andere Richtung.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Reihenfolge und Ausrichtungen der Teilnehmer nach Ablauf der Methode ascend ist korrekt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode moveUp bewegt alle Teilnehmer nach links und in keine andere Richtung.",
                        (JUnitTestRef) null),
                    criterion(
                        "Nach der Methode moveUp sind zwischen den einzelnen Teilnehmern, sowie der linken Wand keine Lücken.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Reihenfolge und Ausrichtungen der Teilnehmer nach Ablauf der Methode moveUp ist korrekt.",
                        (JUnitTestRef) null)
                )
                .build(),
            Criterion.builder()
                .shortDescription("H1.2.2 | Zur nächsten Runde laufen")
                .addChildCriteria(
                    criterion(
                        "Nach der Methode ist die Länge des Teilnehmer Arrays korrekt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Nach der Methode sind die Einträge des Teilnehmer Arrays, im Bezug auf dessen Typs, korrekt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Alle Teilnehmer befinden sich nach der Methode an der korrekten Position und haben die korrekte Ausrichtung",
                        (JUnitTestRef) null),
                    criterion(
                        "Es befinden sich nach der Methode keine Objekte in der Welt.",
                        (JUnitTestRef) null)
                )
                .build(),
            Criterion.builder()
                .shortDescription("H1.2.3 | Der Gegner neben einem")
                .addChildCriteria(
                    criterion(
                        "Die Methode getOpponent behandelt den Fall, dass der Gegner links ist, korrekt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode getOpponent behandelt den Fall, dass der Gegner recht ist, korrekt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Methode getOpponent behandelt den Fall korrekt, dass es keinen Gegner mehr gibt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Reihenfolge und Ausrichtungen der Teilnehmer nach Ablauf der Methode ist korrekt.",
                        (JUnitTestRef) null)
                )
                .build(),
            Criterion.builder()
                .shortDescription("H1.2.4 | Die Sieger finden")
                .addChildCriteria(
                    criterion(
                        "Die Länge des zurückgegeben Arrays wird immer korrekt berechnet.",
                        (JUnitTestRef) null),
                    criterion(
                        "Die Einträge des zurückgegeben Arrays werden immer korrekt berechnet.",
                        (JUnitTestRef) null)
                )
                .build(),
            Criterion.builder()
                .shortDescription("H1.2.5 | Eine Runde spielen")
                .addChildCriteria(
                    criterion(
                        "Die Aufrufe von determineVictors und arrangeParticipants sind korrekt.",
                        (JUnitTestRef) null),
                    criterion(
                        "Das Teilnehmer Array zeigt nach Ablauf der Methode korrekt auf die Sieger.",
                        (JUnitTestRef) null)
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
}
