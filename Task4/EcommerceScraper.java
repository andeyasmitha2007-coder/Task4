
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class EcommerceScraper {

    public static void main(String[] args) {

        String url = "https://books.toscrape.com/";

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements products = document.select("article.product_pod");

            FileWriter writer = new FileWriter("products.csv");

            writer.append("Name,Price,Rating\n");

            for (Element product : products) {

                String name = product.select("h3 a")
                        .attr("title");

                String price = product.select(".price_color")
                        .text();

                String rating = product.select("p.star-rating")
                        .attr("class")
                        .replace("star-rating", "")
                        .trim();

                writer.append("\"")
                        .append(name)
                        .append("\",")
                        .append(price)
                        .append(",")
                        .append(rating)
                        .append("\n");
            }

            writer.flush();
            writer.close();

            System.out.println("Product data saved to products.csv");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}