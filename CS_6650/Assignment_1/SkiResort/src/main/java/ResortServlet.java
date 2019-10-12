import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ResortServlet")
public class ResortServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(ResortServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
        try {
            LOGGER.info("Start ResortServlet Post request");
            res.setContentType("application/json");
            String urlPath = request.getPathInfo();
            BufferedReader bufferedReader = request.getReader();
            StringBuilder requestBuilder = new StringBuilder();
            String line;

            LOGGER.info("UrlPath = " + urlPath);
            if (urlPath == null || urlPath.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("Missing parameters");
                return;
            }

            List<String> urlParts = new ArrayList(Arrays.asList(urlPath.split("/")));

            if (!isUrlValid(urlParts)) {
                LOGGER.error("Invalid url");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            while((line = bufferedReader.readLine()) != null) {
                requestBuilder.append(line);
            }

            String requestBody = requestBuilder.toString();
            LOGGER.info("requestBody::" + requestBody);

            LOGGER.info("End ResortServlet Post request");
            res.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            LOGGER.info("Start Resort Servlet Get request");
            res.setContentType("application/json");
            String urlPath = req.getPathInfo();
            String queryString = req.getQueryString();

            LOGGER.info("UrlPath = " + urlPath);
            if (urlPath == null || urlPath.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("Missing parameters");
                return;
            }

            List<String> urlParts = new ArrayList(Arrays.asList(urlPath.split("/")));

            if (!isUrlValid(urlParts)) {
                LOGGER.error("Invalid url");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (urlParts.size() == 1) {
                Resort resort = new Resort("resort1", 25);
                List<Resort> resorts = new ArrayList<Resort>();
                resorts.add(resort);
                ResortResponse resortResponse = new ResortResponse(resorts);
                String jsonString = new Gson().toJson(resortResponse);
                PrintWriter out = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                out.print(jsonString);
                out.flush();
                LOGGER.info("End Resort Servlet Get request");
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                List<String> seasons = Arrays.asList(new String[] {"2020"});
                SeasonResponse seasonResponse = new SeasonResponse(seasons);

                String jsonString = new Gson().toJson(seasonResponse);
                PrintWriter out = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                out.print(jsonString);
                out.flush();
                LOGGER.info("End Resort Servlet Get request");
                res.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isUrlValid(List<String> urlPath) {
        if (urlPath == null || urlPath.isEmpty()) {
            return false;
        }
        urlPath.remove(0);

        boolean isValidUrl;
        switch (urlPath.size()) {
            case 3:
                isValidUrl = isValidSeasonsUrl(urlPath);
                break;
            case 1:
                isValidUrl = urlPath.get(0).equals("resorts");
                break;
            default:
                LOGGER.error("Invalid size");
                isValidUrl = false;
        }
        return isValidUrl;
    }

    private boolean isValidSeasonsUrl(List<String> urlPath) {
        boolean isValidResortsString = urlPath.get(0).equals("resorts");
        boolean isValidSeasonsString = urlPath.get(2).equals("seasons");
        return isValidResortsString && isValidResortId(urlPath.get(1)) && isValidSeasonsString;
    }

    private boolean isValidResortId(String resortId) {
        try {
            Integer.parseInt(resortId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
