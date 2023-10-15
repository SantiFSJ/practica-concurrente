package ejercicios_entregables;

import mpi.*;

public class ejercicio_3 {
    /*Crea un programa MPI en el que múltiples procesos se comuniquen en un patrón de anillo. Cada
proceso debe recibir un mensaje de su proceso vecino y luego pasar el mensaje al siguiente
proceso en el anillo hasta que vuelva al proceso original.*/
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int siguiente = (rank + 1) % size;
        int prev = (rank - 1 + size) % size;

        String mensaje = "Hola proceso vecino!!";
        char[] mensajeEnviar = (mensaje + siguiente).toCharArray();
        char[] mensajeRecibido = new char[mensaje.length()];

        if(rank == 0){
            MPI.COMM_WORLD.Send(mensajeEnviar,0,mensaje.length(), MPI.CHAR,siguiente,0);
            System.out.println("El proceso nro:"+rank+" envio el mensaje!!");
            MPI.COMM_WORLD.Recv(mensajeRecibido, 0, 50, MPI.CHAR, prev, 0);
            System.out.println("El proceso nro:"+rank+" recibio el mensaje!!");

        }else{
            MPI.COMM_WORLD.Recv(mensajeRecibido, 0, 50, MPI.CHAR, prev, 0);
            System.out.println("El proceso nro:"+rank+" recibio el mensaje!!");
            MPI.COMM_WORLD.Send(mensajeEnviar,0,mensaje.length(), MPI.CHAR,siguiente,0);
            System.out.println("El proceso nro:"+rank+" envio el mensaje!!");

        }

        MPI.Finalize();


    }


}
