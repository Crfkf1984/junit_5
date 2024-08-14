package chapter;

public enum ChapterPage {
    ALL("Все"),
    NEWS("Новости"),
    VIDEO("Видео"),
    PICTURES("Картинки"),
    CARTS("Карты");

    public String tab;

    ChapterPage(String tab) {
        this.tab = tab;
    }
}