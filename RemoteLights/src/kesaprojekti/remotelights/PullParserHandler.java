package kesaprojekti.remotelights;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class PullParserHandler {
	private	ArrayList<String>	names;
	private ArrayList<Boolean>	buttonType;
	private	String				text;
	private	int					tag;

	
	public PullParserHandler()	{
		names		= new ArrayList<String>();
		buttonType  = new ArrayList<Boolean>();
		tag			= 0;
		
	}
	
	public ArrayList<Boolean> returnButtonType() {
		return buttonType;
		
	}
		
	public ArrayList<String> parse(InputStream is, String name) throws IOException	{
		try	{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser parser = factory.newPullParser();
			
			parser.setInput(is, null);
			
			int eventType	= parser.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT)	{
				String tagname = parser.getName();
				switch (eventType)	{
				case XmlPullParser.START_TAG:
					break;
				case XmlPullParser.TEXT:
					text = parser.getText();
					if (text.equalsIgnoreCase(name))	{
						tag = 1;
					}
					break;
				case XmlPullParser.END_TAG:
					//collects all zonenames
					if (tagname.equalsIgnoreCase("zonename") && name.equalsIgnoreCase("zonename"))	{
						Log.v("zone", text);		// for testing purposes
						names.add(text);
					}
					//targetnames in given zone
					if (tagname.equalsIgnoreCase("targetname") && tag != 0)	{
						Log.v("target", text);		// for testing purposes
						names.add(text);
					}
					//type of button
					if (tagname.equalsIgnoreCase("type") && tag !=0) {
						Log.v("zone", text);
						if (text.equalsIgnoreCase("onoff")) {
							buttonType.add(true);
						}
						else {
							buttonType.add(false);
						}
					}
					
					if (tagname.equalsIgnoreCase("zone"))	{
						tag = 0;
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
			
			
		} catch(XmlPullParserException e)	{
			e.printStackTrace();
		}
		return names;
		
		
	}
	
}

