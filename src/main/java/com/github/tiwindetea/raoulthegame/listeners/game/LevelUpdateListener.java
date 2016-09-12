//////////////////////////////////////////////////////////////////////////////////
//                                                                              //
//     This Source Code Form is subject to the terms of the Mozilla Public      //
//     License, v. 2.0. If a copy of the MPL was not distributed with this      //
//     file, You can obtain one at http://mozilla.org/MPL/2.0/.                 //
//                                                                              //
//////////////////////////////////////////////////////////////////////////////////

package com.github.tiwindetea.raoulthegame.listeners.game;

import com.github.tiwindetea.raoulthegame.events.LevelUpdateEvent;

/**
 * The interface LevelUpdateListener
 * @author Maxime PINARD
 */
public interface LevelUpdateListener {
	/**
	 * Handler associated to a LevelUpdateListener
	 * @param e Event to handle
	 */
	void updateLevel(LevelUpdateEvent e);
}
