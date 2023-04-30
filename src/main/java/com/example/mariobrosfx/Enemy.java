package com.example.mariobrosfx;

public abstract class Enemy extends AnimatedSprite
{
    protected int lives;
    protected int currentSpeed;
    protected int points;
    protected boolean isVulnerable;

    public Enemy(int width, int height)
    {
        super(width, height);
    }

    public void move()
    {

    }

    public void recover()
    {

    }
}