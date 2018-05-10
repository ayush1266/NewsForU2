package acedemy.learningprogramming.newsforu;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by win on 09-05-2018.
 */
public class ParseApplications {
    private static final String TAG = "ParseApplications";
    private ArrayList<FeedEntry> applications;
    public ParseApplications() {
        this.applications = new ArrayList<>();
    }
    public ArrayList<FeedEntry> getApplications() {
        return applications;
    }
    public boolean parse(String xmldata){
        boolean status=true;
        FeedEntry currentRecord=null;
        boolean inEntry = false;
        String textValue="";
        boolean gotImage=false;
        int rank=1;
        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmldata));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
               //         Log.d(TAG, "parse: Starting tag: "+ tagName);
                        if("item".equalsIgnoreCase(tagName))
                        {
                            inEntry=true;
                            currentRecord=new FeedEntry();

                        }

                        break;
                    case XmlPullParser.TEXT:
                        textValue=xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                 //       Log.d(TAG, "parse: Ending tag for "  + tagName);
                        if(inEntry){
                            if("item".equalsIgnoreCase(tagName)){
                                applications.add(currentRecord);
                                inEntry=false;

                            }else if("title".equalsIgnoreCase(tagName)){
                                currentRecord.setname(textValue);
                            }else if("pubdate".equalsIgnoreCase(tagName)){
                                currentRecord.setReleaseDate(textValue);
                            }else if("description".equalsIgnoreCase(tagName)){
                                currentRecord.setSummary(textValue);
                            }else if("a".equalsIgnoreCase(tagName)){
                                currentRecord.setUrl(textValue);
                            }else if("div".equalsIgnoreCase(tagName)){
                                currentRecord.setUrl(textValue);
                            }
                        }
                        break;
                    default:
                        //nothing
                }
                eventType=xpp.next();
            }
//            for (FeedEntry app:applications){
//                Log.d(TAG, "*************");
//                Log.d(TAG, app.toString());
//            }
        }catch(Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
