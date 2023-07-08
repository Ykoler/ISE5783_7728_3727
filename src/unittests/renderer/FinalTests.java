package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.*;
import scene.*;
import renderer.*;
import lighting.*;
import geometries.*;

public class FinalTests {
	Scene scene = new Scene("Test scene");

	/**
	 * Mega geometry combination test including all features
	 */
	@Test
	public void snowmenTest() {
		final double floorHeight = 16;
		final double floorLength = 120;
		final double floorWidth = 100;
		final double ceilingHeight = 240;
		final double eps = 0.42;
		final Color smileColor = new Color(90, 20, 10);
		final double smileCircleRadius = 2;

		Camera camera = new Camera(new Point(0, 170, 950), new Vector(0, -0.1, -1), new Vector(0, 1, -0.1)) //
				.setVPSize(200, 200).setVPDistance(800).setMultiThreading(3).setDebugPrint(0.2);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.16));

		scene.setBackground(Color.BLACK);

		scene.geometries.add(
		// Walls
//				new Plane(new Point(-floorLength - floorHeight, 0, 0), new Vector(1, 0, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(9))
//						.setEmission(new Color(240, 175, 170)),
//				new Plane(new Point(floorLength + floorHeight, 0, 0), new Vector(-1, 0, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(9))
//						.setEmission(new Color(170, 215, 250)),
//				new Plane(new Point(0, -floorHeight, 0), new Vector(0, 1, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(9)).setEmission(new Color(100)),
//				new Plane(new Point(0, 201 + floorHeight, 0), new Vector(0, -1, 0))
//						.setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(9)).setEmission(new Color(150)),
				new Plane(new Point(0, 0, -floorWidth), new Vector(0, 0, 1))
						.setMaterial(new Material().setKr(1).setKg(0)),
				new Plane(new Point(0, 0, floorWidth), new Vector(0, 0, -1))
						.setMaterial(new Material().setKt(0.8).setKr(1)).setEmission(new Color(5)),

				// CubeMan body
				new Prism(-75, 0, -25, 50, 50, 50).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(90)),
				new Prism(-70, 50, -20, 40, 40, 40).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(110)),
				new Prism(-65, 90, -15, 30, 30, 30).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(130)),

				// CubeMan arms
				new Prism(-30, 70, -7, 24, 14, 14, true)
						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(70)),
				new Prism(-70, 70, -7, 14, 24, 14, true)
						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(70)),

				new Prism(-19, 83, -6, 20, 12, 12, true)
						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(95)),
				new Prism(-81, 81, -6, 12, 20, 12, true)
						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(95)),

				new Prism(-10, 93, -5, 12.5, 10, 10, true)
						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(115)),
				new Prism(-89, 90, -5, 10, 15, 10, true)
						.setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7)).setEmission(new Color(115)),

				// CubeMan eyes
				new Prism(-58, 110, 15, 3, 3, 3).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(3)),
				new Prism(-45, 110, 15, 3, 3, 3).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(3)),

				// CubeMan smile
				new Prism(-60, 96, 15, 20, 3, 3).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(3)),
				new Prism(-60, 99, 15, 3, 3.5, 3).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(3)),
				new Prism(-43, 99, 15, 3, 3.5, 3).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(7))
						.setEmission(new Color(3)),

				// SphereMan body
				new Sphere(28d, new Point(50, 28, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(247, 88, 40)),
				new Sphere(23d, new Point(50, 76, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(35, 85, 45)),
				new Sphere(18d, new Point(50, 114, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(40, 75, 100)),

				// SphereMan arms
				new Sphere(10d, new Point(20, 85, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(130, 23, 53)),
				new Sphere(10d, new Point(80, 85, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(130, 23, 53)),

				new Sphere(7d, new Point(8, 96, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(112, 100, 9)),
				new Sphere(7d, new Point(90, 96, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(112, 100, 9)),

				new Sphere(4d, new Point(0, 101, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(103, 50, 128)),
				new Sphere(4d, new Point(97, 101, 0))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(103, 50, 128)),

				// SphereMan eyes
				new Sphere(2.5, new Point(43, 120, 15))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(99, 67, 26)),
				new Sphere(2.5, new Point(57, 120, 15))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(new Color(99, 67, 26)),

				// SphereMan smile
				new Sphere(smileCircleRadius, new Point(40, 106, 15))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(smileColor),
				new Sphere(smileCircleRadius, new Point(44, 104, 16))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(smileColor),
				new Sphere(smileCircleRadius, new Point(48, 102, 17))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(smileColor),
				new Sphere(smileCircleRadius, new Point(52, 102, 17))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(smileColor),
				new Sphere(smileCircleRadius, new Point(56, 104, 16))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(smileColor),
				new Sphere(smileCircleRadius, new Point(60, 106, 15))
						.setMaterial(new Material().setKd(0.2).setKt(0.45).setKs(0.5).setShininess(10))
						.setEmission(smileColor));

		Geometries floor = new Geometries();
		int len = (int) Math.floor(floorLength / floorHeight);
		int wid = (int) Math.floor(floorWidth / floorHeight);

		double r = floorHeight / 2;

		// Floor spheres
		for (int i = 0; i < 2 * len + 1; i++) {
			for (int j = 0; j < 3 * wid; j++) {
				if ((i + j) % 2 == 0) {
					floor.add(new Sphere(r,
							new Point(-floorLength + i * floorHeight + r, -r, -floorWidth + j * floorHeight + r))
							.setMaterial(new Material().setKd(0.5)).setEmission(new Color(90, 20, 10)));
				}
			}
		}
		// Floor cubes
		for (int i = 0; i < 2 * len + 1; i++) {
			for (int j = 0; j < 3 * wid; j++) {
				if ((i + j) % 2 != 0) {
					floor.add(new Prism(-floorLength + i * floorHeight + eps, -floorHeight + eps,
							-floorWidth + j * floorHeight + eps, floorHeight - 2 * eps, floorHeight - 2 * eps,
							floorHeight - 2 * eps).setMaterial(new Material().setKd(0.5)).setEmission(new Color(50)));
				}
			}
		}

//		// Ceiling spheres
//		for (int i = 0; i < 2 * len + 1; i++) {
//			for (int j = 0; j < 3 * wid; j++) {
//				if ((i + j) % 2 == 0) {
//					floor.add(new Sphere(r,
//							new Point(-floorLength + i * floorHeight + r, ceilingHeight - r,
//									-floorWidth + j * floorHeight + r))
//							.setMaterial(new Material().setKd(0.5)).setEmission(new Color(90, 20, 10)));
//				}
//			}
//		}
//		// Ceiling cubes
//		for (int i = 0; i < 2 * len + 1; i++) {
//			for (int j = 0; j < 3 * wid; j++) {
//				if ((i + j) % 2 != 0) {
//					floor.add(new Prism(-floorLength + i * floorHeight + eps, ceilingHeight - floorHeight + eps,
//							-floorWidth + j * floorHeight + eps, floorHeight - 2 * eps, floorHeight - 2 * eps,
//							floorHeight - 2 * eps).setMaterial(new Material().setKd(0.5)).setEmission(new Color(50)));
//				}
//			}
//		}

//		// Left wall spheres
//		for (int i = 0; i < 2 * len + 1; i++) {
//			for (int j = 0; j < 3 * wid; j++) {
//				if ((i + j) % 2 != 0) {
//					floor.add(new Sphere(r,
//							new Point(-floorLength - floorHeight / 2, floorHeight * i - r,
//									-floorWidth + j * floorHeight + floorHeight / 2))
//							.setMaterial(new Material().setKd(0.5)).setEmission(new Color(90, 20, 10)));
//				}
//			}
//		}
//		// Left wall cubes
//		for (int i = 0; i < 2 * len + 1; i++) {
//			for (int j = 0; j < 3 * wid; j++) {
//				if ((i + j) % 2 == 0) {
//					floor.add(new Prism(-floorLength - floorHeight, -floorHeight + i * floorHeight + eps,
//							-floorWidth + j * floorHeight + eps, floorHeight - 2 * eps, floorHeight - 2 * eps,
//							floorHeight - 2 * eps).setMaterial(new Material().setKd(0.5)).setEmission(new Color(50)));
//				}
//			}
//		}
//
//		// Right wall spheres
//		for (int i = 0; i < 2 * len + 1; i++) {
//			for (int j = 0; j < 3 * wid; j++) {
//				if ((i + j) % 2 != 0) {
//					floor.add(new Sphere(r,
//							new Point(floorLength - floorHeight / 2, floorHeight * i - r,
//									-floorWidth + j * floorHeight + floorHeight / 2))
//							.setMaterial(new Material().setKd(0.5)).setEmission(new Color(90, 20, 10)));
//				}
//			}
//		}
//		// Right wall cubes
//		for (int i = 0; i < 2 * len + 1; i++) {
//			for (int j = 0; j < 3 * wid; j++) {
//				if ((i + j) % 2 == 0) {
//					floor.add(new Prism(floorLength - floorHeight, -floorHeight + i * floorHeight + eps,
//							-floorWidth + j * floorHeight + eps, floorHeight - 2 * eps, floorHeight - 2 * eps,
//							floorHeight - 2 * eps).setMaterial(new Material().setKd(0.5)).setEmission(new Color(50)));
//				}
//			}
//		}
		scene.geometries.add(floor);

		scene.lights.add(
				new SpotLight(new Color(200), new Point(0, ceilingHeight - 60, 40), new Vector(0, -1, -0.2)) //
						.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("Snowmen", 450, 450);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}
}
