package items.potions;

import items.Item;

public interface Potion extends Item {
    int getQuantity();
    void setQuantity(int quantity);
    int getQuality();
}
