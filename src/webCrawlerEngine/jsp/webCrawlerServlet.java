package webCrawlerEngine.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.*;
import java.nio.charset.StandardCharsets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Servlet implementation class webCrawlerServlet
 */
@WebServlet("/webCrawlerServlet")
public class webCrawlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public webCrawlerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// fange an die Parametern vom JSP Anfrage zu empfangen
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String url = request.getParameter("url");
		String keyword = request.getParameter("keyword");
		String size_str = request.getParameter("size");
		if(size_str == null) {
			return;
		}
		int maxSize = Integer.parseInt(size_str);
		System.out.println("keyword is " + keyword);
		startCrawling(url, keyword, maxSize);
		// die extrahierte Ergebnisse ausprinten
		out.println("<html><body>");
		out.println("<h2>The harvested websites :</h2>");
		out.println("<hr>");
		int i=1;
		for(String s : matches) {
			if(i > maxSize)break;
			out.println(i + ". " + s + "\n");
			out.println("<hr>");
			i++;
		}
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	 public static Queue<String> q = new LinkedList<>();		// Warteschlange fuer die noch nicht besuchte URLs
	 public static Set<String> visited = new HashSet<>();		// Set enthaelt die schon besuchte und bestaetigte URLs ohne Duplikaten 
	 public static Set<String> matches = new HashSet<>();		// Set enthaelt die schon besuchte und bestaetigte URLs ohne Duplikaten 
	 public static String regex = "http[s]*://(\\w+\\.)*(\\w+)";	// reguelaerer Ausruck definiert die angenommene URL-Aussicht
	 
	 // typical BFS search that searches for all URLs starting from one node
	 public static void startCrawling(String root, String keyword, int maxSize) throws IOException{
		 q.add(root);								// der erste Knoten ist das vom User angegebene URL
		 visited.add(root);
		 while(!q.isEmpty()) {
			 String crawledUrl = q.poll();
			 if(visited.size() > maxSize) {
				 return;
			 }
			 URL url = null;
			 BufferedReader br = null;
			 while(true) {
				 try {
					 url = new URL(crawledUrl);
					 br = new BufferedReader(new InputStreamReader(url.openStream()));		// den Inhalt vom URL auspueffern
					 break;
				// falsches URL ? dann schalte einen Exception frei und nehme den naechsten url von der Warteschlange
				 } catch (MalformedURLException e) {
					 System.out.println("wrong URL : " + crawledUrl);
					 crawledUrl = q.poll();
				// klappt es nicht beim URL-Lesen ? dann schalte einen Exception frei und nehme den naechsten url von der Warteschlange
				 } catch (IOException ioe) {
					 System.out.println("error getting URL : " + crawledUrl);
					 crawledUrl = q.poll();
				 }
			 }
			 
			 StringBuilder sb = new StringBuilder();				// API Kompatibel mit StringBuffer
			 while ((crawledUrl = br.readLine()) != null) {
					 sb.append(crawledUrl);
			 }
			 crawledUrl = sb.toString();
			 Pattern pattern = Pattern.compile(regex);
			 Matcher matcher = pattern.matcher(crawledUrl);
			 searchMatchesFromUrl(keyword, crawledUrl, matcher);
		 }
	 }

	private static void searchMatchesFromUrl(String keyword, String crawledUrl, Matcher matcher) {
		while (matcher.find()) {
			String str = matcher.group();
			System.out.println("should be url " + str);

			if (!visited.contains(str)) {
				visited.add(str);
				try{
					String s = readStringFromURL(str);
					if(s.contains(keyword)) {
						matches.add(str);
						System.out.println("found a match ! added for crawling : " + str);
						q.add(str);
					}
				}catch (IOException ioe) {
					System.out.println("error getting URL : " + crawledUrl);
					crawledUrl = q.poll();
				}
			}
		}
	}

	// nur zum testen
	 public static void showResults() {
		 System.out.println("**** results *** ");
		 System.out.println("Websites crawled : " + visited.size() + "\n");
		 for (String s : matches) {
			 System.out.println("* " + s);
		 }
	 }
	 
	  public static String readStringFromURL(String requestURL) throws IOException
	  {
	      try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
	              StandardCharsets.UTF_8.toString()))
	      {
	          scanner.useDelimiter("\\A");
	          return scanner.hasNext() ? scanner.next() : "";
	      }
	  }
}
