package json.filter;

import com.alchemyapi.api.AlchemyAPI;

import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import java.io.*;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ContentTagging {
	private static String inputText = null;
	ContentTagging(String text){
		inputText = text;
	}

	public static JSONArray extractKeyword(org.json.simple.JSONArray hashtags) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException{

		// Create an AlchemyAPI object.
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("*put alchemy api string*");

		// Extract topic keywords for a text string.
		Document doc = alchemyObj.TextGetRankedKeywords(inputText);
		
		JSONObject xmlJSONObj = XML.toJSONObject(getStringFromDocument(doc));
		
		JSONArray array2 = new JSONArray();
		if(!((JSONObject)xmlJSONObj.get("results")).get("keywords").equals("")){
			JSONObject keywords =  (JSONObject)((JSONObject)xmlJSONObj.get("results")).get("keywords");

			if(keywords.get("keyword") instanceof JSONArray)
			{	
				JSONArray array1 = keywords.getJSONArray("keyword");


				Iterator iterator = array1.iterator();
				while (iterator.hasNext()){
					JSONObject innerObj1 = (JSONObject) iterator.next();
					if((Double)innerObj1.get("relevance")> 0.7 
							&& ! hashtags.contains(innerObj1.get("text")))
						array2.put(innerObj1.get("text"));

				}
			}
			else if(keywords.get("keyword") instanceof JSONObject){
				if((Double)((JSONObject)keywords.get("keyword")).get("relevance")> 0.7
						&& ! hashtags.contains(((JSONObject)keywords.get("keyword")).get("text")))
					array2.put(((JSONObject)keywords.get("keyword")).get("text"));
			}
		}

		System.out.println(array2.toString());
		return array2;
	}
	// utility method
	
	public static double extractSentiment() throws XPathExpressionException, IOException, SAXException, ParserConfigurationException{
		
		// Create an AlchemyAPI object.
				AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("*put alchemy api string*");

				// Extract topic keywords for a text string.
				Document doc = alchemyObj.TextGetTextSentiment(inputText);
				JSONObject xmlJSONObj = XML.toJSONObject(getStringFromDocument(doc));
				JSONArray array2 = new JSONArray();
				System.out.println("result" + xmlJSONObj.toString());
				JSONObject result = (JSONObject) xmlJSONObj.get("results");
				JSONObject docSentiment =  (JSONObject)result.get("docSentiment");
				double sentiment = 0 ;
				if(! ((String)docSentiment.get("type")).equals("neutral")){
				 sentiment = (double) docSentiment.get("score");
				}
				 
				System.out.println("sentiment"+ sentiment);
				return sentiment;
		
	}
	
	private static String getStringFromDocument(Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);

			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}