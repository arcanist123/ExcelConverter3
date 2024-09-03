package excel.adapters;

import java.io.IOException;

public interface GiveSourceStream {
    public byte[] readSource() throws IOException;

}
