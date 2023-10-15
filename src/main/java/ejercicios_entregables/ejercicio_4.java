package ejercicios_entregables;

import mpi.MPI;
import mpi.MPIException;

public class ejercicio_4 {
    /*Divide un arreglo grande en varios fragmentos y distribuye estos fragmentos entre los procesos
MPI. Luego, cada proceso suma su fragmento y envía el resultado al proceso maestro. El proceso
maestro debe calcular la suma total.*/
    public static void main(String[] args) throws MPIException {
        //Se requieren 6 procesos, 1 padre y 5 hijos, arreglo de 15 elementos y se divide en 5
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] arregloEntero = {2, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5};

        int elementosPorProceso = 3;

        if (rank == 0) {
            int sumaTotal = 0;
            int[] arregloParcial = new int[elementosPorProceso];
            for(int i=1; i < size; i++){
                System.arraycopy(arregloEntero, (i - 1) * elementosPorProceso, arregloParcial, 0, elementosPorProceso);
                MPI.COMM_WORLD.Send(arregloParcial, 0, arregloParcial.length, MPI.INT, i, 0);
            }
            for(int i=1; i < size; i++){
                int[] arregloSumasParciales = new int[size];
                MPI.COMM_WORLD.Recv(arregloSumasParciales, 0, 50, MPI.INT, i, 0);
                sumaTotal += arregloSumasParciales[0];
            }
            System.out.println("La suma total del arreglo es:"+sumaTotal);
        } else {
            int[] arregloParcial = new int[elementosPorProceso];
            MPI.COMM_WORLD.Recv(arregloParcial, 0, 50, MPI.INT, 0, 0);
            int sumaParcial = 0;
            for(int i=0; i < elementosPorProceso; i++){
                sumaParcial += arregloParcial[i];
            }
            int[] arregloSumaParcial = {sumaParcial};
            MPI.COMM_WORLD.Send(arregloSumaParcial, 0, arregloSumaParcial.length, MPI.INT, 0, 0);

        }
        MPI.Finalize();

    }

}
