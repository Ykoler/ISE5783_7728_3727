package unittests.renderer;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * @author ashih
 *
 */
class GlossAndBlurTests {
	private Scene scene = new Scene("Test scene");

	@Test
	public void TestCombo() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add(
				new Sphere(50d, new Point(0, 0, 0))
						.setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(10).setKt(0.85))// .setKr(0.2))
						.setEmission(new Color(RED)),
				new Triangle(new Point(-80, 10, 0), new Point(0, -80, -50), new Point(0, 80, -50))
						.setMaterial(new Material().setKd(0.1).setKr(0.6).setKg(20)),
				new Triangle(new Point(0, 0, 50), new Point(0, 60, 50), new Point(80, 0, 50))
						.setMaterial(new Material().setKd(0.1).setKt(0.3).setKb(20)));
////					.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
//				new Plane(new Point(0, 0, 100), new Vector(0, 0, 1))
//						.setMaterial(new Material().setKd(0.1).setKt(0.9).setKb(40)));//.setEmission(new Color(0, 0, 50)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		scene.setBackground(new Color(0, 100, 160));

		ImageWriter imageWriter = new ImageWriter("TestComboWithBlur", 200, 200);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
	
	
	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0)).setKb(1)),
				new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1).setKg(5)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4)).setKg(1)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirroredWithGloss", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	
	
	/** Geometry combination including refraction and reflection */
	//@Test
//	public void geometryCombinationTest() {
//		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//				.setVPSize(200, 200).setVPDistance(1000);
//
//		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//		scene.geometries.add(
//				new Sphere(50d, new Point(0, 0, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKt(0.55).setKr(0.5).setKg(3)),
//				new Sphere(35d, new Point(60, 0, 0))
//						.setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4).setKt(0.2))
//						.setEmission(new Color(20, 120, 50)),
//				new Triangle(new Point(0, 20, 0), new Point(20, 70, 0), new Point(-20, 70, 0))
//						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6))
//						.setEmission(new Color(RED)));
//
//		scene.geometries.add( //
//				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
//						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
//						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
//						.setMaterial(new Material().setKd(0.2).setKs(0.2).setKb(20).setShininess(30).setKt(0.6)));
//
//		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
//				.setKl(4E-5).setKq(2E-7));
//
//		ImageWriter imageWriter = new ImageWriter("GeometryCombination", 600, 600);
//		camera.setImageWriter(imageWriter) //
//				.setRayTracer(new RayTracerBasic(scene)) //
//				.renderImage() //
//				.writeToImage();
//	}
	
	
	
	/**
	 * Mega geometry combination test including all features
	 */
	/*
	 * @Test public void megaTest() { Camera camera = new Camera(new Point(0, 0,
	 * 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) // .setVPSize(200,
	 * 200).setVPDistance(1000);
	 * 
	 * scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
	 * 
	 * scene.geometries.add( new Sphere(30d, new Point(-80, 10, -50))
	 * .setMaterial(new
	 * Material().setKd(0.3).setKs(0.5).setShininess(10).setKt(0.55).setKr(0.5)),
	 * new Sphere(35d, new Point(60, 0, 0)) .setMaterial(new
	 * Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4).setKt(0.2))
	 * .setEmission(new Color(252, 148, 3)), new Sphere(25d, new Point(-15, -40,
	 * 10)) .setMaterial(new
	 * Material().setKd(0.3).setKs(0.7).setShininess(5).setKr(0.7).setKt(0.2))
	 * .setEmission(new Color(252, 3, 252)),
	 * 
	 * new Plane(new Point(0, 160, 0), new Vector(0, 1, 0)).setMaterial(new
	 * Material().setKd(0.9).setKr(0.4)) .setEmission(new Color(170, 170, 170)),
	 * 
	 * new Polygon(new Point(-80, 80, -60), new Point(80, 80, -60), new Point(80,
	 * 100, 10), new Point(-80, 100, 10)).setMaterial(new
	 * Material().setKd(0.6).setKt(0.1)) .setEmission(new Color(3, 250, 190)),
	 * 
	 * new Triangle(new Point(60, 60, 20), new Point(100, 70, 0), new Point(70, 100,
	 * 0)) .setMaterial(new
	 * Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6)) .setEmission(new
	 * Color(250, 70, 0)),
	 * 
	 * new Triangle(new Point(-20, 20, -60), new Point(30, 70, 0), new Point(-20,
	 * 70, 0)) .setMaterial(new
	 * Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6)) .setEmission(new
	 * Color(RED)));
	 * 
	 * scene.geometries.add( // new Triangle(new Point(-150, -150, -115), new
	 * Point(150, -150, -135), new Point(75, -75, -150)) // .setMaterial(new
	 * Material().setKd(0.5).setKs(0.5).setShininess(60)), // new Triangle(new
	 * Point(-150, -150, -115), new Point(-70, -70, -140), new Point(75, -75, -150))
	 * // .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
	 * new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
	 * .setMaterial(new
	 * Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
	 * 
	 * scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50,
	 * 0), new Vector(0, 0, -1)) // .setKl(4E-5).setKq(2E-7));
	 * 
	 * ImageWriter imageWriter = new ImageWriter("MegaCombinationWithBlur", 400,
	 * 400); camera.setImageWriter(imageWriter) // .setRayTracer(new
	 * RayTracerBasic(scene)) // .setGlossAndDiffuse(70)// .renderImage() //
	 * .writeToImage(); }
	 */

}
