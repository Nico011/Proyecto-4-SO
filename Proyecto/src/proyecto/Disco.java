/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nicolas
 */
public class Disco
{
    private File archivoDisco;
    private int numBloques;
    private int sizeBloques;
    
    public Disco(int numBloques, int sizeBloques)
    {
        this.numBloques = numBloques;
        this.archivoDisco = new File("Disco");
        this.sizeBloques = sizeBloques;
    }
    
    public void iniciarDisco()
    {
        FileWriter fw = null;
        PrintWriter pw = null;
        try
        {
            fw = new FileWriter(this.archivoDisco);
            pw = new PrintWriter(fw);
            Bloque b = new Bloque(this.sizeBloques);
            
            for(int i = 0; i < this.numBloques; i++)
            {
                pw.println(b.getTexto());
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if(fw != null)
                {
                    fw.close();
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    public Bloque leerBloque(int numBloque)
    {
        if(this.numBloques >= numBloque && numBloque >= 0)
        {
            Bloque b = new Bloque(this.sizeBloques);
            Scanner sc;
            try
            {
                sc = new Scanner(this.archivoDisco);
                
                for(int i = 0; i < numBloques && sc.hasNextLine(); i++)
                {
                    char linea[] = sc.nextLine().toCharArray();
                    if(i == numBloque)
                    {
                        b.setTexto(linea);
                        sc.close();
                        return b;
                    }
                }
                sc.close();
            } catch(Exception e)
            {
                e.printStackTrace();
            }
            return b;
        }
        return null;
    }
    
    public void escribirBloque(int numBloque, Bloque b)
    {
        Scanner sc;
        ArrayList<String> lineas = new ArrayList<>();
        try
        {
            sc = new Scanner(this.archivoDisco);
            for(int i = 0; i < numBloques && sc.hasNextLine(); i++)
            {
                String linea = sc.nextLine();
                lineas.add(linea);
            }
            sc.close();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        
        FileWriter fw = null;
        PrintWriter pw = null;
        try
        {
            fw = new FileWriter(this.archivoDisco);
            pw = new PrintWriter(fw);
            
            for(int i = 0; i < this.numBloques; i++)
            {
                if(i == numBloques)
                {
                    pw.println(b.getTexto());
                }
                else
                {
                    pw.println(lineas.get(i));
                }
            }
        } catch(Exception e) 
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if(fw != null)
                {   
                    fw.close();
                } 
            } catch(Exception ex)
            {
                ex.printStackTrace();
            }
            
        }
            
    }
    
    public int getNumSectores()
    {
        return this.numBloques;
    }
}
