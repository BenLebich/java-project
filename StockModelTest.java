import static org.junit.Assert.*;
import org.junit.Test;

/*
 * Test the StockModel Class
 */
public class StockModelTest {
	// only run the test once per min
	// api maxed out at 5 req/min
	// current tests use 5 req total

	// checks if MSFT is valid stock symbol
	// should return 1 for true
	@Test
	public void testIsValid1() {
		StockModel stock = new StockModel();
		assertEquals(1, stock.isValid("MSFT"));
	}

	// check if INVALID is valid stock symbol
	// should return 0 for false
	@Test
	public void testIsValid2() {
		StockModel stock = new StockModel();
		assertEquals(0, stock.isValid("INVALID"));
	}

	// get the time zone
	// should return "US/Eastern"
	@Test
	public void testTimeZone() {
		StockModel stock = new StockModel();
		stock.isValid("NVDA");
		assertEquals("US/Eastern", stock.getTimeZone());
	}

	// checks if daily data returned is 2d array
	// checks size of each dimension (7,5)
	@Test
	public void testGetDaily() {
		StockModel stock = new StockModel();
		double[][] stockDaily = stock.getDaily("");
		assertEquals(7, stockDaily.length);
		for (int i = 0; i < stockDaily.length; i++) {
			assertEquals(5, stockDaily[i].length);
		}
	}

	// checks if monthly data returned is 2d array
	// checks size of each dimension (7,5)
	@Test
	public void testGetWeekly() {
		StockModel stock = new StockModel();
		double[][] stockWeekly = stock.getDaily("");
		assertEquals(7, stockWeekly.length);
		for (int i = 0; i < stockWeekly.length; i++) {
			assertEquals(5, stockWeekly[i].length);
		}
	}

	// checks if monthly data returned is 2d array
	// checks size of each dimension (7,5)
	@Test
	public void testGetMonthly() {
		StockModel stock = new StockModel();
		double[][] stockMonthly = stock.getDaily("");
		assertEquals(7, stockMonthly.length);
		for (int i = 0; i < stockMonthly.length; i++) {
			assertEquals(5, stockMonthly[i].length);
		}
	}

}
