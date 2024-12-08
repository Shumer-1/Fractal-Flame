package backend.academy.writer;

import java.io.PrintStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsoleWriter implements Writer {
    private PrintStream printer;

    @Override
    public void printStartInfo() {
        printer.print("""
            Введите параметры изображения в формате width height (1920 1080).
            Введите количество итераций для генерации фрактала (рекомендованное число - 1500).
            Введите параметр симметрии (1 - без симметрии, 2 - два сектора и т.д.).
            После этого введите трансформации из списка:
            1. Диск
            2. Экспоненциальная
            3. Сердце
            4. Полярная
            5. Синусоидальная
            6. Вихрь
            7. Сферическая
            Вводить нужно номера трансформаций, в одну строчку (1 2 3 или 5 1).
            Результаты сохранятся как results/output.txt.
            """);
    }

    @Override
    public void printFailedInputParams() {
        printer.print("""
            Введенные параметры некорректные, повторите попытку.
            """);
    }

    @Override
    public void printStartRendering() {
        printer.print("""
            Параметры приняты.
            """);
    }

    @Override
    public void print(String mes) {
        printer.println(mes);
    }

}
