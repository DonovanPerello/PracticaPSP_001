package paucasesnovescifp.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class AnalitzadorPrincipal {

    public  static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        System.out.println("Que archivo quieres contar los errores: ");
        String textoRecibido = "Fallo de conexion ERROR\n" +
                "Otro ERROR mas\n" +
                "Y el ultimo ERROR del sistema\n" +
                "Cuidado, un WARNING\n" +
                "Otro WARNING de memoria\n" +
                "Y el tercer WARNING\n";
            try {


                ProcessBuilder pbA = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "cat.paucasesnovescifp.utilitats.FiltreLog");
                pbA.redirectError(new File("errors_filtre.log"));

                ProcessBuilder pbB = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "cat.paucasesnovescifp.utilitats.FiltreLog", "WARNING");
                pbB.redirectError(new File("errors_filtre.log"));
                Process procesoHijoA = pbA.start();
                 Process procesoHijoB = pbB.start();

                BufferedWriter escrituraA = new BufferedWriter(new OutputStreamWriter(procesoHijoA.getOutputStream()));
                BufferedWriter escrituraB = new BufferedWriter(new OutputStreamWriter(procesoHijoB.getOutputStream()));

                escrituraA.write(textoRecibido);

                escrituraA.flush();

                escrituraA.close();

                escrituraB.write(textoRecibido);


                escrituraB.flush();

                escrituraB.close();

                Scanner recibidoA = new Scanner(procesoHijoA.getInputStream());
                Scanner recibidoB = new Scanner(procesoHijoB.getInputStream());
                String respuestaA = "";
                String respuestaB = "";
                if (recibidoA.hasNextLine()) {
                     respuestaA = recibidoA.nextLine();

                }
                procesoHijoA.waitFor();


                if (recibidoB.hasNextLine()) {
                     respuestaB = recibidoB.nextLine();

                }
                procesoHijoB.waitFor();

                int valor = 0;

                if(procesoHijoA.exitValue()== 1 || procesoHijoB.exitValue()==1)valor = 1;

                System.out.println("RESULTAT: ERRORS="+ respuestaA +" | WARNINGS="+ respuestaB +" | EXIT_CODE=" + valor);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
