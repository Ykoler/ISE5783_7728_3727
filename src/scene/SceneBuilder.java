package scene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
			double r = Double.parseDouble(sceneDesc.ambientLightAttributes.get("color").split(" ")[0]);
			double g = Double.parseDouble(sceneDesc.ambientLightAttributes.get("color").split(" ")[1]);
			double b = Double.parseDouble(sceneDesc.ambientLightAttributes.get("color").split(" ")[2]);
			Color ambientLightColor = new Color(r, g, b);
			// adding background color (map<String, String>)
			r = Double.parseDouble(sceneDesc.sceneAttributes.get("background-color").split(" ")[0]);
			g = Double.parseDouble(sceneDesc.sceneAttributes.get("background-color").split(" ")[1]);
			b = Double.parseDouble(sceneDesc.sceneAttributes.get("background-color").split(" ")[2]);

			scene.setAmbientLight(
					new AmbientLight(ambientLightColor, Double.parseDouble(sceneDesc.ambientLightAttributes.get("k"))));
			Color backgroundColor = new Color(r, g, b);
			scene.setBackground(backgroundColor);
			// adding spheres (list<map<String, String>>)
			for (Map<String, String> sphere : sceneDesc.spheres) {
				scene.geometries.add(new Sphere(Double.parseDouble(sphere.get("radius")),
						new Point(Double.parseDouble(sphere.get("center").split(" ")[0]),
								Double.parseDouble(sphere.get("center").split(" ")[1]),
								Double.parseDouble(sphere.get("center").split(" ")[2]))));
			}
			// adding triangles (list<map<String, String>>)
			for (Map<String, String> triangle : sceneDesc.triangles) {
				scene.geometries.add(new Triangle(
						new Point(Double.parseDouble(triangle.get("p0").split(" ")[0]),
								Double.parseDouble(triangle.get("p0").split(" ")[1]),
								Double.parseDouble(triangle.get("p0").split(" ")[2])),
						new Point(Double.parseDouble(triangle.get("p1").split(" ")[0]),
								Double.parseDouble(triangle.get("p1").split(" ")[1]),
								Double.parseDouble(triangle.get("p1").split(" ")[2])),
						new Point(Double.parseDouble(triangle.get("p2").split(" ")[0]),
								Double.parseDouble(triangle.get("p2").split(" ")[1]),
								Double.parseDouble(triangle.get("p2").split(" ")[2]))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scene;
	}
}
