import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.bytedream.untis4j.Session;
import org.bytedream.untis4j.responseObjects.Classes;
import org.bytedream.untis4j.responseObjects.Timetable;
import org.bytedream.untis4j.responseObjects.WeeklyTimetable;
import org.junit.jupiter.api.Test;

public class TestUntisApiRaw
{
	
	public static String school = System.getenv("UNTIS_SCHOOl") == null ? "GSZ%20Balingen" : System.getenv("UNTIS_SCHOOl");
	public static String weburl = System.getenv("UNTIS_WEBURL") == null ? "https://neilo.webuntis.com" : System.getenv("UNTIS_SCHOOl");
	
	public static String username = System.getenv("UNTIS_USERNAME");
	public static String password = System.getenv("UNTIS_PASSWORD");
	
	@Test
	public void testUntisApiLogin() throws IOException
	{
		if (username == null || password == null)
		{
			System.out.println("UNTIS_USERNAME or UNTIS_PASSWORD not set. skiping...");
			return;
		}
		
		System.out.printf("UNTIS_SCHOOl: %s\nUNTIS_WEBURL: %s\nUNTIS_USERNAME: %s\nUNTIS_PASSWORD: %s\n", school, weburl, username, password);
		
		Session session = Session.login(username, password, weburl, school);
		session.logout();
	}
	
	@Test
	public void testUntisApiGetClasses() throws IOException
	{
		if (username == null || password == null)
		{
			System.out.println("UNTIS_USERNAME or UNTIS_PASSWORD not set. skiping...");
			return;
		}
		
		Session session = Session.login(username, password, weburl, school);
		
		Classes classes = session.getClasses();
		
		for (Classes.ClassObject class_obj : classes)
		{
			System.out.printf("%s: %d\n", class_obj.getName(), class_obj.getId());
		}
		
		session.logout();
	}
	
	@Test
	public void testUntisApiGetTimetable() throws IOException
	{	
		if (username == null || password == null)
		{
			System.out.println("UNTIS_USERNAME or UNTIS_PASSWORD not set. skiping...");
			return;
		}
	
		Session session = Session.login(username, password, weburl, school);
		
		Classes classes = session.getClasses();
		Classes.ClassObject my_class = null;
		
		for (Classes.ClassObject class_obj : classes)
		{
			if (class_obj.getName().equals(("TGI11_2")))
			{
				my_class = class_obj;
			}
		}
		
		System.out.printf("MY_CLASS: %s: %d\n", my_class.getName(), my_class.getId());
		
		WeeklyTimetable timetable = session.getWeeklyTimetableFromClassId(LocalDate.now(), my_class.getId());
		
		System.out.println(timetable);
		
		session.logout();
	}
}
