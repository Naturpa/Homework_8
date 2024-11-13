package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;

public class FileSystemExample {
    public static void main(String[] args) {
        String surname = "Turukina"; // Ваша фамилия
        String name = "Anastasia"; // Ваше имя

        try {
            // a. Создаем директорию <surname>
            Path directory = Paths.get(surname);
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
                System.out.println("Created directory: " + directory);
            } else {
                System.out.println("Directory already exists: " + directory);
            }

            // b. Создаем файл <name>
            Path file = Paths.get(surname, name + ".txt");
            if (!Files.exists(file)) {
                Files.createFile(file);
                System.out.println("Created file: " + file);
            } else {
                System.out.println("File already exists: " + file);
            }

            // c. Создаем вложенные директории
            Path dir1 = Files.createDirectories(Paths.get(surname, "dir1"));
            Path dir2 = Files.createDirectories(Paths.get(surname, "dir2"));
            Path dir3 = Files.createDirectories(Paths.get(surname, "dir3"));
            System.out.println("Created nested directories: " + dir1 + ", " + dir2 + ", " + dir3);

            // Копируем файл в новые директории
            Files.copy(file, dir1.resolve(name + ".txt"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file, dir2.resolve(name + ".txt"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(file, dir3.resolve(name + ".txt"), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied file to dir1, dir2, and dir3");

            // d. Создаем файл file1 в dir1
            Path file1 = Paths.get(dir1.toString(), "file1.txt");
            if (!Files.exists(file1)) {
                Files.createFile(file1);
                System.out.println("Created file: " + file1);
            } else {
                System.out.println("File already exists: " + file1);
            }

            // e. Создаем файл file2 в dir2
            Path file2 = Paths.get(dir2.toString(), "file2.txt");
            if (!Files.exists(file2)) {
                Files.createFile(file2);
                System.out.println("Created file: " + file2);
            } else {
                System.out.println("File already exists: " + file2);
            }

            // f. Рекурсивный обход директории
            System.out.println("Contents of directory " + surname + ":");
            Files.walk(directory).forEach(path -> {
                if (Files.isDirectory(path)) {
                    System.out.println("D " + path.getFileName()); // Пометка для директорий
                } else {
                    System.out.println("F " + path.getFileName()); // Пометка для файлов
                }
            });

            // g. Удаляем директорию dir1 со всем ее содержимым
            Files.walk(dir1)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path); // Удаляем каждый файл и директорию
                            System.out.println("Deleted: " + path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            // Удаляем саму директорию dir1
            Files.delete(dir1);
            System.out.println("Deleted directory: " + dir1);

        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }
}
