package test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.warzone.team08.maphandlers.LoadMap;
import com.warzone.team08.maphandlers.MapComponents;

/**
 * This Class tests the blank fields available in the map file.
 * @author CHARIT
 */
public class TestBlankDataFields {
	private MapComponents d_mapComponents;
	private LoadMap load;
	private String path;
	
	/**
	 * This method runs before the test case runs.
	 * This method initializes different objects required to perform test.
	 */
	@Before
	public void beforeTest()
	{
		d_mapComponents = MapComponents.getInstance();
		load = new LoadMap();
		path = "C:\\Users\\CHARIT\\eclipse-workspace\\warzone-team-08\\src\\main\\java\\com\\warzone\\team08\\maps\\TestMapFiles\\";
	}
	/**
	 * This is a method that performs actual test.
	 * It test passes if .map file consists of any empty field.
	 * @throws Exception IOException
	 */
	@Test
	public void testBlankFields() throws Exception {
		d_mapComponents = load.loadMap(path + "TestBlankDataFields.map");
		assertEquals(false, d_mapComponents.checkMapCorrect());
	}
}
