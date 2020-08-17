package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] player;
	int playerState;
	int pause = 0;
	float gravity = 0.5f;
	float velocity = 0;
	int playerY = 0;
	Rectangle playerRectangle;

	ArrayList<Integer> coinXs;
	ArrayList<Integer> coinYs;
	ArrayList<Rectangle> coinRectangles;
	Texture coin;
	int coinCount;

	ArrayList<Integer> bombXs;
	ArrayList<Integer> bombYs;
	ArrayList<Rectangle> bombRectangles;
	Texture bomb;
	int bombCount;

	int score = 0;
	BitmapFont font;
	int gameState = 0;
	Random rand;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		player = new Texture[4];
		player[0] = new Texture("frame-1.png");
		player[1] = new Texture("frame-2.png");
		player[2] = new Texture("frame-3.png");
		player[3] = new Texture("frame-4.png");
		playerState = 0;
		playerY = Gdx.graphics.getHeight()/2 - player[playerState].getHeight()/2;

		coinXs = new ArrayList<Integer>();
		coinYs = new ArrayList<Integer>();
		coinRectangles = new ArrayList<Rectangle>();
		coin = new Texture("coin.png");

		bombXs = new ArrayList<Integer>();
		bombYs = new ArrayList<Integer>();
		bombRectangles = new ArrayList<Rectangle>();
		bomb = new Texture("bomb.png");

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		rand = new Random();

	}

	public void makeCoin()
	{
		float height = rand.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	public void makeBomb()
	{
		float height = rand.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}

	@Override
	public void render ()
	{
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if(gameState == 1)
		{
			//GAME IS LIVE
			if(coinCount < 100)
				coinCount++;
			else
			{
				coinCount = 0;
				makeCoin();
			}

			coinRectangles.clear();
			for(int i = 0; i < coinXs.size(); i++)
			{
				batch.draw(coin, coinXs.get(i), coinYs.get(i));
				coinXs.set(i, coinXs.get(i) - 4);
				coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i),coin.getWidth(),coin.getHeight()));
			}

			if(bombCount < 250)
				bombCount++;
			else
			{
				bombCount = 0;
				makeBomb();
			}

			bombRectangles.clear();
			for(int i = 0; i < bombXs.size(); i++)
			{
				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
				bombXs.set(i, bombXs.get(i) - 4);
				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
			}

			if(Gdx.input.justTouched())
				velocity = -10;

			if(pause < 4)
				pause++;
			else {
				pause = 0;
				if (playerState < 3)
					playerState++;
				else
					playerState = 0;
			}

			velocity += gravity;
			playerY -= velocity;
			if(playerY <= 0)
				playerY = 0;
		}
		else if(gameState == 0)
		{
			//Waiting to start
			if(Gdx.input.justTouched())
				gameState = 1;
		}
		else if(gameState == 2)
		{
			//Game over
			if(Gdx.input.justTouched()) {
				gameState = 1;
				playerY = Gdx.graphics.getHeight() / 2 - player[playerState].getHeight() / 2;
				score = 0;
				velocity = 0;
				coinXs.clear();
				coinYs.clear();
				coinRectangles.clear();
				coinCount = 0;
				bombXs.clear();
				bombYs.clear();
				bombRectangles.clear();
				bombCount = 0;
				pause = 0;
				playerState = 0;
			}
		}

		if(gameState == 2)
		{
			batch.draw(new Texture("dizzy-1.png"), Gdx.graphics.getWidth()/2 - player[playerState].getWidth() /2, playerY);
		}
		else
		{
			batch.draw(player[playerState], Gdx.graphics.getWidth()/2 - player[playerState].getWidth() /2, playerY);
		}
		playerRectangle = new Rectangle(Gdx.graphics.getWidth()/2 - player[playerState].getWidth() /2, playerY, player[playerState].getWidth(), player[playerState].getHeight());

		for(int i = 0; i < coinXs.size(); i++)
		{
			if(Intersector.overlaps(playerRectangle, coinRectangles.get(i)))
			{
				score++;
				coinRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;
			}
		}

		for(int i = 0; i < bombXs.size(); i++)
		{
			if(Intersector.overlaps(playerRectangle, bombRectangles.get(i)))
			{
				gameState = 2;
			}
		}

		font.draw(batch,String.valueOf(score),100, 200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
