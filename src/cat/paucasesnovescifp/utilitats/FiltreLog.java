package cat.paucasesnovescifp.utilitats;

import java.util.Scanner;

public class FiltreLog {
   public static void main(String[] args) {
    String palabraBuscada;
        if(args.length == 0){
            palabraBuscada = "ERROR";
        } else {
            palabraBuscada = args[0];
        }
        Scanner lector = new Scanner(System.in);
        int contador = 0;

        if (!lector.hasNextLine()){
            System.err.println("Error: text buit");
            System.exit(1);
        }
        while (lector.hasNextLine()) {

            String  operacion = lector.nextLine();

            String[] palabras = operacion.split("\\s+");

            for (String p : palabras){


                if(p.contains(palabraBuscada)){
                    contador ++;
                }
            }
        }

        System.out.println(contador);
        System.exit(0);
    }
}
