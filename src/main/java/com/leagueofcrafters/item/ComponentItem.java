package com.leagueofcrafters.item;

import java.util.Map;

public class ComponentItem extends LeagueItem {
    public ComponentItem(Properties properties) {
        this(properties, Map.of());
    }

    public ComponentItem(Properties properties, Map<String, Float> stats) {
        super(properties.stacksTo(1), stats);
    }
}
