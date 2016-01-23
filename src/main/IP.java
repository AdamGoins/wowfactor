package wowfactor.src.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Josh on 1/23/2016.
 */
public class IP {

    public String address;
    public String continent;
    public String country;
    public String state;
    public String city;
    public String postalCode;
    public String latitude;
    public String longitude;

    public IP(String IP){
        this.address = IP;
        updateInfo();
    }

    private void updateInfo() {
        try {
            URL url = new URL("http://whatismyipaddress.com/ip/" + this.address);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "UA-1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            ArrayList<String> content = new ArrayList<>();
            while ((inputLine = reader.readLine()) != null) {
                if (inputLine.contains("Continent:") || inputLine.contains("Country:") ||
                        inputLine.contains("State/Region:") || inputLine.contains("City:") ||
                        inputLine.contains("&deg") || inputLine.contains("Postal Code:") ||
                        inputLine.contains("Longitude") || inputLine.contains("Latitude")) {
                    content.add(inputLine.replaceAll("\\<[^>]*>", "").replaceAll("&nbsp;", "").replaceAll("&deg;", "*")
                            .replaceAll("&prime;", "'").replaceAll("&Prime;", "\"").trim());
                }
                this.continent = content.get(0).substring(content.get(0).indexOf(":") + 1);
                this.country = content.get(1).substring(content.get(1).indexOf(":") + 1);
                this.state = content.get(2).substring(content.get(2).indexOf(":") + 1);
                this.city = content.get(3).substring(content.get(3).indexOf(":") + 1);
                this.latitude = content.get(5);
                this.longitude = content.get(7);
                this.postalCode = content.get(8).substring(content.get(8).indexOf(":") + 1);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
