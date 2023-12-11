// Package declaration
package buk;

// Importing necessary classes
import org.neo4j.driver.*;

// Main class
public class App {
        // Main method
        public static void main(String[] args) {
                // Database connection parameters
                String uri = "bolt://localhost:7687";
                String username = "neo4j";
                String password = "friendsfree";

                // Establishing a connection to the database and starting a session
                try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
                                Session session = driver.session()) {
                        // Print a message to the console once connected
                        System.out.println("Connected...");

                        // Creating nodes for users Alice, Bob, Carol, Dave, and Sam
                        session.run("CREATE " +
                                        "(alice:User {name: 'Alice'}), " +
                                        "(bob:User {name: 'Bob'}), " +
                                        "(carol:User {name: 'Carol'}), " +
                                        "(dave:User {name: 'Dave'})," +
                                        "(sam:User {name: 'Sam'})");

                        // Creating FRIENDS_WITH relationships between different pairs of users
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

                        // closing connection
                        driver.close();

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
