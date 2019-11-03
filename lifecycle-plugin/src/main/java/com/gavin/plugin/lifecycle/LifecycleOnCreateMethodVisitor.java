package com.gavin.plugin.lifecycle;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author gavin
 * @date 2019/2/19
 * ASM生成代码，如果有引用别的类，要先对别的类进行转换先
 *
 */
public class LifecycleOnCreateMethodVisitor extends BaseMethodVisitor {
    private  String path;
    private int index=0;
private boolean isInject = false;

    public LifecycleOnCreateMethodVisitor(MethodVisitor mv,String path) {
        super(mv);
        this.path=path;
    }
    public LifecycleOnCreateMethodVisitor(MethodVisitor mv) {
        super(mv);
    }
//访问方法上的注释,通过注解可以定位这个方法
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//        Lcom/gavin/asmdemo/anno/Cost;
        System.out.println("visitAnnotation method desc :"+desc+
                "  path: "+path);
        //生成代码
            if(null!=path&&!path.equals("")){
             geneteCode();
            }


        return super.visitAnnotation(desc, visible);
    }

    /**
     * 生成字节码，但是只能通过反射调用
     */
    private void geneteCode() {
        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "com/gavin/asmdemo/anno/Example", null, "java/lang/Object", null);

        cw.visitSource("Example.java", null);

        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(2, l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/gavin/asmdemo/anno/Example;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(4, l0);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Hello world!");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(5, l1);
            mv.visitInsn(Opcodes.RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l2, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();
        byte[] code = cw.toByteArray();
        //将修改的class文件写入输出目录
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(
                    path+ File.separator + "Example.class");
        fos.write(code);
        fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //根据字节码的差异性在第2个JumpInsn插入以下代码
    @Override
    public void visitJumpInsn(int opcode, Label label) {
        System.out.println("visitJumpInsn:"+opcode+"  label:"+label.info+"  "+label.toString());
        super.visitJumpInsn(opcode, label);
        if(++index==2){
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/gavin/asmdemo/BBB", "add", "()Z", false);
        mv.visitJumpInsn(opcode, label);
        }
    }

    @Override
    public void visitCode() {
        System.out.println("visitCode");


        //方法执行前插入
//        mv.visitLdcInsn("TAG");
//        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
//        mv.visitInsn(Opcodes.DUP);
//        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//        mv.visitLdcInsn("-------> onCreate : ");
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitVarInsn(Opcodes.ALOAD, 0);
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//        mv.visitInsn(Opcodes.POP);
//        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//        mv.visitLdcInsn("========start=========");
//        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//        mv.visitInsn(Opcodes.RETURN);
        super.visitCode();


    }

    //每执行一个指令前都会调用,对应于字节码的 visitInsn(POP);中括号内的值
    @Override
    public void visitInsn(int opcode) {
        System.out.println("opcode: "+opcode);

        if (opcode == Opcodes.RETURN){

        }
        //方法执行后插入
        /*if (opcode == Opcodes.RETURN) {
            mv.visitLdcInsn("TAG");
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("-------> onCreate : end ：");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(Opcodes.POP);
        }*/
        super.visitInsn(opcode);
    }
}
