package json.filter;

import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;
 

public class CreateFilteredjson {
	
	
	private static final String filePath = "E:/Sem 1/CSE 535/Project Part 3/SampleJava/SampleJava/src/fr_tweets.json";
	public static Date parseTwitterUTC(String date) 
			throws ParseException, java.text.ParseException {
		 	String twitterFormat="EEE MMM dd HH:mm:ss +0000 yyyy";
		 	String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		 	// Important note. Only ENGLISH Locale works.
			SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
			sf.setLenient(true);
		//	sf.applyPattern(pattern);
			
		 	return sf.parse(date);
		}
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException, XPathExpressionException, SAXException, ParserConfigurationException {
		
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			//JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
			JSONArray statuses = (JSONArray	) jsonParser.parse(reader);
			//JSONArray statuses= (JSONArray) jsonObject.get("statuses");
			Iterator iterator = statuses.iterator();
			
			JSONObject fullObject = new JSONObject();
			JSONObject obj = null;
			JSONArray array = new JSONArray();
			JSONArray array1 = new JSONArray();
			
			int n=1;
			
			while(iterator.hasNext())
			{
				JSONObject innerObj = (JSONObject) iterator.next();
				//System.out.println("Text"+ innerObj.get("lang").toString());
				obj = new JSONObject();
				//innerObj.get("text").toString().replace("&amp", "");
				obj.put("id",innerObj.get("id").toString());
				if("en".equals(innerObj.get("lang").toString())){
					obj.put("text_en",innerObj.get("text_fr"));}
				
				if("de".equals(innerObj.get("lang").toString())){
					obj.put("text_de",innerObj.get("text_fr"));}
				
				if("ru".equals(innerObj.get("lang").toString())){
					obj.put("text_ru",innerObj.get("text_fr"));}
				
				
				
				obj.put("lang",innerObj.get("lang"));
				//obj.put("tweet_hashtags",innerObj.get("tweet_hashtags"));
				Map<String,Object> map = (Map<String,Object>)(innerObj.get("tweet_hashtags"));
				array1 = (JSONArray)map.get("hashtags");
				Iterator iterator1 = array1.iterator();
				JSONArray array2 = new JSONArray();
				while(iterator1.hasNext()){
					
					JSONObject innerObj1 = (JSONObject) iterator1.next();
					array2.add((innerObj1.get("text")));
					
				}
				
				
				JSONArray array3 = new JSONArray();
				array3 = (JSONArray)map.get("tweet_urls");
				Iterator iterator2 = array3.iterator();
				JSONArray array4 = new JSONArray();
				while(iterator2.hasNext()){
					
					JSONObject innerObj1 = (JSONObject) iterator2.next();
					array4.add(innerObj1.get("tweet_urls"));
					
				}
				
				obj.put("tweet_hashtags", array2);
				obj.put("tweet_urls", array4);
				
				//ContentTagging contentTagging = new ContentTagging((String)innerObj.get("text"));
				//Object  arrayKeyword = contentTagging.extractKeyword(array2);
				//obj.put("tags", arrayKeyword);
				
				ContentTagging contentTagging= new ContentTagging((String)innerObj.get("text"));
				double  sentiment = contentTagging.extractSentiment();
				obj.put("sentiment", sentiment);
				 
				String name = (String) ((JSONObject)(innerObj.get("user"))).get("name");
				obj.put("UserName", name);
				
				String location =  (String) ((JSONObject)(innerObj.get("user"))).get("location");
				obj.put("location", location);
				
				if(null != innerObj.get("geo")){
				JSONArray geo = (JSONArray) ((JSONObject)(innerObj.get("geo"))).get("coordinates");
				obj.put("geo", geo);
				}
				
				
						
				//obj.replace("/n", "");
				//obj.replace("&amp", "");
				
				//obj.put("created_at", innerObj.get("created_at"));
				//array.add(obj);
				
				Date date = CreateFilteredjson.parseTwitterUTC(innerObj.get("created_at").toString());
				SimpleDateFormat date1 = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss'Z'");
				String DateToStr = date1.format(date);
				obj.put("created_at", DateToStr);
				System.out.print("DateToStr\n" + DateToStr);
				
			    
				
				

				//System.out.println(DateToStr + "  " + innerObj.get("created_at").toString() );
				array.add(obj);
				n= n +1 ;
				
			}
			
			fullObject.put("statuses", array);
			System.out.print("n" + n);
			//System.out.print("DateToStr" + DateToStr);
			FileWriter file = new FileWriter("fr_12_15_2015_out.json");
			file.write(array.toString());
			file.flush();
			file.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner dis=new Scanner(System.in);
        int a,b,c;
        String line;
        String[] lineVector;

        line = dis.nextLine(); //read 1,2,3

        //separate all values by comma
        lineVector = line.split(" ");

        //parsing the values to Integer
        a=Integer.parseInt(lineVector[0]);
        b=Integer.parseInt(lineVector[1]);
        c=Integer.parseInt(lineVector[2]);
        System.out.println(a);
        		


	}

}

