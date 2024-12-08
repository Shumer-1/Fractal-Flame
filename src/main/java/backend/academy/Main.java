package backend.academy;

import backend.academy.generateFractalFlame.FlameGenerator;
import backend.academy.generateFractalFlame.MultiThreadFrameGenerator;
import backend.academy.input.ConsoleInputReader;
import backend.academy.input.InputReader;
import backend.academy.session.Session;
import backend.academy.writer.ConsoleWriter;
import backend.academy.writer.Writer;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        int threadCount = Runtime.getRuntime().availableProcessors();
        FlameGenerator multiThreading = new MultiThreadFrameGenerator(new Random(System.currentTimeMillis()),
            threadCount);

        Writer writer = new ConsoleWriter(new PrintStream(System.out));
        InputReader inputReader = new ConsoleInputReader(new Scanner(System.in));
        Session session = new Session(multiThreading, writer, inputReader);

        session.startSession();
    }
}
