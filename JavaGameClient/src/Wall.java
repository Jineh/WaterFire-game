import java.awt.Image;
import java.util.Random;

public class Wall {

	Image img; //이미지 참조변수

	int myx, myy; //이미지 중심 좌표

	int w, h; //이미지 절반폭, 절반높이

	

	int dy; //적군의 변화량

	

	int width, height; //화면(panel)의 사이즈

	//본인 객체가 죽었는지 여부!

	boolean isDead = false;

	

	public Wall(Image wall, int x, int y) {

		//this.width = width;

		//this.height = height;

		

		//멤버변수 값 초기화..

		img = wall.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		w = 25; //이미지 절반넓이

		h = 25;
		
		myx=x;
		myy=y;

				

		

	}

	

	/*void move() { //Enemy의 움직이는 기능 메소드

		y += dy;

		//만약 화면 밑으로 나가버리면 객체 없애기

		if( y > height + h ) { //ArrayList에서 제거

			isDead = true; //죽음 표식! 

		}

	}*/

}
