/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.Scanner;

/**
 *
 * @author Nicolas
 */
public class Proyecto
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
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
                    System.out.println("Formateando........");
                    break;
                    
                case "create":
                    System.out.println("Creando archivo " + comandos[1] + " de tamaño " + comandos[2] + " kilobytes.");
                    break;
                    
                case "remove":
                    System.out.println("Eliminando el archivo " + comandos[1] + ".");
                    break;
                    
                case "open":
                    System.out.println("Abriendo el archivo " + comandos[1] + ".");
                    break;
                    
                case "readat":
                    System.out.println("Leyendo el archivo " + comandos[1] + "desde la posición " + comandos[2] + ".");
                    break;
                    
                case "writeat":
                    System.out.println("Escribiendo en el archivo " + comandos[1] + "desde la posición " + comandos[2] + ".");
                    break;
                    
                case "printfile":
                    System.out.println("Imprimiendo archivo " + comandos[1] + ".");
                    break;
                    
                case "list":
                    System.out.println("Listando archivos.");
                    break;
            }
            
            System.out.println("\nIngrese otro comando o exit para salir.");
            scan = new Scanner(System.in);
            comando = scan.nextLine();
            comandos = comando.split(" ");
            
        }
    }
}
