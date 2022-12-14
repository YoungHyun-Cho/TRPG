package items.potions;

public class MpPotion implements Potion {
    private String name = "MP 포션";
    private final int quality;
    private int quantity;

    public MpPotion(int quality, int quantity) {
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

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
