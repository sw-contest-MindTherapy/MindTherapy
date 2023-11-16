package com.mysite.sbb.chattt;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Person {
    private final String name;
    private final int age;
    private String address; //final로 선언을 안해서 자동으로 생성자가 생성되지는 않음.
}

class test {
    public static void main(String[] args) {
        // @RequiredArgsConstructor로 생성된 생성자를 사용하여 객체 생성
        Person person = new Person("김우성", 25);

        // 값 출력
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Address: " + person.getAddress());
    }
}
