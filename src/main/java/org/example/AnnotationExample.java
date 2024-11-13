package org.example;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

// 1. Создаем аннотацию с целочисленным параметром
@Retention(RetentionPolicy.RUNTIME) // Аннотация будет доступна в рантайме
@interface Repeat {
    int value(); // Параметр для указания количества повторений
}

// 2. Класс с публичными, защищенными и приватными методами
class MyClass {

    // 2.1 Публичный метод (не аннотирован)
    public void publicMethod() {
        System.out.println("Public Method");
    }

    // 2.2 Защищенный метод с аннотацией
    @Repeat(value = 3) // Аннотируем метод с параметром 3
    protected void protectedMethod() {
        System.out.println("Protected Method");
    }

    // 2.3 Еще один защищенный метод
    @Repeat(value = 2) // Аннотируем метод с параметром 2
    protected void anotherProtectedMethod() {
        System.out.println("Another Protected Method");
    }

    // 2.4 Приватный метод с аннотацией
    @Repeat(value = 1) // Аннотируем метод с параметром 1
    private void privateMethod() {
        System.out.println("Private Method");
    }

    // 2.5 Еще один приватный метод
    private void anotherPrivateMethod() {
        System.out.println("Another Private Method");
    }
}

// 3. Класс для вызова аннотированных методов
public class AnnotationExample {
    public static void main(String[] args) {
        MyClass myClass = new MyClass(); // Создаем экземпляр MyClass
        Method[] methods = MyClass.class.getDeclaredMethods(); // Получаем все методы класса

        // 4. Проходим по всем методам класса
        for (Method method : methods) {
            // 4.1 Проверяем наличие аннотации
            if (method.isAnnotationPresent(Repeat.class)) {
                Repeat annotation = method.getAnnotation(Repeat.class); // Получаем аннотацию
                method.setAccessible(true); // Делаем приватный метод доступным
                // 4.2 Вызываем метод указанное количество раз
                for (int i = 0; i < annotation.value(); i++) {
                    try {
                        method.invoke(myClass); // Вызываем метод
                    } catch (Exception e) {
                        e.printStackTrace(); // Обработка исключений
                    }
                }
            }
        }
    }
}
