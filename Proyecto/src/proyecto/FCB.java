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
public class FCB
{
    private int sizeArchivo;
    private int sizeBloques;
    private char[] texto;
    
    private ArrayList<Bloque> bloques;
    private ArrayList<Integer> pos;
    
    public FCB(int sizeBloques, int sizeArchivo)
    {
        this.sizeBloques = sizeBloques;
        this.sizeArchivo = sizeArchivo;
        this.bloques = new ArrayList<>();
        this.pos = new ArrayList<>();
        
        this.texto = new char[sizeBloques];
        
        for(int i = 0; i < sizeBloques; i++)
        {
            this.texto[i] = ' ';
        }
    }    

    public int getSizeArchivo()
    {
        return sizeArchivo;
    }

    public void setSizeArchivo(int sizeArchivo)
    {
        this.sizeArchivo = sizeArchivo;
    }

    public int getSizeBloques()
    {
        return sizeBloques;
    }

    public void setSizeBloques(int sizeBloques)
    {
        this.sizeBloques = sizeBloques;
    }

    public char[] getTexto()
    {
        return texto;
    }

    public void setTexto(char[] texto)
    {
        this.texto = texto;
    }

    public ArrayList<Bloque> getBloques()
    {
        return bloques;
    }

    public void setBloques(ArrayList<Bloque> bloques)
    {
        this.bloques = bloques;
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
