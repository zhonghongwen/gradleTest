package com.gavin.plugin.lifecycle;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author gavin
 * @date 2019/2/18
 * lifecycle class visitor
 *
 *  visit（一次）-》visitSource（一次）-》visitOuterClass-》visitAnnotation-》（visitInnerClass或者visitField 和 visitMethod）
 *
 */



public class LifecycleClassVisitor extends ClassVisitor implements Opcodes {

    private  String path;
    private String mClassName;

    public LifecycleClassVisitor(ClassVisitor cv,String path) {
        super(Opcodes.ASM5, cv);
        this.path=path;
    }
    public LifecycleClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }


    /**
     * name
     * @param version
     * @param access
     * @param name  类名
     * @param signature
     * @param superName 父类名
     * @param interfaces 接口
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
       System.out.println("LifecycleClassVisitor : visit -----> started ClassName:" + name+"  "+superName);
       if(interfaces!=null){
           for (String anInterface : interfaces) {
               System.out.println("anInterface  :"+anInterface);
           }
       }
        this.mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    //不是方法上面的注解
//    @Override
//    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//        System.out.println("LifecycleClassVisitor : visitAnnotation : " + desc);
//        return super.visitAnnotation(desc, visible);
//    }
    /**
     * 修改变量
     *    //可修改修饰符access，名字name和参数desc
     *
     */

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("LifecycleClassVisitor : visitField : " + name +"     "+desc);
        return super.visitField(access, name, desc, signature, value);
    }

    /**
     *
     * 如果是删除这个方法的话，直接返回null即可
     *
     *
     * @param access 用来判断方法的修饰符
     * @param name 方法名
     * @param desc  方法签名，用来判断参数和返回值
     * @param signature
     * @param exceptions
     * @return
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("LifecycleClassVisitor : visitMethod : " + name);
        //可修改修饰符，名字和参数
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        //表示这个方法是public修饰的，判断接口
        if((access & ACC_PUBLIC)!=0){
            System.out.println("ACC_PUBLIC");
        }
        //直接进行处理,跳过构造方法
        if(!"<init>".equals(name)&&"say12345".equals(name)){
        return new LifecycleOnCreateMethodVisitor(mv,path);
        }
       // return null; 表示移除方法

        //匹配FragmentActivity
//        "android/support/v4/app/FragmentActivity"
        if ("com/gavin/asmdemo/Test".equals(this.mClassName)) {
            if ("getUseCaseCmd".equals(name) ) {
                //处理onCreate
                System.out.println("LifecycleClassVisitor : hook method name ----> " + name);
                return new LifecycleOnCreateMethodVisitor(mv);
            }
//            else if ("onDestroy".equals(name)) {
//                //处理onDestroy
//                System.out.println("LifecycleClassVisitor : change method ----> " + name);
//                return new LifecycleOnDestroyMethodVisitor(mv);
//            }
        }else if ("android/support/v4/app/FragmentActivity".equals(this.mClassName)) {
            if ("onDestroy".equals(name) ) {
                //处理onCreate
//                System.out.println("LifecycleClassVisitor : change method ----> " + name);
                return new LifecycleOnDestroyMethodVisitor(mv);
            }
//            else if ("onDestroy".equals(name)) {
//                //处理onDestroy
//                System.out.println("LifecycleClassVisitor : change method ----> " + name);
//                return new LifecycleOnDestroyMethodVisitor(mv);
//            }
        }
        return mv;

    }

    @Override
    public void visitEnd() {
        System.out.println("LifecycleClassVisitor : visit -----> end");
        super.visitEnd();
    }
}
