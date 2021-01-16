package main.java.xml.builder;

import main.java.xml.entity.Gem;

import java.util.HashSet;
import java.util.Set;

public abstract class GemBuilder {
    protected Set<Gem> gems;

    public GemBuilder() {
        gems = new HashSet<Gem>();
    }

    public Set<Gem> getGems() {
        return gems;
    }

    abstract public void buildGemSet(String fileName);

}
