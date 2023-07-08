package unittests.renderer;

import scene.*;

public class MPtests {
	Scene scene = new Scene("Test scene");

//	/**
//	 * Mega geometry combination test including all features
//	 */
//	@Test
//	public void cityTest() {
//		Camera camera = new Camera(new Point(600, 175, 1080), new Vector(-0.548, -0.12, -1), new Vector(0, 1, -0.12)) //
//				.setVPSize(200, 200).setVPDistance(920);
//
//		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
//
//		scene.setBackground(new Color(10, 150, 180));
//
//		scene.geometries.add(
//				new Sphere(25d, new Point(105, 90, 95)).setMaterial(new Material().setKd(0.2).setKt(0.65).setKb(5))
//						.setEmission(new Color(100, 25, 25)),
//
//				new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
//						.setMaterial(new Material().setKd(0.1).setKr(0.85).setKs(0.5).setShininess(9).setKg(3))
//						.setEmission(new Color(10, 40, 80)),
//				new Plane(new Point(0, 0, 50), new Vector(0, 1, 0.001)).setMaterial(new Material().setKd(0.07))
//						.setEmission(new Color(61, 27, 9)),
//
//				new Prism(0, 0, 0, 25, 50, 30).setMaterial(new Material().setKd(0.8).setKs(0.6).setShininess(7))
//						.setEmission(Color.GRAY),
//				new Prism(37.5, 0.0, -15.0, 27.0, 72, 37.5)
//						.setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(7)).setEmission(Color.DARK_GRAY),
//				new Prism(-52.5, 0.0, -15.0, 34.5, 68.0, 19.5)
//						.setMaterial(new Material().setKd(0.6).setKs(0.6).setShininess(7)).setEmission(new Color(60)),
//				new Prism(-45.0, 0.0, -82.5, 30.0, 72.0, 27.0)
//						.setMaterial(new Material().setKd(0.7).setKs(0.8).setShininess(7)).setEmission(new Color(100)),
//				new Prism(75.0, 0.0, -45.0, 34.5, 75.0, 19.5)
//						.setMaterial(new Material().setKd(0.6).setKs(0.6).setShininess(7)).setEmission(new Color(70)),
//				new Prism(0.0, 0.0, -82.5, 27.0, 82.5, 30.0)
//						.setMaterial(new Material().setKd(0.5).setKs(0.7).setShininess(7)).setEmission(new Color(50)),
//				new Prism(-100.0, 0.0, -7.5, 30.0, 60.0, 45.0)
//						.setMaterial(new Material().setKd(0.6).setKs(0.5).setShininess(7)).setEmission(new Color(90)),
//				new Prism(-90.0, 0.0, -60.0, 34.5, 97.5, 40.5)
//						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(110)),
//
//				new Prism(-150, 0, -40, 40, 80, 45).setMaterial(new Material().setKd(0.8).setKs(0.6).setShininess(7))
//						.setEmission(new Color(70)),
//				new Prism(-10, 0, -95, 45, 120, 45).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
//						.setEmission(new Color(80)),
//				new Prism(100, 43, 90, 10, 7, 10).setMaterial(new Material().setKd(0.6).setKs(0.6).setShininess(7))
//						.setEmission(new Color(117, 68, 9)),
//
//				new Sphere(2, new Point(105, 51.5, 95)).setEmission(new Color(230, 170, 130)),
//				new Triangle(new Point(100, 50, 90), new Point(88, 85, 73), new Point(85, 85, 76))
//						.setEmission(new Color(40)),
//				new Triangle(new Point(100, 50, 100), new Point(87, 85, 112), new Point(90, 85, 115))
//						.setEmission(new Color(40)),
//				new Triangle(new Point(110, 50, 100), new Point(125, 85, 112), new Point(122, 85, 115))
//						.setEmission(new Color(40)),
//				new Triangle(new Point(110, 50, 90), new Point(125, 85, 78), new Point(122, 85, 75))
//						.setEmission(new Color(40)));
//
//		scene.lights.add(new SpotLight(new Color(1500, 1300, 3000), new Point(600, 500, 0), new Vector(0, 0, -1)) //
//				.setKl(4E-5).setKq(2E-7));
//
//		ImageWriter imageWriter = new ImageWriter("Skyline", 700, 700);
//		camera.setImageWriter(imageWriter) //
//				.setRayTracer(new RayTracerBasic(scene)) //
//				.renderImage() //
//				.writeToImage();
//	}

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
