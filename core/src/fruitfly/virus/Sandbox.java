package fruitfly.virus;

import com.badlogic.gdx.math.MathUtils;

public class Sandbox {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while (true) {

			System.out.println(MathUtils.cos(MathUtils.PI * 0.5f - 0.3f));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
