package ejercicios_entregables;

import mpi.*;

public class ejercicio_1 {
    /*Crea un programa en Java utilizando MPJ Express que envíe un mensaje desde un proceso
maestro (rango 0) a un proceso esclavo (rango 1). El mensaje puede ser un simple saludo.*/
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
        if(rank == 0){
            MPI.COMM_WORLD.Send(saludo.toCharArray(),0,saludo.length(), MPI.CHAR,1,0);
            System.out.println("Ahi se envio el mensaje");
        }else if(rank== 1){
            char[] mensaje = new char[saludo.length()];
            MPI.COMM_WORLD.Recv(mensaje, 0, 50, MPI.CHAR, 0, 0);
            System.out.println("El mensaje se recibio, y es: " + String.valueOf(mensaje));
        }
        MPI.Finalize();

    }

}