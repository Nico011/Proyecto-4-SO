/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.ArrayList;

/**
 *
 * @author Nicolas
 */
public class Directorio
{
    private ArrayList<String> nombres;
    private ArrayList<Integer> pos;
    
    public Directorio()
    {
        this.pos = new ArrayList<Integer>();
        this.nombres = new ArrayList<String>();
    }
    
    public Directorio(ArrayList<Integer> pos, ArrayList<String> nombres)
    {
        this.pos = pos;
        this.nombres = nombres;
    }

    public ArrayList<String> getNombres()
    {
        return nombres;
    }

    public void setNombres(ArrayList<String> nombres)
    {
        this.nombres = nombres;
    }

    public ArrayList<Integer> getPos()
    {
        return pos;
    }

    public void setPos(ArrayList<Integer> pos)
    {
        this.pos = pos;
    }
    
    
}
