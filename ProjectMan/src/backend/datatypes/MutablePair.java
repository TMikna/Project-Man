package backend.datatypes;

public class MutablePair<K, V>  //had to write my own Pair class because the only one that is included in the default java package is immutable
{
    private K key;
    private V value;
    
    public MutablePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
    
    public K getKey()
    {
        return key;
    }
    
    public void setKey(K key)
    {
        this.key = key;
    }
    
    public V getValue()
    {
        return value;
    }
    
    public void setValue(V value)
    {
        this.value = value;
    }
}