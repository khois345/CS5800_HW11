import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
	SnackTest.class,
	SnackDispenseHandlerTest.class,
	VendingMachineTest.class,
	DispensingSnackStateTest.class,
	WaitingForMoneyStateTest.class,
	IdleStateTest.class
})
public class CompleteUnitTest {
}
