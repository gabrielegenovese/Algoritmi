package mnkgame;

import java.util.ArrayList;
import java.util.List;

public class Albero {

    private float value;
    private List children;
    private Albero parent;

    public Albero(float value) {
        this.value = value;
        children = new ArrayList();
    }
    
    public void addChild(Albero child) {
        child.setParent(this);
        children.add(child);
    }
    
    public List getChildren() {
        return children;
    }
    
    public float getValue() {
        return value;
    }
    
    public void setValue(float value) {
        this.value = value;
    }
    
    private void setParent(Albero parent) {
        this.parent = parent;
    }
    
    public Albero getParent() {
        return parent;
    }
 
    public boolean isLeaf() {
        if(children.size() == 0)
            return true;
        else
            return false;
    }
}
