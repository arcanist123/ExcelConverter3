package excel.aligner;

import excel.adapters.GiveSourceStream;
import excel.adapters.SendSourceStream;

public class Aligner {
    private GiveSourceStream source;
    private SendSourceStream target;

    public Aligner(GiveSourceStream source, SendSourceStream target) {
        this.source = source;
        this.target = target;
    }

    public void AlignSource() {

    }

}
