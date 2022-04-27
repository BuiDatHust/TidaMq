package tidaMq.server.unitTest;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import tidaMq.server.QueueNow;
import tidaMq.server.WriteToDiskThread;

public class testReadFromDisk {
	@BeforeClass()
	public static void runOnceBeforeAll() {
		System.out.println("start testing...");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public static void testReadFromFile() throws Exception {
		List<String> expectedReturn  ;
		
		WriteToDiskThread.ReadFromFile("") ;
		
	}
	
	@AfterClass()
	public static void runAfterClass() {
		System.out.println("ending testing...");
	}
}
