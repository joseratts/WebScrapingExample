package com.josedeveloper.WebScrapingExample;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	final String url = "http://resultados.as.com/resultados/futbol/primera/2014_2015/calendario";
    	
    	Document doc = Jsoup.connect(url).get();
    	
    	//Obtenemos todas las filas identificadas como evento deportivo
    	//ya que con este atributo es como se identifican los partidos
    	Elements matches = doc.select("tr[itemtype$=\"http://schema.org/SportsEvent\"]");
    	
    	for (Element match: matches) {
    		
    		//Obtenemos los equipos de cada partido utilizando también expresiones
    		Elements teams = match.select("td[itemtype$=\"http://schema.org/SportsTeam\"]");
    		
    		//obtenemos el enlace al detalle del partido
    		Elements score = match.select("a[class=\"resultado resul_post\"]");
    		
    		String localTeam = teams.get(0).text();
    		String visitorTeam = teams.get(1).text();
    		String statsLink = score.first().attr("href");
    		
    		String[] goals = score.first().text().split("-");
    		int localGoals = Integer.parseInt(goals[0].trim());
    		int visitorGoals = Integer.parseInt(goals[1].trim());
    		
    		System.out.println(localTeam + " vs " + visitorTeam + ": " + localGoals + "-" + visitorGoals + " -> " + statsLink);
    	}
    }
}
