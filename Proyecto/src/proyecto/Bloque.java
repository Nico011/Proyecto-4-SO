/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author Nicolas
 */
public class Bloque
{
    private char[] texto;
    private int id;
    
    public Bloque(int sizeBloque)
    {
        this.id = -1;
        this.texto = new char[sizeBloque];
        inicializarVacio(sizeBloque);
        
    }
    
    public Bloque(int sizeBloque, char[] texto)
    {
        this.texto = new char[sizeBloque];
        inicializarConTexto(sizeBloque, texto);
    }
    
    public Bloque(int sizeBloque, int id)
    {
        this.id = id;
        this.texto = new char[sizeBloque];
        
        for(int i = 0; i < sizeBloque; i++)
        {
            this.texto[i] = '-';
        }
    }
    
    public Bloque(int sizeBloque, int bytes, int id)
    {
        this.id = id;
        this.texto = new char[sizeBloque];
        
        for(int i = 0; i < sizeBloque; i++)
        {
            if(i < bytes)
            {
                this.texto[i] = '-';
            }
            else
            {
                this.texto[i] = ' ';
            }
            
        }
    }
    
    public void inicializarVacio(int sizeBloque)
    {
        for(int i = 0; i < sizeBloque; i++)
        {
            this.texto[i] = ' ';
        }
    }
    
    public void inicializarConTexto(int sizeBloque, char[] texto)
    {
        for(int i = 0; i < sizeBloque; i++)
        {
            if(i < texto.length)
            {
                this.texto[i] = texto[i];
            }
            else 
            {
                this.texto[i] = '-';
            }
        }
    }
    
    public char[] getTexto()
    {
        return texto;
    }
    
    public void setTexto(char[] texto)
    {
        this.texto = texto;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
}

