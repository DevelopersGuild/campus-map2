package net.developersguild.campus_map.pathfinding.importdata;

import android.util.Xml;

import net.developersguild.campus_map.pathfinding.DestinationNode;
import net.developersguild.campus_map.pathfinding.NavMap;
import net.developersguild.campus_map.pathfinding.Node;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by planetguy on 10/31/2014.
 */
public class TextImporter {

    final NavMap mapGenerating;

    /**
     * Processes string into nodes. Format:
     * @param readLines
     */
    public TextImporter(String readLines){
        String[] lines=readLines
                .replaceAll("#.*?\n", "") //removes comments
                .split("\n"); //splits by newlines
        int index=0; //index into lines
        String[] offsets=lines[0].split(" "); //process first line - minX, maxX, minY, maxY
        mapGenerating=new NavMap(
                Double.parseDouble(offsets[0]),
                Double.parseDouble(offsets[1]),
                Double.parseDouble(offsets[2]),
                Double.parseDouble(offsets[3])
        );
        index++;

        //process points

        //store created nodes
        HashMap<String, Node> createdNodes=new HashMap<String, Node>();
        while(index<lines.length){
            String[] parts=lines[index].split(" "); //separate by space
            String name=parts[0];
            Node node;
            if(name.charAt(0)=='>'){
                node=new DestinationNode(
                        mapGenerating,
                        name.substring(1).replaceAll("_"," "), //remove > at start, expand _
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2])
                );
            } else {
                node = new Node(
                        mapGenerating,
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2])
                );
            }
            createdNodes.put(name, node);
            mapGenerating.addNode(node);

            //parse connections
            for(int i=3; i<parts.length; i++){
                String entry=parts[i];
                String[] entries=entry.split(":");
                Node referencedNode=createdNodes.get(entries[0]);
                String out = entries[1].replace('_', ' ');
                if(!entries[1].equals("/")) {
                    node.addPathToNode(referencedNode, out);
                }
                if(!entries[2].equals("/"))
                    if (entries[2].equals("="))
                        referencedNode.addPathToNode(node, invertDirections(out));
                    else
                        referencedNode.addPathToNode(node, entries[2].replace('_', ' '));
            }
            index++;
        }
    }

    //24 random alphanumeric chars - used in swapping, under the assumption that it will not be in any direction given
    private static String marker="9GeNwEmCKpn6kFTW4apt9dv4";

    private static String invertDirections(String s){
        s=swap(s, "north", "south");
        s=swap(s,"east","west");
        s=swap(s,"left","right");
        return s;
    }

    private static String swap(String in, String s1, String s2){
        return in.replace(s1, marker).replaceAll(s2, s1).replaceAll(marker,s2);
    }
}
