import java.awt.Image;
import java.util.Random;

public class Wall {

	Image img; //�̹��� ��������

	int myx, myy; //�̹��� �߽� ��ǥ

	int w, h; //�̹��� ������, ���ݳ���

	

	int dy; //������ ��ȭ��

	

	int width, height; //ȭ��(panel)�� ������

	//���� ��ü�� �׾����� ����!

	boolean isDead = false;

	

	public Wall(Image wall, int x, int y) {

		//this.width = width;

		//this.height = height;

		

		//������� �� �ʱ�ȭ..

		img = wall.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

		w = 25; //�̹��� ���ݳ���

		h = 25;
		
		myx=x;
		myy=y;

				

		

	}

	

	/*void move() { //Enemy�� �����̴� ��� �޼ҵ�

		y += dy;

		//���� ȭ�� ������ ���������� ��ü ���ֱ�

		if( y > height + h ) { //ArrayList���� ����

			isDead = true; //���� ǥ��! 

		}

	}*/

}
