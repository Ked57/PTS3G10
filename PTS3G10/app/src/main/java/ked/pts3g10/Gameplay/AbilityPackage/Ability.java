package ked.pts3g10.Gameplay.AbilityPackage;


import ked.pts3g10.Interface.Case;

public abstract class Ability {
    protected int id;
    protected String name;
    protected String description;
    protected Case target;

    public Ability(int id, String name, String description, Case target){
        this.id = id;
        this.name = name;
        this.description = description;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Case getTarget() {
        return target;
    }
}
