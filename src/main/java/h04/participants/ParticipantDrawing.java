package h04.participants;

import fopbot.*;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Comparator;
import java.util.List;

import java.awt.*;

@DoNotTouch
public class ParticipantDrawing extends SvgBasedDrawing<Participant> {

    private static final List<Class<? extends Participant>> classes = List.of(Rock.class, Paper.class, Scissors.class);

    public ParticipantDrawing() {
        super(classes.size());
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Participant> context) {
        for (int i = 0; i < classes.size(); i++) {
            var path = classes.get(i).getSimpleName() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(i, image);
        }
    }

    @Override
    protected Image getCurrentDrawingImage(Participant entity) {
        var index = classes.indexOf(entity.getClass());
        return getImage(index);
    }

    public static void register() {
        World.getGlobalWorld().setDrawingRegistry(DrawingRegistry.
            builder(DrawingRegistry.DEFAULT)
            .add(Participant.class, new ParticipantDrawing())
            .build(Comparator.comparingInt(x -> 0)));
    }
}
