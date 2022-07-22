import java.util.concurrent.Semaphore;

public class Intersectie extends Thread {
    private static final int semaphoreTime = 10;
    Semaphore semafor;
    String directie;
    String masiniNordSud[];
    String masiniEstVest[];

    public Intersectie(Semaphore semaphore, String directie, String masiniNordSud[], String masiniEstVest[]) {
        super(directie);
        this.semafor = semaphore;
        this.directie = directie;
        this.masiniNordSud = masiniNordSud;
        this.masiniEstVest = masiniEstVest;
    }

    @Override
    public void run() {
        int m = masiniNordSud.length;
        int n = masiniEstVest.length;
        if (this.getName().equals("NordSud")) {
            try {
                semafor.acquire();
                System.out.println("Se circula pe directia Nord-Sud->Este interzisa circulatia in directia Est-Vest");
                if (System.currentTimeMillis() - System.currentTimeMillis() <= semaphoreTime) {
                    if (m > -1) {
                        for (int i = 0; i < m; i++) {
                            System.out.println(masiniNordSud[i] + " a tranversat intersectia");
                        }
                        m--;
                    }

                }

            } catch (InterruptedException exception) {
                System.out.println(exception);
            }
            semafor.release();
        } else if (this.getName().equals("EstVest")) {

            try {
                semafor.acquire();
                System.out.println("Se circula pe directia Est-Vest. Este interzisa circulatia in directia Nord-Sud");
                if (System.currentTimeMillis() - System.currentTimeMillis() <= semaphoreTime) {
                    if (n >= -1) {
                        for (int i = 0; i < n; i++) {
                            System.out.println("Masina " + masiniEstVest[i] + " a tranversat intersectia");
                        }
                        n--;
                    }

                }

            } catch (InterruptedException exception) {
                System.out.println(exception);
            }

            semafor.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1);
        String masiniNordSud[] = new String[]{"MasinaNordSud1", "MasinaNordSud2", "MasinaNordSud3", "MasinaNordSud4", "MasinaNordSud5","MasinaNordSud6","MasinaNordSud7","MasinaNordSud8","MasinaNordSud9","MasinaNordSud10"};
        String masiniEstVest[] = new String[]{"MasinaEstVest1", "MasinaEstVest2", "MasinaEstVest3", "MasinaEstVest4","MasinaEstVest5","MasinaEstVest6","MasinaEstVest7","MasinaEstVest8","MasinaEstVest9","MasinaEstVest10"};

        Intersectie NordSud = new Intersectie(semaphore, "NordSud", masiniNordSud, masiniEstVest);
        Intersectie EstVest = new Intersectie(semaphore, "EstVest", masiniEstVest, masiniEstVest);

        NordSud.start();
        NordSud.join();
        EstVest.start();
        EstVest.join();

    }

}
