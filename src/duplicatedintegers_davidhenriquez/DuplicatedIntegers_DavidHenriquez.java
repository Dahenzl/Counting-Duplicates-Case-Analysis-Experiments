
package duplicatedintegers_davidhenriquez;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Algoritmos y Complejidad IST 4310
 * NRC: 3265
 * Name: David Daniel Henriquez Leal
 * Student code: 200157506
 * Date: 24/08/2022
 * 
 * Workshop 3: Best, Worst, and Average Case Analysis Experiments
 * In this workshop I created a proyect that is able to create and write a file with N random numbers given by the user, to them store all the individual integers in a second-rank array or matrix and count exactly how
 * many times that number appears in the plain text file. Also is able to take the exact time is needed to the main loop to end, the number of iterations and the size of the file, to then write all of them in a results file,
 * so we can send this data to phyton and graphicate the behaviour of the algorithm.
 * 
 */

public class DuplicatedIntegers_DavidHenriquez {
    static int N;
    static int random;
    static int caso;
    static int count;
    static int tam;
    static int [][] rand;
    static long inicio;
    static long fin;
    static long iteraciones;
    static PrintWriter out;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        out = new PrintWriter ("results.txt");
        
        caso = 0; // set the case to be evaluated, 0.Best Case 1.Worst Case 2.Average case       
        N = (int)Math.pow(2, 6); // set a value of N lines in the file
        rand = new int[(int)Math.pow(2, 22)][2]; // initialize matrix
        
        create("random.txt"); // creates the N integers file
        create("results.txt"); // creates the results files
        
        // writes and process 16 times a file duplicating the N lines each time
        for (int i = 0; i < 16; i++) {
            random = N; // set a range for the random
            iteraciones = 0; // reset the iterations counter
            tam = 0; // reset the counter of the size of the matrix
            count = 0; // reset the counter of duplicated numbers
            write(); // writes data to the file
            store(); // stores data in an matrix
            writeResults(); // writes the results (N, iterations and execution time) in the results.txt
            N = N * 2; // duplicate the lines of the file
        }
        out.close();
    }

// implementations:
	private static void create (String name)
	// creates a file
	{
		try
		{
			// defines the filename
			String fname = (name);
			// creates a new File object
			File f = new File (fname);

			// creates the new file
			f.createNewFile();

		}
		catch (IOException err)
		{
			// complains if there is an Input/Output Error
			err.printStackTrace();
		}
	}


	private static void write ()
	// writes data to a file
	{
		try
		{
			// defines the filename
			String filename = "random.txt";
			// creates new PrintWriter object for writing file
			PrintWriter out = new PrintWriter (filename);
                        // creates new random objecct to create the random numbers
			Random rand = new Random ();
                        
                        // Writes the file with the best case
                        if (caso == 0){
                            for (int i = 0; i != N; ++i)
				out.printf("%d\n", 1);
                        }
                        
                        // Writes the file with the worst case
                        if (caso == 1){
                            for (int i = 0; i != N; ++i)
				out.printf("%d\n", i);
                        }
			// Writes the file with the average case
                        if (caso == 2){
                            for (int i = 0; i != N; ++i)
				out.printf("%d\n", rand.nextInt(random) );
                        }
                        
			out.close();	// closes the file
		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}
	}


	private static void store ()
	// reads the file contents into an array and prints the array
	{
		String filename = "random.txt";
		File f = new File (filename);

		try
		{
			Scanner in = new Scanner (f);

                        inicio =  System.nanoTime(); // Takes the exact time in wich the main loop started
                        // reads random numbers into matrix an search if it is located in the matrix
			while ( in.hasNextInt() )
			{
                            int a = in.nextInt();
                            SearchAndModify(a);
			}
                        fin = System.nanoTime(); // Takes the exact time in wich the main loop started
                        in.close();

		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			System.out.println("IO Error:");
			err.printStackTrace();
		}
	}
        
        private static void SearchAndModify(int a)
        // search if a given integer is allocated in the matrix and them modify the matrix depending if the integer is there or not
        {
            boolean isThere = false; 
            // check if the value is in the matrix, actualize it and get the number of iterations
            for (int i = 0; i < tam; i++) {
                iteraciones ++;
                if(rand[i][0] == a){
                    rand[i][1] ++;
                    isThere = true; 
                    // check if it is the first time the number is repeated
                    if (rand[i][1] == 2)
                        count ++;
                    break;
                }
            }
            
            // add a element to the matrix if the number is new
            if (!isThere){
                rand[tam][0] = a;
                rand[tam][1] = 1;
                tam++;
            }
        } 
        
        private static void print()
        // print the duplicated numbers allocated in the matrix
        {
            System.out.printf("\nNumeros repetidos en el array:\n");
            System.out.println("\n" + "Numero - Veces Repetido");
            // search the numbers that were found more than one time
            for (int i = 0; i < tam; i++) {
                if(rand[i][1] > 1){
                    System.out.printf("%2d %4d\n", rand[i][0], rand[i][1]);
                }
            }
            System.out.println("\n" + count + " numeros estan duplicados en el archivo");
            System.out.println("\n" + "Tiempo empleado para el loop principal: " + (fin - inicio) + " nanosegundos");
        }
        
        private static void writeResults() throws FileNotFoundException
        // write the data in the results.txt file
        {
            out.printf("%s\n", N + " " + iteraciones + " " + (fin-inicio));
        }
}
