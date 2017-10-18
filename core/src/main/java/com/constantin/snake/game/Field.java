package com.constantin.snake.game;

import com.constantin.snake.util.Location;

import java.util.ArrayList;
import java.util.Random;

public class Field {

    private int maxX, maxY = -1;
    private Snake snake;
    private Location apple;

    public Field(int maxX, int maxY) {
        startGame(maxX, maxY);
    }

    private void startGame(int maxX, int maxY){
        this.maxX = maxX;
        this.maxY = maxY;
        snake = new Snake(new Location(maxX >> 1, maxY >> 1), 4, maxX, maxY);
        createNewApple();
    }

    private void createNewApple(){
        Random r = new Random();
        apple = null;
        while(apple == null) {
            apple = new Location(r.nextInt(maxX-1) + 1, r.nextInt(maxY-1) + 1);
            switch (snake.getDirection()){
                case 'R':
                    if(snake.getSnake().get(0).getY()+1 == apple.getY() && snake.getSnake().get(0).getX() == apple.getX()){
                        apple = null;
                    }break;
                case 'L':
                    if(snake.getSnake().get(0).getY()-1 == apple.getY() && snake.getSnake().get(0).getX() == apple.getX()){
                        apple = null;
                    }break;
                case 'D':
                    if(snake.getSnake().get(0).getX()+1 == apple.getX() && snake.getSnake().get(0).getY() == apple.getY()){
                        apple = null;
                    }break;
                case 'U':
                    if(snake.getSnake().get(0).getX()-1 == apple.getX() && snake.getSnake().get(0).getY() == apple.getY()){
                        apple = null;
                    }break;
            }
            for (Location location : snake.getSnake()) {
                if (location.equals(apple)) {
                    apple = null;
                }
            }
        }

    }

    public boolean logic(char direction){
        setDirection(direction);
        snake.moveSnake();
        return checkCollisions();
    }

    private boolean checkCollisions(){
        boolean collision = false;
        ArrayList<Location> snakeLocations = snake.getSnake();
        for(int i = 1; i < snakeLocations.size(); ++i){
            collision = snakeLocations.get(0).equals(snakeLocations.get(i)) || collision;
        }
        if(snakeLocations.get(0).equals(apple)){
            snake.incrementSnake();
            createNewApple();
        }
        return collision;
    }

    private void setDirection(char direction){
        if(direction != 'n' && snake.getDirection() != direction){
            switch (direction){
                case 'R':
                    if(snake.getDirection() != 'L'){
                        snake.setDirection(direction);
                    }
                    break;
                case 'L':
                    if(snake.getDirection() != 'R'){
                        snake.setDirection(direction);
                    }
                    break;
                case 'D':
                    if(snake.getDirection() != 'U'){
                        snake.setDirection(direction);
                    }
                    break;
                case 'U':
                    if(snake.getDirection() != 'D'){
                        snake.setDirection(direction);
                    }
                    break;
            }
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public Location getApple() {
        return apple;
    }

    public int getMaxX(){
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
