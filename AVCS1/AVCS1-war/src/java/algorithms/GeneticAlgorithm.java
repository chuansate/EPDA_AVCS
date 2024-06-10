/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.ArrayList;

/**
 *
 * @author lowsi
 */


//public class GeneticAlgorithm {
//    private int populationSize;
//    private float mutationProb;
//    private int eliteNum; // number of best solutions/chromosomes
//    private int maxIter;
//    private float mutationProb2;
//    private Schedule[] population;
//    public GeneticAlgorithm(int populationSize, float mutationProb, int eliteNum, int maxIter, float mutationProb2) {
//        this.populationSize = populationSize;
//        this.mutationProb = mutationProb;
//        this.mutationProb2 = mutationProb2;
//        this.eliteNum = eliteNum;
//        this.maxIter = maxIter;
//        this.population = new Schedule[this.populationSize];
//    }
//    
//    public void init_population(ArrayList<Schedule> schedules) {
//        // init the population entity/(list of chromosomes/solutions) here
////        for i in range(self.population_size):
////            entity = []
////
////            for schedule in schedules:
////                # random init roomId, weekDay, and slot of schedules
////                schedule.random_init(roomRange)
////                entity.append(copy.deepcopy(schedule))
////            self.population.append(entity)
//        for (int i = 0; i < this.populationSize; i++) {
//            ArrayList<Schedule> entity = new ArrayList<Schedule>();;
//            for (Schedule sc: schedules) {
//                sc.random_init();
//                entity.add(new Schedule(sc));
//            }
//        }
//    }
//    
//    
//    public void runEvolution(ArrayList<Schedule> schedules) {
//        int bestScore = 0;
//        ArrayList<Schedule> bestSolution_Entity[] = null;
//        this.init_population(schedules);
//        
//        for (int i=1; i<this.maxIter+1; i++) {
//            if (i % 50 == 0) {
//                System.out.println("Generation " + i + "...");
//            }
//            
////            eliteIndexes = ;
////            bestScore = ;
//                    
//        }
//    }
//}
