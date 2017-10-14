package ked.pts3g10.Gameplay.AbilityPackage;


import ked.pts3g10.Interface.Case;

public abstract class NormalAbility extends Ability {

    protected int radius;
    protected int amount;

    public NormalAbility(int id, String name, String description, int radius, int amount, Case target) {
        super(id,name,description,target);
        this.radius = radius;
        this.amount = amount;
    }

    public int getRadius() {
        return radius;
    }

    public int getAmount() {
        return amount;
    }
}
