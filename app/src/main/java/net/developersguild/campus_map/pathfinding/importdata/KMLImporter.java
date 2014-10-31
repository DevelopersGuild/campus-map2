package net.developersguild.campus_map.pathfinding.importdata;

import android.util.Xml;

import net.developersguild.campus_map.pathfinding.NavMap;
import net.developersguild.campus_map.pathfinding.Node;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by planetguy on 10/31/2014.
 */
public class KMLImporter {

    public static NavMap loadData(NavMap nm) throws XmlPullParserException, IOException {
        XmlPullParser parser= Xml.newPullParser();
        parser.setInput(new StringReader("arasd"));
        int eventType=parser.getEventType();


        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType==XmlPullParser.START_TAG){
                String tagName=parser.getName();
                if(tagName.equals("Placemark")){
                    
                }

            }


            parser.next();
        }

        return nm;
    }

    public Node readPlacemark(XmlPullParser parser){

    }

}
