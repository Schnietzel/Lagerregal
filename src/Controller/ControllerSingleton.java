package Controller;

public class ControllerSingleton {
	private static Buchungsverwaltung bvInstance;
	private static Lagerverwaltung lvInstance;
	private static Dateiverwaltung dvInstance;
	private static GUITools gtInstance;
	
	private ControllerSingleton() {}
	
	public static Buchungsverwaltung getBVInstance()
	{
		if (bvInstance == null)
			bvInstance = new Buchungsverwaltung();
		return bvInstance;
	}
	
	public static Lagerverwaltung getLVInstance()
	{
		if (lvInstance == null)
			lvInstance = new Lagerverwaltung();
		return lvInstance;
	}
	
	public static Dateiverwaltung getDVInstance()
	{
		if (dvInstance == null)
			dvInstance = new Dateiverwaltung();
		return dvInstance;
	}
	
	public static GUITools getGTInstance()
	{
		if (gtInstance == null)
			gtInstance = new GUITools();
		return gtInstance;
	}
}
