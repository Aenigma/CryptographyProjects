
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * Main class
 *
 * @author Seyed Kevin Raoofi
 */
public class Poly {

    /**
     * Reads the file, input.txt in the current directory, reads in the
     * polynomials, and writes the sum, difference, and product of all the
     * polynomials in the file to the output.txt
     *
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        final Path input = Paths.get("input.txt");
        final Path output = Paths.get("output.txt");

        final List<Polynomial> polynomials = Polynomial.generateFrom(Files
                .readAllLines(input));

        // 1. we iterate through the add, sub, and mult bifunctions
        // 2. for each one, we apply it to the list of polynomials
        // 3. the results for each bifunction is then mapped by its outputFormat
        //    function
        // 4. all the formatted outputs are then collected as a list
        // 5. the list is then wrtitten to the output file
        Files.write(output,
                Arrays.<BinaryOperator<Polynomial>>asList(
                        Polynomial::add,
                        Polynomial::sub,
                        Polynomial::mult)
                .stream()
                .map(reducer -> polynomials.stream()
                        .reduce(reducer)
                        .map(Polynomial::outputFormat)
                        .get())
                .collect(Collectors.toList()));
    }
}
