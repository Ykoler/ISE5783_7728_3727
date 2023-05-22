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

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Point;

public class SceneBuilder {
	SceneDescriptor sceneDesc;
	Scene scene;
	String filePath;

	public SceneBuilder(String sceneName) {
		scene = new Scene(sceneName);
		sceneDesc = new SceneDescriptor();
	}

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
			var ambientC = ambience.get("color");
			scene.setAmbientLight(new AmbientLight(new Color(ambientC.get(0), ambientC.get(1), ambientC.get(2)),
					ambience.get("k").get(0)));

			var backColor = parser(sceneDesc.sceneAttributes).get("background-color");
			scene.setBackground(new Color(backColor.get(0), backColor.get(1), backColor.get(2)));

			// adding spheres (list<map<String, String>>)
			for (Map<String, String> sphereMap : sceneDesc.spheres) {
				Map<String, List<Double>> sphere = parser(sphereMap);
				var spherePoints = sphere.get("center");
				scene.geometries.add(new Sphere(sphere.get("radius").get(0),
						new Point(spherePoints.get(0), spherePoints.get(1), spherePoints.get(2))));
			}
			// adding triangles (list<map<String, String>>)
			for (Map<String, String> triangleMap : sceneDesc.triangles) {
				Map<String, List<Double>> triangle = parser(triangleMap);
				List<Point> points = new LinkedList<>();
				for (int i = 0; i < 3; ++i) {
					var point = triangle.get("p" + String.valueOf(i));
					points.add(new Point(point.get(0), point.get(1), point.get(1)));
				}
				System.out.println(points);
				Triangle t = new Triangle(points.get(0), points.get(1), points.get(2));
				System.out.println(t);
				scene.geometries.add(t);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scene;
	}

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
}
