package items.potions;

public class HpPotion implements Potion {

    private String name = "HP포션";
    private int quality;

    private int quantity;

    public HpPotion(int quality, int quantity) {
        this.quality = quality;
        this.quantity = quantity;
    }

    @Override
    public int getQuality() {
        return quality;
    }

    @Override
    public String getName() {
        return name;
    }

    public int useItem() {
        return quality;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


// 이지모드, 노말모드, 하드모드, 헬모드에 따라 각 아이템 능력치 다르게 할 수 있도록