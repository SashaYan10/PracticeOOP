import java.util.ArrayList;
import java.util.List;

public class Collection {
    private List<BinaryResult> results;

    public Collection() {
        results = new ArrayList<>();
    }

    public void addResult(BinaryResult result) {
        results.add(result);
    }

    public List<BinaryResult> getResults() {
        return results;
    }
}
