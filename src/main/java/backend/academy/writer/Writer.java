package backend.academy.writer;

public interface Writer {

    void printStartInfo();

    void printFailedInputParams();

    void printStartRendering();

    void print(String mes);
}
