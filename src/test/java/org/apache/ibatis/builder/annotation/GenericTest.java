package org.apache.ibatis.builder.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 关于泛型的桥接方法测试，
 * bridge在1.5以后的版本中出现，为了兼容以前的1.5版本将，泛型的方法，生成一个阴影，来调用。
 * 就是说一个子类在继承（或实现）一个父类（或接口）的泛型方法时，在子类中明确指定了泛型类型，
 * 那么在编译时编译器会自动生成桥接方法.
 * 1.7与1.8又不一样！！！
 * （当然还有其他情况会生成桥接方法，这里只是列举了其中一种情况）。
 */
public class GenericTest
{
    @Target(METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BogusMyBatisSqlAnnotation {
    }

    public class MyModel {
    }

    public interface BaseMapper<M> {
        public void insert(M model);
        default void insert(){
            System.out.println("1");
        }
    }

    public interface MyModelMapper extends BaseMapper<MyModel> {
        @BogusMyBatisSqlAnnotation
        @Override
        public void insert(MyModel model);
    }

    public static void main( String[] args )
    {
        //这里其实会打印出两个方法，
        printMethodList(MyModelMapper.class);
        System.out.println(Object.class.isAssignableFrom(Array.class));
        System.out.println(Array.class.getSuperclass());
        System.out.println(Arrays.toString(ArrayList.class.getInterfaces()));


    }

    /**
     *
     *
     * <note>1.7版本 </note>
     * <p>
     *     MyModelMapper
     *   public abstract void GenericTest$MyModelMapper.insert(GenericTest$MyModel)
     *     isSynthetic = false, isBridge = false
     *     Annotation = @GenericTest$BogusMyBatisSqlAnnotation()
     *   public abstract void GenericTest$BaseMapper.insert(java.lang.Object)
     *     isSynthetic = false, isBridge = false
     *     NO ANNOTATIONS!
     * </p>
     * 请注意，bridge方法存在，但它位于基接口类文件中，并且未进行注释。
     *
     * JDK 1.8版本:
     * <p>
     *   public abstract void org.apache.ibatis.builder.annotation.GenericTest$MyModelMapper.insert(org.apache.ibatis.builder.annotation.GenericTest$MyModel)
     *          isSynthetic = false, isBridge = false
     *          isDefault = false
     *          Annotation = @org.apache.ibatis.builder.annotation.GenericTest$BogusMyBatisSqlAnnotation()
     *   public default void org.apache.ibatis.builder.annotation.GenericTest$MyModelMapper.insert(java.lang.Object)
     *           isSynthetic = true, isBridge = true
     *           isDefault = true
     *           Annotation = @org.apache.ibatis.builder.annotation.GenericTest$BogusMyBatisSqlAnnotation()
     * </p>
     * 合成桥接方法现在是一个默认方法，位于子接口类文件中，并且与声明的接口方法具有相同的名称和注释。
     * 这导致MyBatis 3.2.7抛出上面列出的异常。
     *
     * @param clazz
     */
    public static void printMethodList(Class<?> clazz) {
        System.out.println();
        System.out.println(clazz.getSimpleName());
        Method[] interfaceMethods = clazz.getMethods();
        for (Method method : interfaceMethods) {
            System.out.println("  " + method);
            System.out.println("    isSynthetic = " + method.isSynthetic() + ", isBridge = " + method.isBridge());
            System.out.println("    isDefault = " + method.isDefault() );
            if (method.getAnnotations().length > 0) {
                for (Annotation annotation : method.getAnnotations()) {
                    System.out.println("    Annotation = " + annotation);
                }
            } else {
                System.out.println("    NO ANNOTATIONS!");
            }
        }
    }
}