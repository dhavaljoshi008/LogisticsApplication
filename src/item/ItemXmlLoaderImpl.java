package item;

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
 * ItemXmlLoaderImpl.java
 * LogisticsApplication
 */
public class ItemXmlLoaderImpl implements ItemLoader {

    @Override
    public Map<String, Item> loadItems(String source) {
        Map<String, Item> items = new HashMap<>();
        try {
                String fileName = source;

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                File xml = new File(fileName);
                if (!xml.exists()) {
                    System.err.println("******** XML File '" + fileName + "' cannot be found! ********");
                    System.exit(-1);
                }

                Document doc = db.parse(xml);
                doc.getDocumentElement().normalize();

                NodeList itemEntries = doc.getDocumentElement().getChildNodes();

                for (int i = 0; i < itemEntries.getLength(); i++) {
                    if (itemEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    String entryName = itemEntries.item(i).getNodeName();
                    if (!entryName.equals("Item")) {
                        System.err.println("Unexpected node found: " + entryName);
                        return null;
                    }

                    // Fetching Item_ID
                    NamedNodeMap aMap = itemEntries.item(i).getAttributes();
                    String itemId = aMap.getNamedItem("Id").getNodeValue();

                    // Fetching Item_Price
                    Element elem = (Element) itemEntries.item(i);
                    String itemPrice = elem.getElementsByTagName("Price").item(0).getTextContent();

                    // Populating items Map
                    items.put(itemId, new Item(itemId, Double.parseDouble(itemPrice)));
                   // System.out.println("Item_ID: " + itemId + " Item_Price: " + itemPrice + "\n");

            }

        }
        catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return items;
    }
}
