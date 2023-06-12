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
						.setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKt(0.8))// .setKr(0.2))
						.setEmission(new Color(RED)),
				new Triangle(new Point(0, 0, 50), new Point(0, 60, 50), new Point(80, 0, 50)).setMaterial(new Material().setKd(0.1).setKt(0.3).setKb(10)));
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
//	@Test
//	public void TestCombo() {
//		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//				.setVPSize(200, 200).setVPDistance(1000);
//
//		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//		scene.geometries.add(
//				new Sphere(50d, new Point(0, 0, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKt(0.7).setKb(50))// .setKr(0.2))
//						.setEmission(new Color(BLACK)),
//				new Plane(new Point(100, 0, -100), new Vector(-1, 0, 1))
//						.setMaterial(new Material().setKd(0.3).setKr(0.8)).setEmission(new Color(GREEN)));
//
//		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
//				.setKl(4E-5).setKq(2E-7));
//
//		scene.setBackground(new Color(0, 150, 120));
//
//		ImageWriter imageWriter = new ImageWriter("TestComboWithBlur", 200, 200);
//		camera.setImageWriter(imageWriter) //
//				.setRayTracer(new RayTracerBasic(scene)) //
//				.renderImage() //
//				.writeToImage();
//	}

//	@Test
//	public void geometryCombinationTest() {
//		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//				.setVPSize(200, 200).setVPDistance(1000);
//
//		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
//
//		scene.geometries.add(
//				new Sphere(50d, new Point(0, 0, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKt(0.55).setKr(0.5)),
//				new Sphere(35d, new Point(60, 0, 0))
//						.setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4))// .setKt(0.2))
//						.setEmission(new Color(20, 120, 50)),
//				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
//						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)));// .setKt(0.6)));
//
////		scene.geometries.add(
////				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
////						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
////				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
////						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
////						new Triangle(new Point(0, 20, 0), new Point(20, 70, 0), new Point(-20, 70, 0))
////						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6))
////						.setEmission(new Color(RED)));
//
//		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)).setKl(4E-5)
//				.setKq(2E-7));
//
//		ImageWriter imageWriter = new ImageWriter("GeometryCombinationWithBlur", 300, 300);
//		camera.setImageWriter(imageWriter).setRayTracer(new RayTracerBasic(scene)).setGlossAndDiffuse(70).renderImage() //
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
