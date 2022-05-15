package ru.potatocoder228.itmo.lab6.data;


/**
 * Класс, описывающий пещеру дракона
 */

public class DragonCave {
    private final float depth;

    /**
     * Конструктор, задающий координаты
     *
     * @param depth глубина пещеры
     */

    public DragonCave(float depth) {
        this.depth = depth;
    }

    /**
     * Возвращает глубину пещеры
     *
     * @return depth
     */

    public float getDepth() {
        return depth;
    }
}