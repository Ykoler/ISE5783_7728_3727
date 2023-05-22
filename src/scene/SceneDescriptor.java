package scene;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class SceneDescriptor {
    
    public Map<String, String> sceneAttributes;
    /**
     * Ambient light attributes for example:
     * <ambientLight r="0.1" g="0.1" b="0.1"/>
     */
    public Map<String, String> ambientLightAttributes;
    public List<Map<String, String>> spheres;
    public List<Map<String, String>> triangles;
    public List<Map<String, String>> planes;
    public List<Map<String, String>> geometries;

    /**
     * Initializes the scene from the XML file content.
     * 
     * @param XMltext the XML file content
     */
    public void initializeFromXMLstring(String XMltext){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(XMltext));
            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();

            // Parse scene attributes
            sceneAttributes = new HashMap<>();
            NamedNodeMap sceneAttrs = root.getAttributes();
            for (int i = 0; i < sceneAttrs.getLength(); i++) {
                Node attr = sceneAttrs.item(i);
                sceneAttributes.put(attr.getNodeName(), attr.getNodeValue());
            }

            // Parse ambient light attributes as a map for example:
            ambientLightAttributes = new HashMap<>();
            NodeList ambientLightNodes = root.getElementsByTagName("ambient-light");
            if (ambientLightNodes.getLength() > 0) {
                Node ambientLightNode = ambientLightNodes.item(0);
                NamedNodeMap ambientLightAttrs = ambientLightNode.getAttributes();
                for (int i = 0; i < ambientLightAttrs.getLength(); i++) {
                    Node attr = ambientLightAttrs.item(i);
                    ambientLightAttributes.put(attr.getNodeName(), attr.getNodeValue());
                }
            }

            // Parse sphere elements
            spheres = new ArrayList<>();
            NodeList sphereNodes = root.getElementsByTagName("sphere");
            for (int i = 0; i < sphereNodes.getLength(); i++) {
                Node sphereNode = sphereNodes.item(i);
                NamedNodeMap sphereAttrs = sphereNode.getAttributes();
                Map<String, String> sphereMap = new HashMap<>();
                for (int j = 0; j < sphereAttrs.getLength(); j++) {
                    Node attr = sphereAttrs.item(j);
                    sphereMap.put(attr.getNodeName(), attr.getNodeValue());
                }
                spheres.add(sphereMap);
            }

            // Parse triangle elements
            triangles = new ArrayList<>();
            NodeList triangleNodes = root.getElementsByTagName("triangle");
            for (int i = 0; i < triangleNodes.getLength(); i++) {
                Node triangleNode = triangleNodes.item(i);
                NamedNodeMap triangleAttrs = triangleNode.getAttributes();
                Map<String, String> triangleMap = new HashMap<>();
                for (int j = 0; j < triangleAttrs.getLength(); j++) {
                    Node attr = triangleAttrs.item(j);
                    triangleMap.put(attr.getNodeName(), attr.getNodeValue());
                }
                triangles.add(triangleMap);
            }

            // Parse plane elements
            planes = new ArrayList<>();
            NodeList planeNodes = root.getElementsByTagName("plane");
            for (int i = 0; i < planeNodes.getLength(); i++) {
                Node planeNode = planeNodes.item(i);
                NamedNodeMap planeAttrs = planeNode.getAttributes();
                Map<String, String> planeMap = new HashMap<>();
                for (int j = 0; j < planeAttrs.getLength(); j++) {
                    Node attr = planeAttrs.item(j);
                    planeMap.put(attr.getNodeName(), attr.getNodeValue());
                }
                planes.add(planeMap);
            }

            // Parse geometry elements
            geometries = new ArrayList<>();
            NodeList geometryNodes = root.getElementsByTagName("geometry");
            for (int i = 0; i < geometryNodes.getLength(); i++) {
                Node geometryNode = geometryNodes.item(i);
                NamedNodeMap geometryAttrs = geometryNode.getAttributes();
                Map<String, String> geometryMap = new HashMap<>();
                for (int j = 0; j < geometryAttrs.getLength(); j++) {
                    Node attr = geometryAttrs.item(j);
                    geometryMap.put(attr.getNodeName(), attr.getNodeValue());
                }
                geometries.add(geometryMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}