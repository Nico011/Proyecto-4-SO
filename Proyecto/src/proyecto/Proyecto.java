/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Nicolas
 */
public class Proyecto
{
    private static Directorio directorio;
    private static Disco disco;
    private static BitMap bm;
    private static int sizeBloques;
    private static int numBloques;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        numBloques = 1024;
        sizeBloques = 512;
        
        iniciarDisco(numBloques, sizeBloques);
        
        String comando = "";
        System.out.println("Ingrese el comando y sus respectivos parámetros separados por espacios.\nIngrese h para mostrar los comandos disponibles o exit para salir. ");

        Scanner scan = new Scanner(System.in);
        comando = scan.nextLine();
        String[] comandos = comando.split(" ");
        
        while(!(comando.equals("exit")))
        {
            
            switch(comandos[0])
            {
                case "h":
                    System.out.println("format: Crea un volumen y su sistema de archivos asociado.");
                    System.out.println("create nombre_archivo tamaño_archivo: Crea un archivo, con su correspondiente entrada en el directorio, especificando su nombre y tamaño (en bytes).");
                    System.out.println("remove nombre_archivo: Borra un archivo, eliminando su entrada en el directorio y liberando el espacio en disco que ocupa.");
                    System.out.println("open nombre_archivo: Abre un archivo de lectura y escritura, a apartir de su nombre.");
                    System.out.println("readat nombre_archivo posición: Lee un archivo previamente abierto en una determinada posición.");
                    System.out.println("writeat nombre_archivo posición texto: Escribe un archivo previamente abierto en una determinada posición.");
                    System.out.println("printfile nombre_archivo: Muestra en pantalla el contenido de un archivo.");
                    System.out.println("list: Muestra en pantalla de las entradas del directorio, incluyendo el tamaño en bytes de cada archivo.");
                    break;
                    
                case "format":
                    format(numBloques, sizeBloques);
                    System.out.println("Formateo listo.");
                    break;
                    
                case "create":
                    if(Integer.parseInt(comandos[2]) < 0)
                    {
                        System.out.println("El tamaño es negativo, intente nuevamente.");
                        break;
                    }
                    create(Integer.parseInt(comandos[2]), comandos[1]);
                    System.out.println("Archivo creado exitosamente");
                    break;
                    
                case "remove":
                    remove(comandos[1]);
                    System.out.println("Listo.");
                    break;
                    
                case "open":
                    System.out.println("Abriendo el archivo " + comandos[1] + ".");
                    printFile(comandos[1]);
                    break;
                    
                case "readat":
                    System.out.println("Leyendo el archivo " + comandos[1] + "desde la posición " + comandos[2] + ".");
                    if(Integer.parseInt(comandos[2]) < 0)
                    {
                        System.out.println("Error en la posición, debe ser un valor positivo.");
                        break;
                    }
                    readAt(comandos[1], Integer.parseInt(comandos[2]));
                    break;
                    
                case "writeat":
                    if(Integer.parseInt(comandos[2]) < 0)
                    {
                        System.out.println("Error en la posición, debe ser un valor positivo.");
                        break;
                    }
                    writeAt(comandos[1], Integer.parseInt(comandos[2]), comandos[3]);
                    break;
                    
                case "printfile":
                    printFile(comandos[1]);
                    break;
                    
                case "list":
                    System.out.println("Lista de archivos:");
                    list();
                    break;
                    
                default:
                    System.out.println("Error, intente nuevamente.");
                    break;
            }
            
            System.out.println("\nIngrese otro comando o exit para salir.");
            scan = new Scanner(System.in);
            comando = scan.nextLine();
            comandos = comando.split(" ");
            
        }
    }

    public static void iniciarDisco(int numBloques, int sizeBloques) throws FileNotFoundException, IOException
    {
        disco = new Disco(numBloques, sizeBloques);
        
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<Integer> pos = new ArrayList<Integer>();
        ArrayList<Integer> espacioLibre = new ArrayList<Integer>();
        
        File f = new File("Disco");
        
        if(f.exists())
        {
            BufferedReader in = new BufferedReader(new FileReader(f));
            String sector = in.readLine();
            
            if(!compararVacio(sector))
            {
                char[] linea = sector.toCharArray();
                int init = 0;
                int fin = 0;
                boolean nombreEncontrado = false;
                
                for(char c : linea)
                {
                    if(c != ' ')
                    {
                        if(c != '-')
                        {
                            fin++;
                        }
                        else
                        {
                            String text = sector.substring(init, fin);
                            fin++;
                            init = fin;

                            if(!nombreEncontrado)
                            {
                                nombres.add(text);
                                nombreEncontrado = true;
                            }
                            else
                            {
                                pos.add(Integer.parseInt(text));
                                nombreEncontrado = false;
                            }
                        }
                    }
                    else 
                    {
                        break;
                    }
                }
                directorio = new Directorio(pos, nombres);
                sector = in.readLine();
                
                for(int i = 0; i < sector.length(); i++)
                {
                    espacioLibre.add(Integer.parseInt(sector.charAt(i) + ""));
                }
                bm = new BitMap(espacioLibre);
            }
            else
            {
                format(numBloques, sizeBloques);
            }
        }
        else
        {
            format(numBloques, sizeBloques);
        }
    }

    public static void format(int numBloques, int sizeBloques)
    {
        disco = new Disco(numBloques, sizeBloques);
        disco.iniciarDisco();
        directorio = new Directorio();
        bm = new BitMap(disco);
        
        Bloque blo = new Bloque(sizeBloques);
        
        char[] texto = new char[sizeBloques];
        texto[0] = '1';
        texto[1] = '1';
        
        for(int i = 2; i < sizeBloques; i++)
        {
            texto[i] = '0';
        }
        
        blo.setTexto(texto);
        disco.escribirBloque(1, blo);
    }
    
    public static boolean esUnico(String nombre)
    {
        for(String n : directorio.getNombres())
        {
            if(n.equals(nombre))
            {
                return false;
            }
        }
        return true;
    }
    
    public static void create(int size, String nombre)
    {
        if(nombre.length() <= 8 && esUnico(nombre))
        {
            int totalSectores = 0;
            
            if(size%512 != 0)
            {
                totalSectores = size/512 + 2;
            }
            else 
            {
                totalSectores = size/512 + 1;
            }
            
            if(bm.hayEspacio(totalSectores)) //si queda espacio...
            {
                //actualizo el bitmap en meoria
                int numSector = bm.getSectorDisponible();
                
                //actualizo el bitmap en disco
                reescribirBitMap();
                
                FCB sectorFCB = new FCB(sizeBloques, size);
                
                // agrego el resto de los sectores para guardar los datos del archivo
                for(int i = 0; i < totalSectores - 1; i++)
                {
                    int numSectorI = bm.getSectorDisponible();
                    reescribirBitMap();
                    
                    Bloque b = new Bloque(sizeBloques, numSectorI);
                    
                    if(i == totalSectores - 2)
                    {
                        b = new Bloque(sizeBloques, (size%sizeBloques), numSectorI);
                    }
                    
                    // añadir el puntero del nuevo bloque al fcb
                    sectorFCB.getBloques().add(b);
                    sectorFCB.getPos().add(numSectorI);
                    
                    // registrar el nuevo bloque en el disco
                    disco.escribirBloque(numSectorI, b);
                }
                
                // registrar el fcb en el disco
                String out = String.valueOf(sectorFCB.getSizeArchivo());
                for(Bloque b : sectorFCB.getBloques())
                {
                    // se imprime el tamaño del archivo y el bloque en el que está
                    out += "-" + String.valueOf(b.getId());
                }
                
                Bloque fc = new Bloque(sizeBloques, out.toCharArray());
                disco.escribirBloque(numSector, fc);
                
                // agrego al directorio
                directorio.getNombres().add(nombre);
                directorio.getPos().add(numSector);
                
                out = "";
                boolean nombreRegistrado = false;
                
                int total = directorio.getNombres().size();
                
                for(int i = 0; i < total; i++)
                {
                    if(!nombreRegistrado)
                    {
                        //obtenemos el nombre del directorio y se guarda en el disco
                        out += directorio.getNombres().get(i) + "-";
                        nombreRegistrado = true;
                    }
                    if(nombreRegistrado)
                    {
                        // obtenemos el bloque en el que parte el archivo
                        out += directorio.getPos().get(i) + "-";
                        nombreRegistrado = false;
                    }
                }
                
                Bloque dir = new Bloque(sizeBloques, out.toCharArray());
                
                disco.escribirBloque(0, dir);
                System.out.println("Archivo creado exitosamente.");
            }
            else
            {
                System.out.println("No queda espacio disponible.");
            }
        }
        else
        {
            if(nombre.length() > 9)
                System.out.println("El nombre supera los 8 caracteres permitidos.");
            else
                System.out.println("El nombre del archivo ya exite.");
        }
    }
    
    public static void remove(String nombre)
    {
        int i = 0;
        boolean flag = false;
        
        for(String s : directorio.getNombres())
        {
            if(s.equals(nombre)) //se encuentra el archivo
            {
                // se borra
                directorio.getNombres().remove(i);
                int posFCB = directorio.getPos().get(i);
                reescribirDirectorio();
                
                //obtenemos el fcb
                Bloque sectorFCB = disco.leerBloque(posFCB);
                FCB fcb = convertirBloqueAFCB(sectorFCB);
                
                for(Integer id : fcb.getPos())
                {
                    Bloque newBloque = new Bloque(sizeBloques);
                    disco.escribirBloque(id, newBloque);
                    bm.addSectorDisponible(id);
                    reescribirBitMap();
                }
                
                Bloque bloqueFCB = new Bloque(sizeBloques);
                disco.escribirBloque(posFCB, bloqueFCB);
                
                flag = true;
                break;
            }
            i++;
        }
        if(!flag)
        {
            System.out.println("El archivo no existe.");
        }
    }

    private static void reescribirDirectorio()
    {
        String out = "";
        boolean nombreRegistrado = false;
        
        int total = directorio.getNombres().size();
        
        for(int i = 0; i < total; i++)
        {
            if(!nombreRegistrado)
            {
                out += directorio.getNombres().get(i) + "-";
                nombreRegistrado = false;
            }
        }
        Bloque dir = new Bloque(sizeBloques, out.toCharArray());
        disco.escribirBloque(0, dir);
    }
    
    public static void readAt(String nombre, int init)
    {
        int i = 0;
        boolean flag = false;
        
        for(String n : directorio.getNombres())
        {
            if(n.equals(nombre))
            {
                int posFCB = directorio.getPos().get(i);
                Bloque sectorFCB = disco.leerBloque(posFCB);
            
                FCB fcb = convertirBloqueAFCB(sectorFCB);
            
                if(fcb.getSizeArchivo() < init)
                {
                    System.out.println("Posición incorrecta.");
                    break;
                }

                System.out.println("Contenido del archivo desde byte: " + init);
                int count = 1;
                int min = 0;
                int max = count*sizeBloques;

                for(Integer id : fcb.getPos())
                {
                    Bloque b = disco.leerBloque(id);
                    String text = String.valueOf(b.getTexto());

                    //segmentado
                    if(init < max && init >= min)
                    {
                        int start = init - min;
                        imprimirBloque(start, text);
                    }

                    // completo
                    else
                    {
                        if(init < min)
                        {
                            System.out.println(text);
                        }
                    }
                    min = max;
                    count++;
                    max = count*sizeBloques;
                }
                flag = true;
                break;
            }
            i++;
        }
        if(!flag)
        {
            System.out.println("El archivo no existe.");
        }
    }
    
    public static void writeAt(String nombre, int pos, String escribir)
    {
        int i = 0;
        boolean flag = false;
        FCB fcb = new FCB(sizeBloques);
        
        for(String n : directorio.getNombres())
        {
            if(n.equals(nombre))
            {
                // se encuentra el archivo
                int posFCB = directorio.getPos().get(i);
                Bloque sectorFCB = disco.leerBloque(posFCB);
                
                fcb = convertirBloqueAFCB(sectorFCB);
                
                int byteFinal = pos + escribir.length();
                
                if(fcb.getSizeArchivo() < byteFinal)
                {
                    System.out.println("No hay espacio suficiente.");
                    break;
                }
                
                for(Integer id : fcb.getPos())
                {
                    Bloque bl = disco.leerBloque(id);
                    bl.setId(id);
                    fcb.getBloques().add(bl);
                }
                flag = true;
                break;
            }
            i++;
        }
        
        int initEscribir = pos;
        int countBloques = 1;
        
        for(Bloque b : fcb.getBloques())
        {
            if(initEscribir < countBloques * sizeBloques)
            {
                int disEscribir = (countBloques * sizeBloques) - initEscribir;
                
                if(disEscribir > escribir.length())
                {
                    String corto = escribir;
                    String antiguo = String.valueOf(b.getTexto()).substring(escribir.length());
                    String nuevo = corto + antiguo;
                    
                    escribir = "";
                    
                    b.setTexto(nuevo.toCharArray());
                    disco.escribirBloque(b.getId(), b);
                    initEscribir = countBloques * sizeBloques;
                }
                else
                {
                    String corto = escribir.substring(0, disEscribir);
                    String antiguo = String.valueOf(b.getTexto()).substring(0, initEscribir);
                    String nuevo = antiguo + corto;
                    
                    escribir = escribir.substring(disEscribir);
                    
                    b.setTexto(nuevo.toCharArray());
                    disco.escribirBloque(b.getId(), b);
                    initEscribir = countBloques * sizeBloques;
                }
                countBloques++;
            }
            if(escribir.length() == 0)
            {
                break;
            }
        }
        if(!flag)
        {
            System.out.println("El archivo no existe.");
        }
        
    }
    
    public static void printFile(String nombre)
    {
        int i = 0;
        boolean flag = false;
        
        for(String n : directorio.getNombres())
        {
            if(n.equals(nombre))
            {
                int posFCB = directorio.getPos().get(i);
                Bloque sectorFCB = disco.leerBloque(posFCB);
                
                FCB fcb = convertirBloqueAFCB(sectorFCB);
                
                System.out.println("Contenido del archivo: " + n);
                
                for(Integer id : fcb.getPos())
                {
                    Bloque leido = disco.leerBloque(id);
                    System.out.println(String.valueOf(leido.getTexto()));
                }
                flag = true;
                break;
            }
            i++;
        }
        if(!flag)
        {
            System.out.println("El archivo no existe.");
        }
    }
    
    public static void list()
    {
        int i = 0;
        
        if(!directorio.getNombres().isEmpty())
        {
            System.out.println("Nombre\tTamaño\t\tBloques");
            for(String n : directorio.getNombres())
            {
                int posFCB = directorio.getPos().get(i);
                Bloque b = disco.leerBloque(posFCB);
                FCB fcb = convertirBloqueAFCB(b);
                System.out.println(n + "\t" + fcb.getSizeArchivo() + " (bytes)" + "\t" + fcb.getPos().size());
                i++;
            }
        }
        else
        {
            System.out.println("El directorio está vacío.");
        }
    }

    // retorna true si la linea "sector" está vacía
    public static boolean compararVacio(String sector)
    {
        Bloque vacio = new Bloque(sizeBloques);
        String s = String.valueOf(vacio.getTexto());
        if(s.equals(sector))
        {
            return true;
        }
        return false;
    }

    private static void reescribirBitMap()
    {
        String nuevo = "";
        for(int i = 0; i < sizeBloques; i++)
        {
            nuevo += String.valueOf(bm.getEspacios().get(i));
        }
        
        Bloque b = new Bloque(sizeBloques);
        b.setTexto(nuevo.toCharArray());
        disco.escribirBloque(1, b);
    }

    private static FCB convertirBloqueAFCB(Bloque sectorFCB)
    {
        FCB out = new FCB(sizeBloques);
        out.setTexto(sectorFCB.getTexto());
        
        char[] linea = sectorFCB.getTexto();
        boolean sizeEncontrado = false;
        int init = 0;
        int fin = 0;
        String temp = "";
        String size = "";
        int pos = 1;
        
        ArrayList<Integer> ref = new ArrayList<>();
        
        for(char c : linea)
        {
            if(c != ' ')
            {
                if(c != '-')
                {
                    fin++;
                }
                else 
                {
                    if(!sizeEncontrado)
                    {
                        size = String.valueOf(linea).substring(init, fin);
                        out.setSizeArchivo(Integer.valueOf(size));
                        fin++;
                        init = fin;
                        sizeEncontrado = true;
                    }
                    else
                    {
                        temp = String.valueOf(linea).substring(init, fin);
                        ref.add(Integer.valueOf(temp));
                        fin++;
                        init = fin;
                    }
                }
            }
            else
            {
                if(pos == fin + 1)
                {
                    temp = String.valueOf(linea).substring(init, fin);
                    ref.add(Integer.valueOf(temp));
                }
                break;
            }
            pos++;
        }
        for(Integer r : ref)
        {
            out.getPos().add(r);
        }
        return out;
    }

    private static void imprimirBloque(int start, String text)
    {
        char[] t = text.toCharArray();
        
        for(int i = 0; i < t.length; i++)
        {
            if(i >= start)
            {
                System.out.println(t[i]);
            }
        }
        System.out.println("\n");
    }
}















