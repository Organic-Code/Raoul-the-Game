package com.github.tiwindetea.dungeonoflegend;

/**
 * Created by organic-code on 4/17/16.
 */
public class Vector2i {
    public int x, y;

    public Vector2i() {
    }

    public Vector2i(Vector2i v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2i(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2i vect) {
        return this.x == vect.x && this.y == vect.y;
    }

	public void add(Vector2i vect) {
		this.x += vect.x;
		this.y += vect.y;
	}

	public void add(Direction dir) {
		switch(dir) {
		case UP:
			--this.y;
			break;
		case DOWN:
			++this.y;
			break;
		case LEFT:
			--this.x;
			break;
		case RIGHT:
			++this.x;
			break;
		}
	}
}
