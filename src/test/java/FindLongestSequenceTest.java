import io.invokegs.pta.ConnectionSolver;

import io.invokegs.pta.Main;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FindLongestSequenceTest {
    @Test
    public void testFindLongestSequence() {
        ConnectionSolver solver = new ConnectionSolver(2);

        InputStream resourceAsStream = FindLongestSequenceTest.class.getResourceAsStream("/source.txt");
        Objects.requireNonNull(resourceAsStream, "Resource not found");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
            List<String> fragments = reader.lines().collect(Collectors.toList());

            ConnectionSolver.Result result = solver.findLongestSequence(fragments);
            Main.printResult(result);

            assert result.fragments().size() == 67;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
