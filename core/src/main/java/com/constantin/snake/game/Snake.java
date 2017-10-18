package com.constantin.snake.game;

import com.constantin.snake.util.Location;

import java.util.ArrayList;

public class Snake {

    private ArrayList<Location> snake;
    private char direction;
    private int maxX, maxY = -1;

    Snake(Location startLocation, int startLength, int maxX, int maxY){
        System.out.print(maxX + ", " + maxY);
        this.maxX = maxX;
        this.maxY = maxY;
        snake = new ArrayList<>();
        snake.add(startLocation);
        for(int i = 1; i <= startLength; ++i){
            snake.add(new Location(startLocation.getX()-i, startLocation.getY()));
        }
        direction = 'R';
    }

    void incrementSnake(){
        snake.add(snake.get(snake.size()-1).clone());
    }

    void moveSnake(){
        switch (direction){
            case 'R':
                snake.add(0, new Location(snake.get(0).getX()+1 > maxX ? 1 : snake.get(0).getX()+1, snake.get(0).getY()));break;
            case 'L':
                snake.add(0, new Location(snake.get(0).getX()-1 < 1 ? maxX : snake.get(0).getX()-1, snake.get(0).getY()));break;
            case 'U':
                snake.add(0, new Location(snake.get(0).getX(), snake.get(0).getY()-1 < 1 ? maxY : snake.get(0).getY()-1));break;
            case 'D':
                snake.add(0, new Location(snake.get(0).getX(), snake.get(0).getY()+1 > maxY ? 1 : snake.get(0).getY()+1));break;
        }
        snake.remove(snake.size()-1);
    }

    void setDirection(char direction){
        this.direction = direction;
        System.out.print(direction);
    }

    char getDirection() {
        return direction;
    }

    public ArrayList<Location> getSnake() {
        return snake;
    }
}
