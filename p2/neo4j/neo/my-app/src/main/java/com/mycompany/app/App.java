package com.mycompany.app;

import org.neo4j.driver.*;

public class App {

        public static void main(String[] args) {
                String uri = "bolt://localhost:7687";
                String username = "neo4j";
                String password = "neo4j";

                try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
                                Session session = driver.session()) {
                        System.out.println("Connected...");

                        session.run("CREATE " +
                                        "(alice:User {name: 'Alice'}), " +
                                        "(bob:User {name: 'Bob'}), " +
                                        "(carol:User {name: 'Carol'}), " +
                                        "(dave:User {name: 'Dave'})," +
                                        "(sam:User {name: 'Sam'})");

                        session.run(
                                        "MATCH (alice:User {name: 'Alice'}), (bob:User {name: 'Bob'}) CREATE (alice)-[:FRIENDS_WITH]->(bob)");
                        session.run(
                                        "MATCH (bob:User {name: 'Bob'}), (carol:User {name: 'carol'}) CREATE (bob)-[:FRIENDS_WITH]->(carol)");

                        session.run(
                                        "MATCH (bob:User {name: 'Bob'}), (carol:User {name: 'Carol'}) CREATE (bob)-[:FRIENDS_WITH]->(carol)");
                        session.run(
                                        "MATCH (alice:User {name: 'Alice'}), (dave:User {name: 'Dave'}) CREATE (alice)-[:FRIENDS_WITH]->(dave)");

                        session.run(
                                        "MATCH (dave:User {name: 'Dave'}), (carol:User {name: 'Carol'}) CREATE (dave)-[:FRIENDS_WITH]->(carol)");
                        session.run(
                                        "MATCH (dave:User {name: 'Dave'}), (sam:User {name: 'Sam'}) CREATE (dave)-[:FRIENDS_WITH]->(sam)");

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }
}
