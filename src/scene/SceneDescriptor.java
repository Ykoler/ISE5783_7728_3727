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
	 * Ambient light attributes for example: <ambientLight r="0.1" g="0.1" b="0.1"/>
	 */
	public Map<String, String> ambientLightAttributes;
	/** Sphere attributes for example: <sphere center="0,0,0" radius="1"/> */
	public List<Map<String, String>> spheres;
	/** Plane attributes for example: <plane p="0,0,0" normal="0,0,1"/> */
	public List<Map<String, String>> triangles;
	/** Plane attributes for example: <plane p="0,0,0" normal="0,0,1"/> */
	public List<Map<String, String>> planes;
	public List<Map<String, String>> polygons;
	public List<Map<String, String>> geometries;
	public List<Map<String, String>> directionalLights;
	public List<Map<String, String>> pointLights;
	public List<Map<String, String>> spotLights;

	/**
	 * Initializes the scene from the XML file content.
	 * 
	 * @param XMltext the XML file content
	 */
	public void initializeFromXMLstring(String XMltext) {
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
				ambientLightAttributes = parseElement(ambientLightNodes, 0);
			}

			// Parse sphere elements
			spheres = new ArrayList<>();
			NodeList sphereNodes = root.getElementsByTagName("sphere");
			for (int i = 0; i < sphereNodes.getLength(); i++) {
				Map<String, String> sphereMap = parseElement(sphereNodes, i);
				spheres.add(sphereMap);
			}

			// Parse triangle elements
			triangles = new ArrayList<>();
			NodeList triangleNodes = root.getElementsByTagName("triangle");
			for (int i = 0; i < triangleNodes.getLength(); i++) {
				Map<String, String> triangleMap = parseElement(triangleNodes, i);
				triangles.add(triangleMap);
			}

			// Parse plane elements
			planes = new ArrayList<>();
			NodeList planeNodes = root.getElementsByTagName("plane");
			for (int i = 0; i < planeNodes.getLength(); i++) {
				Map<String, String> planeMap = parseElement(planeNodes, i);
				planes.add(planeMap);
			}

			// Parse triangle elements
			polygons = new ArrayList<>();
			NodeList polygonNodes = root.getElementsByTagName("polygon");
			for (int i = 0; i < polygonNodes.getLength(); i++) {
				Map<String, String> polygonMap = parseElement(polygonNodes, i);
				polygons.add(polygonMap);
			}

			// Parse directional lights
			directionalLights = new ArrayList<>();
			NodeList directionalLightsNodes = root.getElementsByTagName("directional-light");
			for (int i = 0; i < directionalLightsNodes.getLength(); i++) {
				Map<String, String> directionalMap = parseElement(directionalLightsNodes, i);
				directionalLights.add(directionalMap);
			}

			// Parse directional lights
			pointLights = new ArrayList<>();
			NodeList pointLightsNodes = root.getElementsByTagName("point-light");
			for (int i = 0; i < pointLightsNodes.getLength(); i++) {
				Map<String, String> pointMap = parseElement(pointLightsNodes, i);
				pointLights.add(pointMap);
			}

			// Parse directional lights
			spotLights = new ArrayList<>();
			NodeList spotLightsNodes = root.getElementsByTagName("spot-light");
			for (int i = 0; i < spotLightsNodes.getLength(); i++) {
				Map<String, String> spotMap = parseElement(spotLightsNodes, i);
				spotLights.add(spotMap);
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

	/**
	 * Parses an element into a map for example:
	 * <sphere center="0,0,3" radius="1" color="0,0,1" ka="0.1" kd="0.5" ks="0.5" n=
	 * "20"/> into a map that contains the following key-value pairs: "center" ->
	 * "0,0,3" "radius" -> "1" "color" -> "0,0,1"
	 * 
	 * @param elementNodes the element nodes
	 * @param i            the index
	 * @return the map
	 */
	private Map<String, String> parseElement(NodeList elementNodes, int i) {
		Node elementNode = elementNodes.item(i);
		NamedNodeMap elementAttrs = elementNode.getAttributes();
		Map<String, String> elementMap = new HashMap<>();
		for (int j = 0; j < elementAttrs.getLength(); j++) {
			Node attr = elementAttrs.item(j);
			elementMap.put(attr.getNodeName(), attr.getNodeValue());
		}
		return elementMap;
	}
}
