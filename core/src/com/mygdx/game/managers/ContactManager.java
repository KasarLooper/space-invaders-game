package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.GameSettings;
import com.mygdx.game.gameObjects.GameObject;

public class ContactManager implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        short cBitsA = fixA.getFilterData().categoryBits;
        short cBitsB = fixB.getFilterData().categoryBits;
        int sum = cBitsA + cBitsB;

        if (sum == GameSettings.TRASH_BIT + GameSettings.BULLET_BIT ||
                sum == GameSettings.TRASH_BIT + GameSettings.SHIP_BIT) {
            GameObject objectA = (GameObject) fixA.getUserData();
            GameObject objectB = (GameObject) fixB.getUserData();
            objectA.hit(objectB);
            objectB.hit(objectA);
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
