package dp.leiba.music.person;

/**
 * Person.
 */
public class Person
{
	public static final String NAME = "Bit B";
	
	/**
	 * Say.
	 * 
	 * @param what What.
	 */
	public static void say(String what)
	{
		System.out.println(NAME + ", say: " + what);
	}
	
	/**
	 * Debug.
	 * 
	 * @param name  Name.
	 * @param value Value.
	 */
	public static void debug(String name, String value)
	{
		System.out.println(NAME + ", debug (" + name + "): " + value);
	}
	
	/**
	 * Debug.
	 * 
	 * @param params Parameters.
	 */
	public static void debug(String[][] params)
	{
		for (String[] param : params) {
			debug(param[0], param[1]);
		}
	}
}
