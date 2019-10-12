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
import java.util.*;

@WebServlet(name = "SkierServlet")
public class SkierServlet extends HttpServlet {

    private Random random = new Random();
    private static final Logger LOGGER = LogManager.getLogger(SkierServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
        try {
            LOGGER.info("Start SkierServlet Post request");
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

            if (!isUrlValid(urlParts, new ArrayList())) {
                LOGGER.error("Invalid url");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            while((line = bufferedReader.readLine()) != null) {
                requestBuilder.append(line);
            }

            String requestBody = requestBuilder.toString();
            LOGGER.info("requestBody::" + requestBody);

            LOGGER.info("End SkierServlet Post request");
            res.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            LOGGER.info("Start SkierServlet Get request");
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
            List<String> queryParts;

            if (queryString == null || queryString.isEmpty()) {
                queryParts = new ArrayList<String>();
                if (!isUrlValid(urlParts, queryParts)) {
                    LOGGER.error("Invalid url");
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                String jsonString = new Gson().toJson(random.nextInt(1000000));
                PrintWriter out = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                out.print(jsonString);
                out.flush();
                LOGGER.info("End SkierServlet Get request");
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                queryParts = Arrays.asList(queryString.split("&"));
                if (!isUrlValid(urlParts, queryParts)) {
                    LOGGER.error("Invalid url or query parameter");
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                List<ResortSkierVertical> resorts = new ArrayList<ResortSkierVertical>();
                ResortSkierVertical resort = new ResortSkierVertical("2019", 2);
                resorts.add(resort);
                SkierVerticalResponse vertical = new SkierVerticalResponse(resorts);

                String jsonString = new Gson().toJson(vertical);
                PrintWriter out = res.getWriter();
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                out.print(jsonString);
                out.flush();
                LOGGER.info("End SkierServlet Get request");
                res.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isUrlValid(List<String> urlPath, List<String> queryParts) {
        if (urlPath == null || urlPath.isEmpty()) {
            return false;
        }
        urlPath.remove(0);

        boolean isValidUrl;
        switch (urlPath.size()) {
            case 3:
                isValidUrl = isValidVerticalUrl(urlPath, queryParts);
                break;
            case 8:
                isValidUrl = isValidLiftUrl(urlPath, queryParts);
                break;
            default:
                LOGGER.error("Invalid size");
                isValidUrl = false;
        }
        return isValidUrl;
    }

    private boolean isValidVerticalUrl(List<String> urlPath, List<String> queryParts) {
        if (queryParts == null || queryParts.size() < 1) {
            return false;
        }

        boolean isValidSkiersString = urlPath.get(0).equals("skiers");
        boolean isValidSkierId = isValidSkierId(urlPath.get(1));
        boolean isValidVerticalString = urlPath.get(2).equals("vertical");
        boolean isValidQueryString = isValidQueryStringForVerticalUrl(queryParts);

        return isValidSkierId && isValidVerticalString && isValidQueryString && isValidSkiersString;
    }

    private boolean isValidLiftUrl(List<String> urlPath, List<String> queryParts) {
        if (queryParts != null && queryParts.size() > 0) {
            LOGGER.error("Invalid query parts");
            return false;
        }

        boolean isValidResortId = isValidResortId(urlPath.get(1));
        boolean isValidSeasonsString = urlPath.get(2).equals("seasons");
        boolean isValidDaysString = urlPath.get(4).equals("days");
        boolean isValidSkiersString = urlPath.get(6).equals("skiers");
        boolean isValidSkierId = isValidSkierId(urlPath.get(7));

        return isValidResortId && isValidSeasonsString && isValidDaysString && isValidSkiersString && isValidSkierId;
    }

    private boolean isValidSkierId(String skierId) {
        try {
            Integer.parseInt(skierId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidResortId(String resortId) {
        try {
            Integer.parseInt(resortId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidQueryStringForVerticalUrl(List<String> queryParts) {
        String queryPart = queryParts.get(0);
        String[] resortParts = queryPart.split("=");
        if (resortParts != null && resortParts.length == 2) {
            return resortParts[0].equals("resort");
        }
        return false;
    }
}
