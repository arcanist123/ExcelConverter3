package excel.adapters;

import java.io.IOException;

public interface SendSourceStream {
    public void saveSource(byte[] source) throws IOException;
}
