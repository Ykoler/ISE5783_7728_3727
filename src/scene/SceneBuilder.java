package scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import geometries.*;
import lighting.*;
import primitives.*;

public class SceneBuilder {
	SceneDescriptor sceneDesc;
	Scene scene;
	String filePath;

	public SceneBuilder(String sceneName) {
		scene = new Scene(sceneName);
		sceneDesc = new SceneDescriptor();
	}

	/**
	 * Parses a map of strings and adds to the scene's geometries and lights and
	 * attributes
	 * 
	 * @param xmlFile the xml file to load the scene from
	 */
	public Scene loadSceneFromFile(File xmlFile) {
		// extract the text from the XML file
		filePath = xmlFile.getAbsolutePath();
		try {
			String xmlString = Files.readString(Paths.get(filePath));
			sceneDesc.initializeFromXMLstring(xmlString);
			// create the scene from the fields of the SceneDescriptor
			// adding ambient light (map<String, String>)
			// adding background color (map<String, String>)
			var ambience = parser(sceneDesc.ambientLightAttributes);
			if (ambience.containsKey("color")) {
				var ambientC = ambience.get("color");
				scene.setAmbientLight(new AmbientLight(new Color(ambientC.get(0), ambientC.get(1), ambientC.get(2)),
						ambience.get("k").get(0)));
			}

			var backColor = parser(sceneDesc.sceneAttributes).get("background-color");
			if (backColor != null)
				scene.setBackground(new Color(backColor.get(0), backColor.get(1), backColor.get(2)));

			// adding spheres (list<map<String, String>>)
			for (Map<String, String> sphereMap : sceneDesc.spheres) {
				Map<String, List<Double>> sphere = parser(sphereMap);
				var spherePoints = sphere.get("center");
				Sphere s = new Sphere(sphere.get("radius").get(0),
						new Point(spherePoints.get(0), spherePoints.get(1), spherePoints.get(2)));
				if (sphere.containsKey("emission")) {
					var color = sphere.get("emission");
					s.setEmission(new Color(color.get(0), color.get(1), color.get(2)));
				}
				s.setMaterial(parseMaterial(sphereMap));
				scene.geometries.add(s);
			}
			// adding triangles (list<map<String, String>>)
			for (Map<String, String> triangleMap : sceneDesc.triangles) {
				List<Point> points = polygonParser(triangleMap);
				Triangle triangle = new Triangle(points.get(0), points.get(1), points.get(2));
				var color = parser(triangleMap).get("emission");
				if (color != null)
					triangle.setEmission(new Color(color.get(0), color.get(1), color.get(2)));
				triangle.setMaterial(parseMaterial(triangleMap));
				scene.geometries.add(triangle);
			}

			// adding polygons (list<map<String, String>>)
			for (Map<String, String> polygonMap : sceneDesc.polygons) {
				Polygon polygon = new Polygon(polygonParser(polygonMap).toArray(new Point[0]));
				polygon.setMaterial(parseMaterial(polygonMap));
				scene.geometries.add(polygon);
			}

			// adding directional lights (list<map<String, String>>)

			for (Map<String, String> directionalLightsMap : sceneDesc.directionalLights) {
				Map<String, List<Double>> directional = parser(directionalLightsMap);
				var color = directional.get("color");
				var dir = directional.get("direction");
				scene.lights.add(new DirectionalLight(new Color(color.get(0), color.get(1), color.get(2)),
						new Vector(dir.get(0), dir.get(1), dir.get(2))));
			}

			// adding point lights (list<map<String, String>>)
			for (Map<String, String> pointLightsMap : sceneDesc.pointLights) {
				Map<String, List<Double>> point = parser(pointLightsMap);
				var color = point.get("color");
				var pos = point.get("position");
				PointLight pl = new PointLight(new Color(color.get(0), color.get(1), color.get(2)),
						new Point(pos.get(0), pos.get(1), pos.get(2)));
				updateAtenuationFactors(pl, point);
				scene.lights.add(pl);

			}

			// adding spot lights (list<map<String, String>>)
			for (Map<String, String> spotLightsMap : sceneDesc.spotLights) {
				Map<String, List<Double>> spot = parser(spotLightsMap);
				var color = spot.get("color");
				var pos = spot.get("position");
				var dir = spot.get("direction");
				SpotLight spotL = new SpotLight(new Color(color.get(0), color.get(1), color.get(2)),
						new Point(pos.get(0), pos.get(1), pos.get(2)), new Vector(dir.get(0), dir.get(1), dir.get(2)));
				updateAtenuationFactors(spotL, spot);
				scene.lights.add(spotL);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return scene;
	}

	/**
	 * parses polygon attributes to a list of points
	 * 
	 * @param polygonMap map of polygon attributes
	 * @return list of points
	 */
	private List<Point> polygonParser(Map<String, String> polygonMap) {
		Map<String, List<Double>> polygon = parser(polygonMap);
		List<Point> points = new LinkedList<>();
		// length is the number of points in the polygon (elements that start with "p")
		// there can be optional material / emission attributes
		int length;
		for (length = 0; polygon.containsKey("p" + String.valueOf(length)); ++length)
			;
		for (int i = 0; i < length; ++i) {
			var point = polygon.get("p" + String.valueOf(i));
			points.add(new Point(point.get(0), point.get(1), point.get(2)));
		}
		return points;
	}

	/**
	 * parses map of attributes to a map of attributes to a list of doubles
	 * 
	 * @param map map of attributes
	 * @return map of attributes to a list of doubles
	 */
	private Map<String, List<Double>> parser(Map<String, String> map) {
		Map<String, List<Double>> attributes = new HashMap<String, List<Double>>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			List<Double> values = new ArrayList<>();
			for (String s : entry.getValue().split(" "))
				values.add(Double.parseDouble(s));
			attributes.put(entry.getKey(), values);
		}
		return attributes;
	}

	/**
	 * parses map of attributes to a material
	 * 
	 * @param map map of attributes
	 * @return material
	 */
	private Material parseMaterial(Map<String, String> map) {
		Material material = new Material();
		Map<String, List<Double>> attributes = parser(map);
		if (attributes == null)
			return null;
		var kD = attributes.get("kD");
		if (kD != null)
			material.setKd(kD.get(0));
		var kS = attributes.get("kS");
		if (kS != null)
			material.setKs(kS.get(0));
		var kR = attributes.get("kR");
		if (kR != null)
			material.setKr(kR.get(0));
		var kT = attributes.get("kT");
		if (kT != null)
			material.setKt(kT.get(0));
		var nShininess = attributes.get("nShininess");
		if (nShininess != null)
			material.setShininess((int) Math.round(nShininess.get(0)));
		return material;
	}

	/**
	 * updates attenuation factors of a light source
	 * 
	 * @param light light source
	 * @param map   map of attributes
	 */
	private void updateAtenuationFactors(PointLight pl, Map<String, List<Double>> attributes) {
		var kC = attributes.get("kC");
		if (kC != null)
			pl.setKc(kC.get(0));
		var kL = attributes.get("kL");
		if (kL != null)
			pl.setKl(kL.get(0));
		var kQ = attributes.get("kQ");
		if (kQ != null)
			pl.setKq(kQ.get(0));
	}
}
