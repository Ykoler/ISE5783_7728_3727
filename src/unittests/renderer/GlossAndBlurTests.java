package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing the gloss and diffuse features added in MP1.
 * 
 * @author Yahel and Ashi
 */
class GlossAndBlurTests {
	private Scene scene = new Scene("Test scene");

	// @Test
	public void TestCombo() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				new Sphere(50d, new Point(0, 0, 0))
						.setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(10).setKt(0.8))
						.setEmission(new Color(RED)),
				new Sphere(25d, new Point(0, 0, 0)).setMaterial(new Material().setKd(0.2).setKs(0.8).setShininess(50))
						.setEmission(new Color(GRAY)),
				new Triangle(new Point(-120, 10, 0), new Point(0, -100, -50), new Point(0, 110, -50))
						.setMaterial(new Material().setKd(0.1).setKr(0.6).setKg(20)),
				new Triangle(new Point(0, 0, 50), new Point(0, 90, 50), new Point(80, 0, 50))
						.setMaterial(new Material().setKd(0.1).setKt(0.3).setKb(20)),
				new Plane(new Point(70, 0, -140), new Vector(-0.35, 0, 1))
						.setMaterial(new Material().setKg(7).setKr(0.9)).setEmission(new Color(0, 30, 50)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		scene.setBackground(new Color(0, 100, 160));

		ImageWriter imageWriter = new ImageWriter("TestComboWithBlur", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere within a sphere mirrored by a double mirror */
	// @Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
								.setKt(new Double3(0.5, 0, 0)).setKb(0)),
				new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1).setKg(10)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4)).setKg(10)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirroredWithGloss", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Geometry combination presenting glossing and diffusion */
	@Test
	public void geometryCombinationTest() {
		Camera camera = new Camera(new Point(40, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				new Sphere(50d, new Point(0, 0, 0)).setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10))
						.setEmission(new Color(130, 80, 0)),
				new Triangle(new Point(-10, 0, 70), new Point(0, 90, 40), new Point(80, 0, 50))
						.setMaterial(new Material().setKt(0.3).setKb(20)).setEmission(new Color(GRAY)),
				new Plane(new Point(70, 0, -140), new Vector(-0.35, 0, 1))
						.setMaterial(new Material().setKg(7).setKr(0.9)).setEmission(new Color(0, 30, 50)));

		scene.setBackground(new Color(30, 10, 0));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("BlurredNewCombo1", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

}
