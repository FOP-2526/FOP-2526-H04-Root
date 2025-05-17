package h04.participants;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Each {@link Participant} has a species to indicate how they play against other {@link Participant}s.
 * Following the typical rules:
 * Scissors beats paper,
 * rock beats scissors
 * and paper beats rock.
 */
@DoNotTouch
public enum Species {
    ROCK, PAPER, SCISSORS
}
