package excel.adapters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTarget implements SendSourceStream {
    private String fileName;

    public FileTarget(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void saveSource(byte[] source) throws IOException {
        Path textFilePath = Paths.get(this.fileName);
        Files.write(textFilePath, source);
    }

}
