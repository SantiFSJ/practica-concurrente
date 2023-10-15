package ejercicios_entregables;

import mpi.MPI;
import mpi.MPIException;

public class ejercicio_2 {
    /*Modifica el ejercicio anterior para que el proceso esclavo también envíe un mensaje de
respuesta al proceso maestro.*/
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if(size < 2){
            System.err.println("Ingresa una cantidad valida");
            MPI.Finalize();
            return;
        }
        String saludo  = "Hola, buen día proceso!";
        String respuesta = "El mensaje se recibio: ";
        if(rank == 0){
            MPI.COMM_WORLD.Send(saludo.toCharArray(),0,saludo.length(), MPI.CHAR,1,0);
            System.out.println("Ahi se envio el mensaje");
            char[] mensajeRespuesta = new char[respuesta.length() + saludo.length()];

            MPI.COMM_WORLD.Recv(mensajeRespuesta, 0, 50, MPI.CHAR, 1, 1);
            System.out.println("El mensaje fue recibido por el hijo, y su respuesta fue: " + String.valueOf(mensajeRespuesta));


        }else if(rank== 1){
            char[] mensaje = new char[saludo.length()];
            MPI.COMM_WORLD.Recv(mensaje, 0, 50, MPI.CHAR, 0, 0);

            System.out.println(respuesta + String.valueOf(mensaje));

            MPI.COMM_WORLD.Send(respuesta.toCharArray(),0,saludo.length(), MPI.CHAR,0,1);

        }
        MPI.Finalize();

    }

}
