package Pattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Executable {
    void execute(ArrayList<String> commandArgs) throws IOException;
}
