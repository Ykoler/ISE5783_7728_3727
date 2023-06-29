package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;
import renderer.*;
import lighting.*;
import geometries.*;

public class MPtests {
	Scene scene = new Scene("Test scene");
	

	/**
	 * Mega geometry combination test including all features
	 */
	@Test
	public void megaTest() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add(
				new Sphere(10d, new Point(-80, 150, 120))
						.setMaterial(new Material().setKr(0.5)// .setKt(0.55)
								.setKg(10)),
				new Sphere(35d, new Point(60, 0, 0))
						.setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4).setKt(0.2))
						.setEmission(new Color(252, 148, 3)),
				new Sphere(25d, new Point(-15, -40, 10))
						.setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKr(0.7).setKt(0.2))
						.setEmission(new Color(252, 3, 252)),

				new Polygon(new Point(-251, -251, -150), new Point(251, -251, -150), new Point(251, 251, -150),
						new Point(-251, 251, -150))
						.setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4)),

				new Polygon(new Point(-80, 60, -60), new Point(80, 60, -60), new Point(80, 100, 10),
						new Point(-80, 100, 10)).setMaterial(new Material().setKd(0.6).setKr(0.4))
						.setEmission(new Color(3, 250, 190)),

				new Triangle(new Point(-10, 20, 100), new Point(100, 10, 100), new Point(50, 100, 100))
						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6).setKb(5))
						.setEmission(new Color(250, 70, 0)),

				new Triangle(new Point(-20, 20, -60), new Point(30, 70, 0), new Point(-20, 70, 0))
						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6))
						.setEmission(new Color(java.awt.Color.RED)));

		scene.geometries.add( //
				new Triangle(new Point(-160, -160, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-160, -160, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("MPCombination", 800, 800);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
	
	
//	/**
//	 * Mega geometry combination test including all features
//	 */
//	@Test
//	public void megaTest() {
//		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//				.setVPSize(200, 200).setVPDistance(1000);
//
//		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
//
//		scene.geometries.add(
//				new Sphere(30d, new Point(-80, 10, -50))
//						.setMaterial(new Material().setKd(0.3).setKs(0.5).setShininess(10).setKr(0.5)// .setKt(0.55)
//								.setKg(10)),
//				new Sphere(35d, new Point(60, 0, 0))
//						.setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4).setKt(0.2))
//						.setEmission(new Color(252, 148, 3)),
//				new Sphere(25d, new Point(-15, -40, 10))
//						.setMaterial(new Material().setKd(0.3).setKs(0.7).setShininess(5).setKr(0.7).setKt(0.2))
//						.setEmission(new Color(252, 3, 252)),
//
//				new Polygon(new Point(-251, -251, -150), new Point(251, -251, -150), new Point(251, 251, -150),
//						new Point(-251, 251, -150))
//						.setMaterial(new Material().setKd(0.6).setKs(0.2).setShininess(3).setKr(0.4)),
//
//				new Polygon(new Point(-80, 60, -60), new Point(80, 60, -60), new Point(80, 100, 10),
//						new Point(-80, 100, 10)).setMaterial(new Material().setKd(0.6).setKr(0.4))
//						.setEmission(new Color(3, 250, 190)),
//
//				new Triangle(new Point(-10, 20, 100), new Point(100, 10, 100), new Point(50, 100, 100))
//						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6).setKb(5))
//						.setEmission(new Color(250, 70, 0)),
//
//				new Triangle(new Point(-20, 20, -60), new Point(30, 70, 0), new Point(-20, 70, 0))
//						.setMaterial(new Material().setKd(0.2).setKs(0.6).setShininess(8).setKt(0.6))
//						.setEmission(new Color(java.awt.Color.RED)));
//
//		scene.geometries.add( //
//				new Triangle(new Point(-160, -160, -115), new Point(150, -150, -135), new Point(75, -75, -150)) //
//						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//				new Triangle(new Point(-160, -160, -115), new Point(-70, -70, -140), new Point(75, -75, -150)) //
//						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
//				new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
//
//		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
//				.setKl(4E-5).setKq(2E-7));
//
//		ImageWriter imageWriter = new ImageWriter("MPCombination", 800, 800);
//		camera.setImageWriter(imageWriter) //
//				.setRayTracer(new RayTracerBasic(scene)) //
//				.renderImage() //
//				.writeToImage();
//	}
}
