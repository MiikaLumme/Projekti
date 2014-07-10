package kesaprojekti.remotelights;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

public class PullParserHandler {
	private static final String XML_FILE	= "settings.xml";
	
	private	ArrayList<String>	names 		= new ArrayList<String>();
	private ArrayList<Integer>	address		= new ArrayList<Integer>();
	private ArrayList<Integer>	allAddr		= new ArrayList<Integer>();
	private ArrayList<Boolean>	buttonType	= new ArrayList<Boolean>();
	private ArrayList<String>	allTargets	= new ArrayList<String>();
	private	String				text		= "";
	private String				currZone	= "";
	private	int					tag 		= 0;
	
	//
	public ArrayList<String> getNames()	{
		
		return names;
	}
	
	public ArrayList<String> getAllTargets() {
		
		return allTargets;
	}
	
	public ArrayList<Boolean> getButtonType()	{
		
		return buttonType;
	}
	
	public ArrayList<Integer> getAddress()	{
		
		return address;
	}
	
	public ArrayList<Integer> getAllAddr()	{
		
		return allAddr;
	}
	
	public PullParserHandler getParser(String item, Context context)	{
    	PullParserHandler	parser	= new PullParserHandler();
   	
	   	try {
				parser.parse(context.getAssets().open(XML_FILE), item);
			} catch (IOException e) {
				e.printStackTrace();
			}
	   	return parser;
   }
	
	public void parse(InputStream is, String name) throws IOException	{
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
						names.add(text);
						//assigns current zone name to currZone
						currZone = text;
					}
					
					//targetnames
					if (tagname.equalsIgnoreCase("targetname"))	{
						//all (add currZone + target)
						allTargets.add(currZone + ": " + text);
						
						
						//in given zone
						if (tag != 0)	{
							names.add(text);
						}
						
						
					}
					//type of button
					if (tagname.equalsIgnoreCase("type") && tag != 0) {
						if (text.equalsIgnoreCase("onoff")) {
							buttonType.add(true);
						
						}
						else {
							buttonType.add(false);
						}
					}
					if (tagname.equalsIgnoreCase("address"))	{
						if (tag != 0)	{
							address.add(Integer.valueOf(text));
						}
						allAddr.add(Integer.valueOf(text));						
						
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
		
				
		
	}
	
}

