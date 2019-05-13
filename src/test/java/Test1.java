import com.github.tomokinakamaru.taskchain.SeedTask;
import com.github.tomokinakamaru.taskchain.Task;
import com.github.tomokinakamaru.taskchain.TaskChainException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

final class Test1 {

  private static final List<Integer> result = new ArrayList<>();

  @Test
  void main() throws TaskChainException {
    new FirstTask().start();
    assert result.equals(result.stream().sorted().collect(Collectors.toList()));
  }

  private static final class FirstTask extends SeedTask {

    @Override
    protected void main() {
      for (int i = 0; i < 100; i++) {
        submit(new EmptyTask(i));
      }
    }

    @Override
    public int getNumberOfThreads() {
      return 1;
    }
  }

  private static final class EmptyTask extends Task {

    private final int number;

    private EmptyTask(int number) {
      this.number = number;
    }

    @Override
    protected void main() {
      result.add(number);
    }
  }
}
