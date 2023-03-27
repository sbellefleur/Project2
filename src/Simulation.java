import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {
    private ArrayList<General> generals;
    private ArrayList<Planets> planets;

    private Config config;

    private Scanner in;

    private int currId = 0;

    public Simulation(Config c){
        config = c;

        in = new Scanner(System.in);

        // skip first line, it's a comment
        in.nextLine();

        // read the mode
        // skip the mode header
        in.next();
        String mode = in.next();

        // read the number of generals
        in.next();
        int numGenerals = in.nextInt();

        // read the number of planets
        in.next();
        int numPlanets = in.nextInt();

        generals = new ArrayList<>(numGenerals);
        for (int i =0; i < numGenerals; i++){
            //FIXME make sure we construct genrals correctly
            generals.add(new General());
        }
        planets = new ArrayList<>(numPlanets);
        for (int i = 0; i < numPlanets; i++){
            planets.add(new Planets(i));
        }

        // if PR mode, finalize our setup
        if(mode.equals("PR")){
            // random seed
            in.next();
            int seed = in.nextInt();

            // numDeployments
            in.next();
            int numDeployments = in.nextInt();

            // arrival rate
            in.next();
            int arrivalRate = in.nextInt();


            // create our random deployment generator
            in = P2Random.PRInit(seed, numGenerals, numPlanets, numDeployments, arrivalRate);
        }
        //in scanner
        //DL: System.in
        //PR: P2Random
    }

    public void performWarfare(){
        //FIXME where we actually read deployments and perform the battles
        long currentTime = 0;

        // print out the simulation starting message
        System.out.println("Deploying troops...");

        //TODO make sure this works
        while(in.hasNextLine()){
            Deployment d = getNextDeployment();

            //FIXME error checking the current time

            // check the time
            if (currentTime < d.timestamp){
                //FIXME do anything that needs doing when the timestamp changes

                // then...update time
                currentTime = d.timestamp;
            }
            Planets p = planets.get(d.planet);
            p.addDeployment(d);
            p.performBattles(config.verbose);
        }
        // no more deployments
        // print out summary
        System.out.print("---End of Day---\n");

        // count up to the battles
        int totalBattles = 0;
        for (Planets p : planets){
            totalBattles += p.getNumBattles()
        }
        System.out.print("Battles: " + totalBattles + "\n");
    }

    private Deployment getNextDeployment(){
        //<TIMESTAMP> <SITH/JEDI> G<GENERAL_ID> P<PLANET_NUM> F<FORCE_SENSITIVITY> #<NUM_TROOPS>
        // read all of this and construct a deployment object to represent the deployment
        long timestamp = in.nextLong();
        String type = in.next();

        // Integer.parseInt()
        int generalId = Integer.parseInt(in.next().substring(1));
        int planetId = Integer.parseInt(in.next().substring(1));
        int force = Integer.parseInt(in.next().substring(1));
        int numTroops = Integer.parseInt(in.next().substring(1));

        //FIXME error checking

        if(type.equals("SITH")){
            return new SithDeployment(currId++ , timestamp, generalId, planetId, force, numTroops);
        } else{
            return new JediDeployment(currId++ , timestamp, generalId, planetId, force, numTroops);
        }

    }
}
