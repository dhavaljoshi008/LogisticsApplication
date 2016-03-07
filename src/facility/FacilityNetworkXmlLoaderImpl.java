package facility;

import exceptions.InvalidArgumentException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * FacilityNetworkXmlLoaderImpl.java
 * LogisticsApplication
 */
public class FacilityNetworkXmlLoaderImpl implements FacilityNetworkLoader {
    @Override
    public Map<String, Facility> loadFacilityNetwork(String source) throws InvalidArgumentException {
        Map<String, Facility> facilityMap = new HashMap<>();
        try {
                String fileName = source;

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                File xml = new File(fileName);
                if (!xml.exists()) {
                    System.err.println("**** XML File '" + fileName + "' cannot be found");
                    System.exit(-1);
                }

                Document doc = db.parse(xml);
                doc.getDocumentElement().normalize();

                NodeList facilityEntries = doc.getDocumentElement().getChildNodes();

                for (int i = 0; i < facilityEntries.getLength(); i++) {
                    if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                String entryName = facilityEntries.item(i).getNodeName();
                if (!entryName.equals("Facility")) {
                    System.err.println("Unexpected node found: " + entryName);
                    return null;
                }

                // Fetch the facility
                NamedNodeMap aMap = facilityEntries.item(i).getAttributes();
                String facilityId = aMap.getNamedItem("Id").getNodeValue();

                // Fetch Processing Rate and Cost of the facility
                Element elem = (Element) facilityEntries.item(i);
                String processingRate = elem.getElementsByTagName("ProcessingRate").item(0).getTextContent();
                String processingCost = elem.getElementsByTagName("ProcessingCost").item(0).getTextContent();



                Map<String, Double> edgeIdDistance = new HashMap<>();

                NodeList edgeList = elem.getElementsByTagName("Edge");
                for (int j = 0; j < edgeList.getLength(); j++) {
                    if (edgeList.item(j).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    entryName = edgeList.item(j).getNodeName();
                    if (!entryName.equals("Edge")) {
                        System.err.println("Unexpected node found: " + entryName);
                        return null;
                    }

                    // Fetch connected facility and its distance
                    elem = (Element) edgeList.item(j);
                    String edgeId = elem.getElementsByTagName("Id").item(0).getTextContent();
                    String edgeDistance = elem.getElementsByTagName("Distance").item(0).getTextContent();



                    edgeIdDistance.put(edgeId, Double.parseDouble(edgeDistance));
                }

               // System.out.println("Facility_ID: " + facilityId + " ProcessingCapacityPerDay: " + processingRate + " ProcessingCost: $"+ processingCost + " DirectLinks: ["+edgeIdDistance+"]");
                // Populate facilityMap
                facilityMap.put(facilityId, FacilityFactory.build("basic", facilityId, Integer.parseInt(processingRate), Double.parseDouble(processingCost), new HashMap<String, Integer>(), edgeIdDistance));

            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return facilityMap;
    }

}
