package com.cn.tf.reflection;

import com.cn.tf.Student;

/**
 * 获取class对象的三种方式
 */
public class GetClass {
    public static void main(String[] args) {
        //第一种方式获取Class对象
        Student student = new Student();
        Class s =student.getClass();
        System.out.println(s.getName());

        //第二种方式获取Class对象
        Class stuClass2 = Student.class;
        System.out.println(s == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个
        //第三种方式获取Class对象(常用)
        try {
            Class stuClass3 = Class.forName("com.cn.tf.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            System.out.println(stuClass3 == stuClass2);//判断三种方式是否获取的是同一个Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
