package com.github.tiwindetea.dungeonoflegend.view.entities;

import com.github.tiwindetea.dungeonoflegend.model.Direction;
import com.github.tiwindetea.dungeonoflegend.model.MainPackage;
import com.github.tiwindetea.dungeonoflegend.model.Vector2i;
import com.github.tiwindetea.dungeonoflegend.view.ViewPackage;
import javafx.scene.image.Image;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by maxime on 5/5/16.
 */
public enum LivingEntityType {
	PLAYER1 {
		@Override
		public String toString() {
			return resourceBundle.getString("player1.string");
		}
	},
	PLAYER2 {
		@Override
		public String toString() {
			return resourceBundle.getString("player2.string");
		}
	},
	LITTLE_PUNK_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("little-punk-duck.string");
		}
	},
	PUNK_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("punk-duck.string");
		}
	},
	LITTLE_ARISTOCRAT_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("little-aristocrat-duck.string");
		}
	},
	ARISTOCRAT_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("aristocrat-duck.string");
		}
	},
	LITTLE_MONSTER_TRUCK_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("little-monster-truck-duck.string");
		}
	},
	MONSTER_TRUCK_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("monster-truck-duck.string");
		}
	},
	LITTLE_SPACE_SHIP_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("little-space-ship-duck.string");
		}
	},
	SPACE_SHIP_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("space-ship-duck.string");
		}
	},
	LITTLE_SATANIC_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("little-satanic-duck.string");
		}
	},
	SATANIC_DUCK {
		@Override
		public String toString() {
			return resourceBundle.getString("satanic-duck.string");
		}
	};

	private final static String bundleName = MainPackage.name + ".LivingEntity";

	private final static ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, Locale.getDefault());

	@Override
	public String toString() {
		return null;
	}

	public Vector2i getSpriteDownPosition() {
		return new Vector2i(Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.down.position.x")),
		  Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.down.position.y")));
	}

	public Vector2i getSpriteUpPosition() {
		return new Vector2i(Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.up.position.x")),
		  Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.up.position.y")));
	}

	public Vector2i getSpriteRightPosition() {
		return new Vector2i(Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.right.position.x")),
		  Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.right.position.y")));
	}

	public Vector2i getSpriteLeftPosition() {
		return new Vector2i(Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.left.position.x")),
		  Integer.parseInt(resourceBundle.getString(this.toString() + ".sprite.left.position.y")));
	}

	public Vector2i getSpritePosition(Direction direction) {
		switch(direction) {
		case UP:
			return this.getSpriteUpPosition();
		case LEFT:
			return this.getSpriteLeftPosition();
		case RIGHT:
			return this.getSpriteRightPosition();
		case DOWN:
			/* Falls through */
		default:
			return this.getSpriteDownPosition();
		}
	}

	public Image getImage() {
		switch(resourceBundle.getString(this.toString() + ".sprites.file")) {
		case "Objects.png":
			return ViewPackage.objectsImage;
		case "Players.png":
			return ViewPackage.playersImage;
			case "Mobs.png":
				return ViewPackage.mobsImage;
		default:
			return ViewPackage.playersImage; // for tests
		//return null;
		}
	}

	public static LivingEntityType parseLivingEntity(String str) {
		str.toLowerCase();
		if (PLAYER1.toString().equals(str)) {
			return PLAYER1;
		} else if (PLAYER2.toString().equals(str)) {
			return PLAYER2;
		} else if (LITTLE_PUNK_DUCK.toString().equals(str)) {
			return LITTLE_PUNK_DUCK;
		} else if (PUNK_DUCK.toString().equals(str)) {
			return PUNK_DUCK;
		} else if (LITTLE_ARISTOCRAT_DUCK.toString().equals(str)) {
			return LITTLE_ARISTOCRAT_DUCK;
		} else if (ARISTOCRAT_DUCK.toString().equals(str)) {
			return ARISTOCRAT_DUCK;
		} else if (LITTLE_MONSTER_TRUCK_DUCK.toString().equals(str)) {
			return LITTLE_MONSTER_TRUCK_DUCK;
		} else if (MONSTER_TRUCK_DUCK.toString().equals(str)) {
			return MONSTER_TRUCK_DUCK;
		} else if (LITTLE_SPACE_SHIP_DUCK.toString().equals(str)) {
			return LITTLE_SPACE_SHIP_DUCK;
		} else if (SPACE_SHIP_DUCK.toString().equals(str)) {
			return SPACE_SHIP_DUCK;
		} else if (LITTLE_SATANIC_DUCK.toString().equals(str)) {
			return LITTLE_SATANIC_DUCK;
		} else if (SATANIC_DUCK.toString().equals(str)) {
			return SATANIC_DUCK;
		} else {
			return null;
		}
	}
}
