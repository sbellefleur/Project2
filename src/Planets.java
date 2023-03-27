import java.util.PriorityQueue;

public class Planets {

    private PriorityQueue<Deployment> jediDeployments;
    private PriorityQueue<Deployment> sithDeployments;

    private int id;
    private int numBattles;

    public Planets(int id){
        this.id = id;
        numBattles = 0;

        jediDeployments = new PriorityQueue<>();
        sithDeployments = new PriorityQueue<>();

    }
    public void addDeployment(Deployment d){
        if(d.isSith()){
            sithDeployments.add(d);
        }else{
            jediDeployments.add(d);
        }
    }
    public boolean canBattle() {
        //FIXME check to see if there is a valid battle
        // we need both a jedi AND A SITH
        if (jediDeployments.isEmpty() || sithDeployments.isEmpty()){
            return false;
        }
        return jediDeployments.peek().force <= sithDeployments.peek().force;
    }

    public void performBattles(boolean printOutput){
        //FIXME check if battles can be performed and do as many
        //battles as possible
        while (canBattle()){
            // a battle can occur
            // subtract the same number of troops from both sith and jedi deployments
            int troopsLost = Math.min(jediDeployments.peek().quantity, sithDeployments.peek().quantity);

            //subtract these from both deployments
            jediDeployments.peek().quantity -= troopsLost;
            sithDeployments.peek().quantity -= troopsLost;

            // print output before removing from the PQs
            if (printOutput){
                System.out.print("General " + sithDeployments.peek().general + "'s battalion" +
                        "attacked General " + jediDeployments.peek().general + "'s battalion on " +
                        "planet " + id + ". " + (troopsLost *2) + " troops were lost.\n");
            }


            //one of these deployments will have lost all the troops
            // remove that deployment from the PQ
            if (jediDeployments.peek().quantity == 0){
                jediDeployments.remove();
            }
            if (sithDeployments.peek().quantity == 0){
                sithDeployments.remove();
            }

            // update the count of our battles
            numBattles++;
        }
    }
    public int getNumBattles() {
        return numBattles;
    }
}
