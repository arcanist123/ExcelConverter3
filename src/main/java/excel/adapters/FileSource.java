package excel.adapters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSource implements GiveSourceStream {
    private String fileName;

    public FileSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public byte[] readSource() throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllBytes(path);

    }

}
