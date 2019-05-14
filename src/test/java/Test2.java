import com.github.tomokinakamaru.taskchain.MultiStepTask;
import com.github.tomokinakamaru.taskchain.SeedTask;
import com.github.tomokinakamaru.taskchain.TaskChainException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

final class Test2 {

  private static final List<Integer> result = new ArrayList<>();

  @Test
  void main() throws TaskChainException {
    new FirstTask().start();
    assert result.equals(Arrays.asList(1, 2, 3));
  }

  private static final class FirstTask extends SeedTask {

    @Override
    protected void main() {
      submit(new EmptyTask());
    }
  }

  public static final class EmptyTask extends MultiStepTask {

    public void main1() {
      result.add(1);
    }

    public void main2() {
      result.add(2);
    }

    public void main3() {
      result.add(3);
    }
  }
}
