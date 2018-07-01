package View;

import java.util.List;

public interface ProjectChangedListener {
    public void sampleAdded(String sampleName, String fastaFileName, String gffFileName, List<String> readHeader);
}
