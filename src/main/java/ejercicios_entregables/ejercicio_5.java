package ejercicios_entregables;

import mpi.MPI;
import mpi.MPIException;

public class ejercicio_5 {
    public static void main(String[] args) throws MPIException {
        //Se requieren 6 procesos, 1 padre y 5 hijos, arreglo de 10 elementos y se divide en 5
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] arregloA = {2, 2, 3, 4, 5, 1, 2, 3, 4, 5};
        int[] arregloB = {2, 2, 3, 4, 5, 1, 2, 3, 4, 5};

        int elementosPorProceso = 2;

        if (rank == 0) {
            int productoEscalar = 0;
            int[] arregloParcial = new int[elementosPorProceso];
            for(int i=1; i < size; i++){
                System.arraycopy(arregloA, (i - 1) * elementosPorProceso, arregloParcial, 0, elementosPorProceso);
                MPI.COMM_WORLD.Send(arregloParcial, 0, arregloParcial.length, MPI.INT, i, 0);
                System.arraycopy(arregloB, (i - 1) * elementosPorProceso, arregloParcial, 0, elementosPorProceso);
                MPI.COMM_WORLD.Send(arregloParcial, 0, arregloParcial.length, MPI.INT, i, 0);
            }
            for(int i=1; i < size; i++){
                int[] arregloProductosEscalares = new int[size];
                MPI.COMM_WORLD.Recv(arregloProductosEscalares, 0, 50, MPI.INT, i, 0);
                productoEscalar += arregloProductosEscalares[0];
            }
            System.out.println("El producto escalar de estos arreglos es:"+productoEscalar);
        } else {
            int[] arregloParcialA = new int[elementosPorProceso];
            MPI.COMM_WORLD.Recv(arregloParcialA, 0, 50, MPI.INT, 0, 0);
            int[] arregloParcialB = new int[elementosPorProceso];
            MPI.COMM_WORLD.Recv(arregloParcialB, 0, 50, MPI.INT, 0, 0);
            int productoEscalarParcial = 0;
            for(int i = 0; i < elementosPorProceso; i++){
                productoEscalarParcial += (arregloParcialA[i] * arregloParcialB[i]);
            }
            int[] arregloProductoEscalar = {productoEscalarParcial};
            MPI.COMM_WORLD.Send(arregloProductoEscalar, 0, arregloProductoEscalar.length, MPI.INT, 0, 0);

        }
        MPI.Finalize();

    }
}
