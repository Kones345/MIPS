package server;

import objects.Entity;
import objects.Pellet;
import utils.Map;

import java.util.HashMap;

public interface Telemeters {
    
    Entity[] getAgents();
    
    Map getMap();
    
    void startAI();
    
    HashMap<String, Pellet> getPellets();
    
}