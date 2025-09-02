package h04;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class AccessTransformer implements ClassTransformer {

    @Override
    public String getName() {
        return "Referee AccessTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h04/Referee")) {
            reader.accept(new Visitor(writer), 0);
        } else {
            reader.accept(writer, 0);
        }
    }

    private class Visitor extends ClassVisitor {

        private Visitor(ClassVisitor cv) {
            super(Opcodes.ASM9, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return super.visitMethod(access & ~Opcodes.ACC_PRIVATE | Opcodes.ACC_PUBLIC, name, descriptor, signature, exceptions);
        }
    }
}
