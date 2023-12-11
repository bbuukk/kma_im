package buk;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

import java.io.ByteArrayOutputStream;

public class Q4 {
    public static void main(String[] args) {
        String queryString = "SELECT ?Pband " +
                "WHERE { " +
                "?Pband rdf:type dbpedia-owl:Band . " +
                "?Pband dbpedia-owl:hometown dbpedia:Kiev . " +
                "} " +
                "LIMIT 100";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        try {
            ResultSet results = qexec.execSelect();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outputStream, results);
            String json = new String(outputStream.toByteArray());
            System.out.println(json);
        } finally {
            qexec.close();
        }
    }
}
