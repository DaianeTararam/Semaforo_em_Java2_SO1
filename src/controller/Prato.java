package controller;

import java.util.concurrent.Semaphore;

public class Prato extends Thread{
	private int id;
	private Semaphore semaforo;
	
	public Prato(int id, Semaphore semaforo) {
		this.id = id;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		verificarTempo();
		
	}

	private void verificarTempo() {
		double cozimentoMin, cozimentoMax;
		if(id % 2 != 0) {
			cozimentoMin = 0.5;
			cozimentoMax = 0.8;
		} else {
			cozimentoMin = 0.6;
			cozimentoMax = 1.2;
		}
		double tempoCozimento = cozimentoMin + (cozimentoMax - cozimentoMin) * Math.random();

        System.out.printf("Prato %d: Início do cozimento. Tempo total: %.2f segundos.%n", id, tempoCozimento);

        long tempoTotal = (long) (tempoCozimento * 1000);
        long tempoAtual = 0;
        
        while (tempoAtual < tempoTotal) {
            try {
                Thread.sleep(100);
                tempoAtual += 100;
                if (tempoAtual > tempoTotal) {
                    tempoAtual = tempoTotal;
                }
                int percentual = (int) ((tempoAtual / (double) tempoTotal) * 100);
                System.out.printf("Prato %d: Cozimento em %d%%.%n", id, percentual);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Prato %d: Cozimento concluído.%n", id);
        entregarPrato();
	}

	private void entregarPrato() {
		try {
			semaforo.acquire();
			System.out.printf("Prato %d: Iniciando entrega.%n", id);
			sleep(500);
			System.out.printf("Prato %d: ENTREGA CONCLUÍDA!%n", id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
	}

}
