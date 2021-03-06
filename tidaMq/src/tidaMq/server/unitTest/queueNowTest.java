package tidaMq.server.unitTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import tidaMq.server.QueueNow;
import tidaMq.server.queue;
import tidaMq.server.config.GetConfigValue;

public class queueNowTest {
	@BeforeClass()
	public static void runOnceBeforeAll() {
		System.out.println("start testing...");
	}
	
	@Test()
	public void testCreateQueue_ReturnOK() {
		String[] arr = { "create", "5", "cdc","1" };
		String expected = "Ok" ;
		QueueNow q = new QueueNow() ;
		assertEquals(expected, q.createNewQueue(arr));
	}
	
	@Test()
	public void testListAllQueue() {
		String expected = "cd new" ;
		QueueNow q = new QueueNow();
		assertEquals(expected, q.listQueue());
	}
	
	@AfterClass()
	public static void runAfterClass() {
		System.out.println("ending testing...");
	}
}
