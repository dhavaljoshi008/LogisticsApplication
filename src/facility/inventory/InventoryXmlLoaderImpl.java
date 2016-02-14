package facility.inventory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * InventoryXmlLoaderImpl.java
 * LogisticsApplication
 */
public class InventoryXmlLoaderImpl implements InventoryLoader {
    @Override
    public Map<String, Map<String, Integer>> loadInventory(String source) {
        Map<String, Map<String, Integer>> facilityInventoryMap = new HashMap<>();
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

                NodeList inventoryEntries = doc.getDocumentElement().getChildNodes();

                for (int i = 0; i < inventoryEntries.getLength(); i++) {
                if (inventoryEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                String entryName = inventoryEntries.item(i).getNodeName();
                if (!entryName.equals("Facility")) {
                    System.err.println("Unexpected node found: " + entryName);
                    return null;
                }

                // Fetching the facility
                NamedNodeMap aMap = inventoryEntries.item(i).getAttributes();
                String facilityId = aMap.getNamedItem("Id").getNodeValue();


                Element elem = (Element) inventoryEntries.item(i);

                Map<String, Integer> itemIdQuantity = new HashMap<>();

                NodeList itemList = elem.getElementsByTagName("Item");
                for (int j = 0; j < itemList.getLength(); j++) {
                    if (itemList.item(j).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    entryName = itemList.item(j).getNodeName();
                    if (!entryName.equals("Item")) {
                        System.err.println("Unexpected node found: " + entryName);
                        return null;
                    }

                    // Fetching connected facility and its distance
                    elem = (Element) itemList.item(j);
                    String itemId = elem.getElementsByTagName("ItemId").item(0).getTextContent();
                    String itemQuantity = elem.getElementsByTagName("Quantity").item(0).getTextContent();



                    itemIdQuantity.put(itemId, Integer.parseInt(itemQuantity));
                }

                // System.out.println("Facility_ID: " + facilityId + "ItemID and Quantity: ["+itemIdQuantity+"]");
                // Populating facilityInventoryMap
                facilityInventoryMap.put(facilityId, itemIdQuantity);

            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return facilityInventoryMap;
    }
}
