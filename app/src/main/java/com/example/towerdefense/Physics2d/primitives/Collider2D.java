package com.example.towerdefense.Physics2d.primitives;


import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

public abstract class Collider2D {

    public Rigidbody2D body;

    public void update() {
        body.update();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public abstract void draw(@NotNull Canvas canvas);

    public abstract Vector2f layoutSize();

    @NonNull
    public abstract Collider2D clone();
}
